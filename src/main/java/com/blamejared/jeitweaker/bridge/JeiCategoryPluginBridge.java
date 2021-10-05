package com.blamejared.jeitweaker.bridge;

import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;

import java.util.function.IntUnaryOperator;

public interface JeiCategoryPluginBridge {
    
    <G> void initializeGui(final IGuiIngredientGroup<G> group, final IntUnaryOperator coordinateFixer);
    
    int getInputSlotsAmount();
    
    int getOutputSlotsAmount();
    
    boolean allowShapelessMarker();
}
