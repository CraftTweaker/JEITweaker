package com.blamejared.jeitweaker.display_tests;

import com.blamejared.crafttweaker.api.item.*;

public class CTIngredientInfo {
    
    private final IItemStack stack;
    private final IIngredient original;
    
    public CTIngredientInfo(IItemStack stack) {
        this(stack, stack);
    }
    
    public CTIngredientInfo(IItemStack stack, IIngredient original) {
        this.stack = stack;
        this.original = original;
    }
    
    public IItemStack getStack() {
        return stack;
    }
    
    public IIngredient getOriginal() {
        return original;
    }
}
