package com.blamejared.jeitweaker.display_tests;

import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;

import javax.annotation.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CTIngredientHelper implements IIngredientHelper<IItemStack> {
    
    @Nullable
    @Override
    public IItemStack getMatch(Iterable<IItemStack> ingredients, IItemStack ingredientToMatch) {
        return null;
    }
    
    @Override
    public String getDisplayName(IItemStack ingredient) {
        return ingredient.getCommandString();
    }
    
    @Override
    public String getUniqueId(IItemStack ingredient) {
        return ingredient.getCommandString();
    }
    
    @Override
    public String getWildcardId(IItemStack ingredient) {
        return ingredient.getCommandString();
    }
    
    @Override
    public String getModId(IItemStack ingredient) {
        return ingredient.getInternal().getItem().delegate.name().getNamespace();
    }
    
    @Override
    public String getResourceId(IItemStack ingredient) {
        return ingredient.getInternal().getItem().delegate.name().toString();
    }
    
    @Override
    public IItemStack copyIngredient(IItemStack ingredient) {
        return new MCItemStack(ingredient.getInternal().copy());
    }
    
    @Override
    public String getErrorInfo(@Nullable IItemStack ingredient) {
        return "NO ERROR";
    }
}
