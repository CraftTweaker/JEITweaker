package com.blamejared.jeitweaker.display_tests;

import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.managers.*;
import com.blamejared.crafttweaker.impl.recipes.*;
import mezz.jei.api.*;
import mezz.jei.api.constants.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.advanced.*;
import mezz.jei.api.recipe.category.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CTRecipeManagerPlugin implements IRecipeManagerPlugin {
    
    public static final List<CTRecipeShaped> ALL_SHAPED = new ArrayList<>();
    
    public CTRecipeManagerPlugin() {
    }
    
    @Nullable
    private static <V> IIngredient getIIngredient(IFocus<V> focus) {
        final V value = focus.getValue();
        if(value instanceof ItemStack) {
            return new MCItemStack((ItemStack) value);
        } else if(value instanceof IIngredient) {
            return (IIngredient) value;
        } else {
            return null;
        }
    }
    
    @Override
    public <V> List<ResourceLocation> getRecipeCategoryUids(IFocus<V> focus) {
        final IIngredient iIngredient = getIIngredient(focus);
        if(iIngredient == null) {
            return Collections.emptyList();
        }
        
        if(focus.getMode() == IFocus.Mode.INPUT) {
            if(ALL_SHAPED.stream()
                    .anyMatch(recipe -> iIngredient.matches(new MCItemStack(recipe.getRecipeOutput())))) {
                return Collections.singletonList(VanillaRecipeCategoryUid.CRAFTING);
            }
        }
        if(ALL_SHAPED.stream()
                .map(CTRecipeShaped::getIngredients)
                .flatMap(Collection::stream)
                .map(IIngredient::fromIngredient)
                .flatMap(i -> Arrays.stream(i.getItems()))
                .anyMatch(iIngredient::matches)) {
            return Collections.singletonList(VanillaRecipeCategoryUid.CRAFTING);
        }
        
        
        return Collections.emptyList();
    }
    
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T, V> List getRecipes(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
        final IIngredient ingredient = getIIngredient(focus);
        if(ingredient == null || recipeCategory.getUid() != VanillaRecipeCategoryUid.CRAFTING) {
            return Collections.emptyList();
        }
        
        if(focus.getMode() == IFocus.Mode.OUTPUT) {
            return getOutputRecipes(ingredient, focus.getValue() instanceof IIngredient);
        }
        return getInputRecipes(ingredient, focus.getValue() instanceof IIngredient);
    }
    
    private List<ICraftingRecipe> getInputRecipes(IIngredient ingredient, boolean includeVanilla) {
        return getRecipeStream(includeVanilla).filter(r -> r.getIngredients()
                .stream()
                .map(IIngredient::fromIngredient)
                .flatMap(i -> Arrays.stream(i.getItems()))
                .anyMatch(ingredient::matches)).distinct().collect(Collectors.toList());
    }
    
    private List<ICraftingRecipe> getOutputRecipes(IIngredient ingredient, boolean includeVanilla) {
        return getRecipeStream(includeVanilla).filter(r -> ingredient.matches(new MCItemStack(r.getRecipeOutput())))
                .distinct()
                .collect(Collectors.toList());
    }
    
    private Stream<? extends ICraftingRecipe> getRecipeStream(boolean includeVanilla) {
        if(includeVanilla && CTCraftingTableManager.recipeManager != null) {
            return CTCraftingTableManager.recipeManager.getRecipes()
                    .stream()
                    .filter(r -> r instanceof ICraftingRecipe)
                    .map(r -> ((ICraftingRecipe) r));
        } else {
            return ALL_SHAPED.stream();
        }
    }
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List getRecipes(IRecipeCategory<T> recipeCategory) {
        if(recipeCategory.getUid() == VanillaRecipeCategoryUid.CRAFTING) {
            return ALL_SHAPED;
        }
        return Collections.emptyList();
    }
}
