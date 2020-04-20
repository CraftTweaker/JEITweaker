package com.blamejared.jeitweaker.display_tests;

import com.blamejared.crafttweaker.impl.recipes.*;
import com.blamejared.jeitweaker.*;
import mezz.jei.api.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.advanced.*;
import mezz.jei.api.recipe.category.*;
import mezz.jei.gui.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CTRecipeManagerPlugin implements IRecipeManagerPlugin {
    
    public static final Set<CTRecipeShaped> ALL_SHAPED = new HashSet<>();
    
    public CTRecipeManagerPlugin() {
    }
    
    @Override
    public <V> List<ResourceLocation> getRecipeCategoryUids(IFocus<V> focus) {
        if(focus.getValue() instanceof CTIngredientInfo && JEIAddonPlugin.recipeManager != null) {
            return JEIAddonPlugin.recipeManager.getRecipeCategories(new Focus<>(focus.getMode(), ((CTIngredientInfo) focus
                    .getValue()).getStack().getInternal()))
                    .stream()
                    .map(IRecipeCategory::getUid)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T, V> List getRecipes(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
        if(focus.getValue() instanceof CTIngredientInfo && JEIAddonPlugin.recipeManager != null) {
            return JEIAddonPlugin.recipeManager.getRecipes(recipeCategory, new Focus<>(focus.getMode(), ((CTIngredientInfo) focus
                    .getValue()).getStack().getInternal()));
        }
        
        return Collections.emptyList();
    }
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List getRecipes(IRecipeCategory<T> recipeCategory) {
        return Collections.emptyList();
    }
}
