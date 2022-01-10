package com.blamejared.jeitweaker.jei;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.jeitweaker.JEITweaker;
import com.blamejared.jeitweaker.api.BuiltinIngredientTypes;
import com.blamejared.jeitweaker.api.IngredientType;
import com.blamejared.jeitweaker.implementation.CoordinateFixerManager;
import com.blamejared.jeitweaker.implementation.state.StateManager;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
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
public final class JeiTweakerPlugin implements IModPlugin {
    
    private static final ResourceLocation ID = new ResourceLocation(JEITweaker.MOD_ID, "main");
    
    public JeiTweakerPlugin() {
        
        System.out.println("Initialized plugin " + ID);
    }
    
    @Override
    public ResourceLocation getPluginUid() {
        
        return ID;
    }
    
    @Override
    public void registerItemSubtypes(final ISubtypeRegistration registration) {
        
        this.registerSubtypesFor(BuiltinIngredientTypes.ITEM.get(), registration::hasSubtypeInterpreter, ItemStack::getItem, Item[]::new, registration::useNbtForSubtypes);
    }
    
    @Override
    public void registerFluidSubtypes(final ISubtypeRegistration registration) {
    
        this.registerSubtypesFor(BuiltinIngredientTypes.FLUID.get(), registration::hasSubtypeInterpreter, FluidStack::getFluid, Fluid[]::new, registration::useNbtForSubtypes);
    }
    
    @Override
    public void registerIngredients(final IModIngredientRegistration registration) {}
    
    @Override
    public void registerCategories(final IRecipeCategoryRegistration registration) {
        
        final IJeiHelpers helpers = registration.getJeiHelpers();
        final IRecipeCategory<?>[] categories = StateManager.INSTANCE
                .actionsState()
                .getCustomCategories()
                .map(it -> new JeiTweakerCategory(it, helpers)).toArray(IRecipeCategory[]::new);
        
        if (categories.length == 0) return;
        
        registration.addRecipeCategories(categories);
    }
    
    @Override
    public void registerVanillaCategoryExtensions(final IVanillaCategoryExtensionRegistration registration) {}
    
    @Override
    public void registerRecipes(final IRecipeRegistration registration) {
        
        StateManager.INSTANCE.registrationState().ingredientTypes().forEach(type -> this.registerDescriptionsFor(registration, type));
        StateManager.INSTANCE.actionsState().getCustomCategories().forEach(category -> this.registerRecipeFor(registration, category));
    }
    
    @Override
    public void registerRecipeTransferHandlers(final IRecipeTransferRegistration registration) {}
    
    @Override
    public void registerRecipeCatalysts(final IRecipeCatalystRegistration registration) {
    
        StateManager.INSTANCE.actionsState().getCustomCategories().forEach(category -> this.registerCatalystsFor(registration, category));
    }
    
    @Override
    public void registerGuiHandlers(final IGuiHandlerRegistration registration) {}
    
    @Override
    public void registerAdvanced(final IAdvancedRegistration registration) {}
    
    @Override
    public void onRuntimeAvailable(final IJeiRuntime iJeiRuntime) {
        
        final IIngredientManager ingredientManager = iJeiRuntime.getIngredientManager();
        final IRecipeManager recipeManager = iJeiRuntime.getRecipeManager();
    
        StateManager.INSTANCE.registrationState().ingredientTypes().forEach(type -> {
            
            this.hideIngredientsFor(ingredientManager, type);
            this.addItemsFor(ingredientManager, type);
        });
        
        this.hideRecipeCategories(recipeManager);
        this.hideRecipes(recipeManager);
        this.storeCurrentCategories(recipeManager);
    }
    
    private <T, U, R> void registerSubtypesFor(final IngredientType<T, U> type, final Predicate<U> hasSubtype, final Function<U, R> flatter,
                                               final IntFunction<R[]> arrayCreator, final Consumer<R[]> consumer) {
    
        final R[] ingredientsWithoutSubtypes = StateManager.INSTANCE.actionsState().getCustomIngredientsForType(type)
                .stream()
                .map(type::toJeiType)
                .filter(hasSubtype.negate())
                .map(flatter)
                .distinct()
                .toArray(arrayCreator);
        
        if (ingredientsWithoutSubtypes.length == 0) return;
        
        consumer.accept(ingredientsWithoutSubtypes);
    }
    
    private <T, U> void registerDescriptionsFor(final IRecipeRegistration registration, final IngredientType<T, U> type) {
    
        StateManager.INSTANCE.actionsState().onDescriptionsFor(
                type,
                (ingredient, description) -> registration.addIngredientInfo(type.toJeiType(ingredient), type.toJeiIngredientType(registration.getIngredientManager()), description)
        );
    }
    
