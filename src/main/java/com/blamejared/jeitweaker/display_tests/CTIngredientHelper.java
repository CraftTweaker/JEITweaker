package com.blamejared.jeitweaker.display_tests;

import com.blamejared.crafttweaker.api.item.*;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;

import javax.annotation.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CTIngredientHelper implements IIngredientHelper<IIngredient> {
    
    @Nullable
    @Override
    public IIngredient getMatch(Iterable<IIngredient> ingredients, IIngredient ingredientToMatch) {
        return null;
    }
    
    @Override
    public String getDisplayName(IIngredient ingredient) {
        return ingredient.getCommandString();
    }
    
    @Override
    public String getUniqueId(IIngredient ingredient) {
        return ingredient.getCommandString();
    }
    
    @Override
    public String getWildcardId(IIngredient ingredient) {
        return ingredient.getCommandString();
    }
    
    @Override
    public String getModId(IIngredient ingredient) {
        return "crafttweaker";
    }
    
    @Override
    public String getResourceId(IIngredient ingredient) {
        return "crafttweaker";
    }
    
    @Override
    public IIngredient copyIngredient(IIngredient ingredient) {
        return ingredient;
    }
    
    @Override
    public String getErrorInfo(@Nullable IIngredient ingredient) {
        return "NO ERROR";
    }
}
