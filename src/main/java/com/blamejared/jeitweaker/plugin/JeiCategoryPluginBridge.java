package com.blamejared.jeitweaker.plugin;

import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;

public interface JeiCategoryPluginBridge {
    
    <G> void initializeGui(final IGuiIngredientGroup<G> group);
    
    int getInputSlotsAmount();
    
    int getOutputSlotsAmount();
}
