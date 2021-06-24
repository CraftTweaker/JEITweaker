package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import mezz.jei.api.*;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIAddonPlugin implements IModPlugin {
    
    public static final List<ResourceLocation> JEI_CATEGORIES = new ArrayList<>();
    
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        
        IIngredientManager ingredientManager = registration.getIngredientManager();
        IIngredientType<ItemStack> itemType = ingredientManager.getIngredientType(ItemStack.class);
        IIngredientType<FluidStack> fluidType = ingredientManager.getIngredientType(FluidStack.class);
        JEIManager.ITEM_DESCRIPTIONS.forEach((key, value) -> registration.addIngredientInfo(key.getInternal(), itemType, value));
        JEIManager.FLUID_DESCRIPTIONS.forEach((key, value) -> registration.addIngredientInfo(key.getInternal(), fluidType, value));
    }
    
    
    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        
        Item[] items = JEIManager.CUSTOM_ITEMS.stream()
                .filter(iItemStack -> !registration.hasSubtypeInterpreter(iItemStack.getInternal()))
                .map(iItemStack -> iItemStack.getInternal().getItem())
                .distinct()
                .toArray(Item[]::new);
        registration.useNbtForSubtypes(items);
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        
        IIngredientManager ingredientManager = iJeiRuntime.getIngredientManager();
        IIngredientType<ItemStack> itemType = ingredientManager.getIngredientType(ItemStack.class);
        IIngredientType<FluidStack> fluidType = ingredientManager.getIngredientType(FluidStack.class);
        
        if(!JEIManager.HIDDEN_ITEMS.isEmpty()) {
            List<ItemStack> collect = ingredientManager.getAllIngredients(itemType)
                    .stream()
                    .filter(itemStack -> JEIManager.HIDDEN_ITEMS.stream()
                            .anyMatch(iItemStack -> iItemStack.matches(new MCItemStackMutable(itemStack))))
                    .collect(Collectors.toList());
            ingredientManager.removeIngredientsAtRuntime(itemType, collect);
        }
        if(!JEIManager.HIDDEN_FLUIDS.isEmpty()) {
            ingredientManager.removeIngredientsAtRuntime(fluidType, JEIManager.HIDDEN_FLUIDS.stream()
                    .map(IFluidStack::getInternal)
                    .collect(Collectors.toList()));
        }
        Set<ResourceLocation> changingCategories = JEIManager.HIDDEN_RECIPE_CATEGORIES.stream()
                .map(ResourceLocation::new).collect(Collectors.toSet());
        
        Set<ResourceLocation> foundCategories = changingCategories.stream()
                .filter(rl -> iJeiRuntime.getRecipeManager()
                        .getRecipeCategories()
                        .stream()
                        .anyMatch(iRecipeCategory -> iRecipeCategory.getUid().equals(rl)))
                .collect(Collectors.toSet());
        foundCategories.forEach(iJeiRuntime.getRecipeManager()::hideRecipeCategory);
        
        changingCategories.removeAll(foundCategories);
        
        changingCategories.forEach(resourceLocation -> {
            CraftTweakerAPI.logError("JEITweaker: Unable to remove JEI category with uid: `%s` as it is not a valid category!", resourceLocation
                    .toString());
        });
        
        JEIManager.HIDDEN_RECIPES
                .forEach(val -> {
                    ResourceLocation category = new ResourceLocation(val.getLeft());
                    ResourceLocation recipeName = new ResourceLocation(val.getRight());
                    Optional<? extends IRecipe<?>> recipe = CTCraftingTableManager.recipeManager.getRecipe(recipeName);
                    
                    if(recipe.isPresent()) {
                        iJeiRuntime.getRecipeManager().hideRecipe(recipe.get(), category);
                    } else {
                        CraftTweakerAPI.logger.throwingErr("Cannot hide recipe with ID: " + val + " as it does not exist!", new IllegalArgumentException("Cannot hide recipe with ID: " + val + " as it does not exist!"));
                    }
                });
        
        iJeiRuntime.getIngredientManager()
                .addIngredientsAtRuntime(VanillaTypes.ITEM, JEIManager.CUSTOM_ITEMS.stream()
                        .map(IItemStack::getInternal)
                        .collect(Collectors.toList()));
        
        
        JEI_CATEGORIES.clear();
        JEI_CATEGORIES.addAll(iJeiRuntime.getRecipeManager()
                .getRecipeCategories()
                .stream()
                .map(IRecipeCategory::getUid)
                .collect(Collectors.toList()));
    }
    
    @Override
    public ResourceLocation getPluginUid() {
        
        return new ResourceLocation("jeitweaker:main");
    }
    
}