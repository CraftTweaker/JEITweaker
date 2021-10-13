package com.blamejared.jeitweaker.bridge;

import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;

import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

public final class OutputListCategoryBridge implements JeiCategoryPluginBridge {
    
    private final IntSupplier rows;
    
    public OutputListCategoryBridge(final IntSupplier rowSupplier) {
        
        this.rows = rowSupplier;
    }
    
    @Override
    public <G> void initializeGui(final IGuiIngredientGroup<G> group, final IntUnaryOperator coordinateFixer) {
    
        for (int i = 0, r = this.rows.getAsInt(); i < r; ++i) {
            
            for (int j = 0; j < 9; ++j) {
                
                group.init(i * 9 + j, false, coordinateFixer.applyAsInt(j * 18 + 1), coordinateFixer.applyAsInt(i * 18 + 1));
            }
        }
    }
    
    @Override
    public int getInputSlotsAmount() {
        
        return 0;
    }
    
    @Override
    public int getOutputSlotsAmount() {
        
        return this.rows.getAsInt() * 9;
    }
    
    @Override
    public boolean allowShapelessMarker() {
        
        return false;
    }
    
    @Override
    public void drawAdditionalComponent(final MatrixStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer) {}
    
}