    private void registerRecipeFor(final IRecipeRegistration registration, final JeiCategory category) {
        
        final IIngredientManager ingredientManager = registration.getIngredientManager();
        final CoordinateFixerManager fixerManager = CoordinateFixerManager.of(ingredientManager);
        registration.addRecipes(
                category.getTargetRecipes().stream().map(it -> new JeiTweakerRecipe(it, ingredientManager, fixerManager)).collect(Collectors.toList()),
                category.id()
        );
    }
    
    private void registerCatalystsFor(final IRecipeCatalystRegistration registration, final JeiCategory category) {
        
        final ResourceLocation id = category.id();
        final IIngredientManager ingredientManager = GrossInternalHacks.getIngredientManager();
        
        if (ingredientManager == null) return; // Prevent hard crashes
        
        Arrays.stream(category.catalysts())
                .map(RawJeiIngredient::cast)
                .forEach(ing -> registration.addRecipeCatalyst(ing.getType().toJeiIngredientType(ingredientManager), ing.getType().toJeiType(ing.getWrapped()), id));
    }
    
    private <T, U> void hideIngredientsFor(final IIngredientManager manager, final IngredientType<T, U> type) {
        
        final Collection<T> hiddenIngredients = StateManager.INSTANCE.actionsState().getHiddenIngredientsForType(type);
        final IIngredientType<U> jeiType = type.toJeiIngredientType(manager);
        final List<U> removals = manager.getAllIngredients(jeiType)
                .stream()
                .map(type::toJeiTweakerType)
                .filter(ingredient -> hiddenIngredients.stream().anyMatch(it -> type.match(it, ingredient)))
                .map(type::toJeiType)
                .collect(Collectors.toList());
        
        if (removals.isEmpty()) return;
        
        manager.removeIngredientsAtRuntime(jeiType, removals);
    }
    
    private <T, U> void addItemsFor(final IIngredientManager manager, final IngredientType<T, U> type) {
        
        final IIngredientType<U> jeiType = type.toJeiIngredientType(manager);
        final Collection<U> additions = StateManager.INSTANCE.actionsState().getCustomIngredientsForType(type)
                .stream()
                .map(type::toJeiType)
                .collect(Collectors.toList());
        
        if (additions.isEmpty()) return;
        
        manager.addIngredientsAtRuntime(jeiType, additions);
    }
    
    private void hideRecipeCategories(final IRecipeManager manager) {
        
        final Set<ResourceLocation> availableCategories = this.getJeiCategoriesFrom(manager, true);
        final Set<ResourceLocation> targetCategories = StateManager.INSTANCE.actionsState().getCategoriesToHide();
        final Set<ResourceLocation> hiddenCategories = targetCategories.stream()
                .filter(availableCategories::contains)
                .peek(manager::hideRecipeCategory)
                .collect(Collectors.toSet());
        Sets.difference(targetCategories, hiddenCategories)
                .forEach(invalid -> CraftTweakerAPI.LOGGER.error("[JEITweaker] Unable to remove JEI category with id '{}' as it does not exist!", invalid));
    }
    
    private void hideRecipes(final IRecipeManager manager) {
    
        StateManager.INSTANCE.actionsState().onHiddenRecipes((categoryId, recipeId) -> {
    
            final Optional<? extends Recipe<?>> recipe = CraftTweakerAPI.getRecipeManager().byKey(recipeId);
            
            if (recipe.isPresent()) {
                manager.hideRecipe(recipe.get(), categoryId);
            } else {
                CraftTweakerAPI.LOGGER.error("[JEITweaker] Cannot hide recipe with id '{}' in category '{}' as it does not exist!", recipeId, categoryId);
            }
        });
    }
    
    private void storeCurrentCategories(final IRecipeManager manager) {
        
        final Set<ResourceLocation> allCategories = this.getJeiCategoriesFrom(manager, true);
        final Set<ResourceLocation> nonHiddenCategories = this.getJeiCategoriesFrom(manager, false);
        StateManager.INSTANCE.jeiGlobalState().replaceJeiCategoriesWith(allCategories, nonHiddenCategories);
    }
    
    private Set<ResourceLocation> getJeiCategoriesFrom(final IRecipeManager manager, final boolean hidden) {
        
        return manager.getRecipeCategories(null, hidden)
                .stream()
                .map(IRecipeCategory::getUid)
                .collect(Collectors.toSet());
    }
}