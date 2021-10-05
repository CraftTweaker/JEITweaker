package com.blamejared.jeitweaker.bridge;

import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;

import java.util.function.IntUnaryOperator;

public final class SimpleInputOutputCategoryBridge implements JeiCategoryPluginBridge {
    
    @Override
    public <G> void initializeGui(final IGuiIngredientGroup<G> group, final IntUnaryOperator coordinateFixer) {
        
        group.init(0, true, coordinateFixer.applyAsInt(1), coordinateFixer.applyAsInt(8));
        group.init(1, false, coordinateFixer.applyAsInt(61), coordinateFixer.applyAsInt(8));
    }
    
    @Override
    public int getInputSlotsAmount() {
        
        return 1;
    }
    
    @Override
    public int getOutputSlotsAmount() {
        
        return 1;
    }
    
    @Override
    public boolean allowShapelessMarker() {
        
        return false;
    }
}