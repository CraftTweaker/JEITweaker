package com.blamejared.jeitweaker.plugin;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.jeitweaker.JEITweaker;
import com.blamejared.jeitweaker.helper.coordinate.JeiCoordinateFixerManager;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import com.google.common.collect.Sets;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
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
public final class JeiAddonPlugin implements IModPlugin {
    
    private static final ResourceLocation ID = new ResourceLocation(JEITweaker.MOD_ID, "main");
    
    @Override
    public ResourceLocation getPluginUid() {
        
        return ID;
    }
    
    @Override
    public void registerItemSubtypes(final ISubtypeRegistration registration) {
        
        this.registerSubtypesFor(JeiTweakerIngredientType.ITEM, registration::hasSubtypeInterpreter, ItemStack::getItem, Item[]::new, registration::useNbtForSubtypes);
    }
    
    @Override
    public void registerFluidSubtypes(final ISubtypeRegistration registration) {
    
        this.registerSubtypesFor(JeiTweakerIngredientType.FLUID, registration::hasSubtypeInterpreter, FluidStack::getFluid, Fluid[]::new, registration::useNbtForSubtypes);
    }
    
    @Override
    public void registerIngredients(final IModIngredientRegistration registration) {}
    
    @Override
    public void registerCategories(final IRecipeCategoryRegistration registration) {
        
        final IJeiHelpers helpers = registration.getJeiHelpers();
        final IRecipeCategory<?>[] categories = JeiStateManager.INSTANCE.getCustomCategories().map(it -> new JeiTweakerCategory(it, helpers)).toArray(IRecipeCategory[]::new);
        
        if (categories.length == 0) return;
        
        registration.addRecipeCategories(categories);
    }
    
    @Override
    public void registerVanillaCategoryExtensions(final IVanillaCategoryExtensionRegistration registration) {}
    
    @Override
    public void registerRecipes(final IRecipeRegistration registration) {
        
        JeiTweakerIngredientType.values().forEach(type -> this.registerDescriptionsFor(registration, type));
        JeiStateManager.INSTANCE.getCustomCategories().forEach(category -> this.registerRecipeFor(registration, category));
    }
    
    @Override
    public void registerRecipeTransferHandlers(final IRecipeTransferRegistration registration) {}
    
    @Override
    public void registerRecipeCatalysts(final IRecipeCatalystRegistration registration) {
        
        JeiStateManager.INSTANCE.getCustomCategories().forEach(category -> this.registerCatalystsFor(registration, category));
    }
    
    @Override
    public void registerGuiHandlers(final IGuiHandlerRegistration registration) {}
    
    @Override
    public void registerAdvanced(final IAdvancedRegistration registration) {}
    
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
    
    private void registerRecipeFor(final IRecipeRegistration registration, final JeiCategory category) {
        
        final IIngredientManager ingredientManager = registration.getIngredientManager();
        final JeiCoordinateFixerManager fixerManager = new JeiCoordinateFixerManager(ingredientManager);
        registration.addRecipes(
                category.getTargetRecipes().stream().map(it -> new JeiTweakerRecipe(it, ingredientManager, fixerManager)).collect(Collectors.toList()),
                category.id()
        );
    }
    
    private void registerCatalystsFor(final IRecipeCatalystRegistration registration, final JeiCategory category) {
        
        final ResourceLocation id = category.id();
        Arrays.stream(category.catalysts())
                .map(RawJeiIngredient::cast)
                .map(ingredient -> ingredient.getType().toInternal(ingredient.getWrapped()))
                .forEach(catalyst -> registration.addRecipeCatalyst(catalyst, id));
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
    
    private <T, U> void addItemsFor(final IIngredientManager manager, final JeiTweakerIngredientType<T, U> type) {
        
        final IIngredientType<U> jeiType = type.toJeiType(manager);
        final Collection<U> additions = JeiStateManager.INSTANCE.getCustomIngredientsForType(type)
                .stream()
                .map(type::toInternal)
                .collect(Collectors.toList());
        
        if (additions.isEmpty()) return;
        
        manager.addIngredientsAtRuntime(jeiType, additions);
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