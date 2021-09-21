package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.jeitweaker.state.JeiStateManager;
import com.blamejared.jeitweaker.state.JeiTweakerIngredientType;
import com.google.common.collect.Sets;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@JeiPlugin
public class JeiAddonPlugin implements IModPlugin {
    
    private static final ResourceLocation ID = new ResourceLocation(JEITweaker.MOD_ID, "main");
    
    @Override
    public void registerRecipes(final IRecipeRegistration registration) {
        
        JeiTweakerIngredientType.values().forEach(type -> this.registerDescriptionsFor(registration, type));
    }
    
    @Override
    public void registerItemSubtypes(final ISubtypeRegistration registration) {
        
        this.registerSubtypesFor(JeiTweakerIngredientType.ITEM, registration::hasSubtypeInterpreter, ItemStack::getItem, Item[]::new, registration::useNbtForSubtypes);
        this.registerSubtypesFor(JeiTweakerIngredientType.FLUID, registration::hasSubtypeInterpreter, FluidStack::getFluid, Fluid[]::new, registration::useNbtForSubtypes);
    }
    
    @Override
    public void onRuntimeAvailable(final IJeiRuntime iJeiRuntime) {
        
        final IIngredientManager ingredientManager = iJeiRuntime.getIngredientManager();
        final IRecipeManager recipeManager = iJeiRuntime.getRecipeManager();
        
        JeiTweakerIngredientType.values().forEach(type -> {
            
            this.hideIngredientsFor(ingredientManager, type);
            this.addItemsFor(ingredientManager, type);
        });
        
        this.hideRecipeCategories(recipeManager);
        this.hideRecipes(recipeManager);
        this.storeCurrentCategories(recipeManager);
    }
    
    @Override
    public ResourceLocation getPluginUid() {
        
        return ID;
    }
    
    private <T, U, R> void registerSubtypesFor(final JeiTweakerIngredientType<T, U> type, final Predicate<U> hasSubtype, final Function<U, R> flatter,
                                               final IntFunction<R[]> arrayCreator, final Consumer<R[]> consumer) {
    
        final R[] ingredientsWithoutSubtypes = JeiStateManager.INSTANCE.getCustomIngredientsForType(type)
                .stream()
                .map(type::toInternal)
                .filter(hasSubtype.negate())
                .map(flatter)
                .distinct()
                .toArray(arrayCreator);
        
        if (ingredientsWithoutSubtypes.length == 0) return;
        
        consumer.accept(ingredientsWithoutSubtypes);
    }
    
    private <T, U> void registerDescriptionsFor(final IRecipeRegistration registration, final JeiTweakerIngredientType<T, U> type) {
        
        JeiStateManager.INSTANCE.onDescriptionsFor(type, (ingredient, description) ->
                registration.addIngredientInfo(type.toInternal(ingredient), type.toJeiType(registration.getIngredientManager()), description));
    }
    
    private <T, U> void addItemsFor(final IIngredientManager manager, final JeiTweakerIngredientType<T, U> type) {
        
        final IIngredientType<U> jeiType = type.toJeiType(manager);
        final Collection<U> additions = JeiStateManager.INSTANCE.getCustomIngredientsForType(type)
                .stream()
                .map(type::toInternal)
                .collect(Collectors.toList());
        
        if (additions.isEmpty()) return;
        
        manager.addIngredientsAtRuntime(jeiType, additions);
    }
    
    private <T, U> void hideIngredientsFor(final IIngredientManager manager, final JeiTweakerIngredientType<T, U> type) {
    
        final Collection<T> hiddenIngredients = JeiStateManager.INSTANCE.getHiddenIngredientsForType(type);
        final IIngredientType<U> jeiType = type.toJeiType(manager);
        final List<U> removals = manager.getAllIngredients(jeiType)
                .stream()
                .map(type::toJeiTweaker)
                .filter(ingredient -> hiddenIngredients.stream().anyMatch(it -> type.match(it, ingredient)))
                .map(type::toInternal)
                .collect(Collectors.toList());
        
        if (removals.isEmpty()) return;
        
        manager.removeIngredientsAtRuntime(jeiType, removals);
    }
    
    private void hideRecipeCategories(final IRecipeManager manager) {
        
        final Set<ResourceLocation> availableCategories = this.getJeiCategoriesFrom(manager, true);
        final Set<ResourceLocation> targetCategories = JeiStateManager.INSTANCE.getCategoriesToHide();
        final Set<ResourceLocation> hiddenCategories = targetCategories.stream()
                .filter(availableCategories::contains)
                .peek(manager::hideRecipeCategory)
                .collect(Collectors.toSet());
        Sets.difference(targetCategories, hiddenCategories)
                .forEach(invalid -> CraftTweakerAPI.logError("[JEITweaker] Unable to remove JEI category with id '%s' as it does not exist!", invalid));
    }
    
    private void hideRecipes(final IRecipeManager manager) {
        
        JeiStateManager.INSTANCE.onHiddenRecipes((categoryId, recipeId) -> {
    
            final Optional<? extends IRecipe<?>> recipe = CTCraftingTableManager.recipeManager.getRecipe(recipeId);
            
            if (recipe.isPresent()) {
                manager.hideRecipe(recipe.get(), categoryId);
            } else {
                CraftTweakerAPI.logError("[JEITweaker] Cannot hide recipe with id '%s' in category '%s' as it does not exist!", recipeId, categoryId);
            }
        });
    }
    
    private void storeCurrentCategories(final IRecipeManager manager) {
        
        final Set<ResourceLocation> allCategories = this.getJeiCategoriesFrom(manager, true);
        final Set<ResourceLocation> nonHiddenCategories = this.getJeiCategoriesFrom(manager, false);
        JeiStateManager.INSTANCE.replaceJeiCategoriesWith(allCategories, nonHiddenCategories);
    }
    
    private Set<ResourceLocation> getJeiCategoriesFrom(final IRecipeManager manager, final boolean hidden) {
        
        return manager.getRecipeCategories(null, hidden)
                .stream()
                .map(IRecipeCategory::getUid)
                .collect(Collectors.toSet());
    }
}