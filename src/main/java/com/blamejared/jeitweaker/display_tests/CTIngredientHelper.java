package com.blamejared.jeitweaker.display_tests;

import com.blamejared.crafttweaker.api.item.*;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;

import javax.annotation.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CTIngredientHelper implements IIngredientHelper<CTIngredientInfo> {
    
    @Nullable
    @Override
    public CTIngredientInfo getMatch(Iterable<CTIngredientInfo> ingredients, CTIngredientInfo ingredientToMatch) {
        return null;
    }
    
    @Override
    public String getDisplayName(CTIngredientInfo ingredient) {
        return ingredient.getOriginal().getCommandString();
    }
    
    @Override
    public String getUniqueId(CTIngredientInfo ingredient) {
        return ingredient.getOriginal().getCommandString();
    }
    
    @Override
    public String getWildcardId(CTIngredientInfo ingredient) {
        return ingredient.getOriginal().getCommandString();
    }
    
    @Override
    public String getModId(CTIngredientInfo ingredient) {
        return "crafttweaker";
    }
    
    @Override
    public String getResourceId(CTIngredientInfo ingredient) {
        return "crafttweaker";
    }
    
    @Override
    public CTIngredientInfo copyIngredient(CTIngredientInfo ingredient) {
        return ingredient;
    }
    
    @Override
    public String getErrorInfo(@Nullable CTIngredientInfo ingredient) {
        return "NO ERROR";
    }
}
