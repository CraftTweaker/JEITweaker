package com.blamejared.jeitweaker.bridge;

import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.helper.coordinate.JeiCoordinateFixer;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntSupplier;

public final class OutputListCategoryBridge implements JeiCategoryPluginBridge {
    
    private final IntSupplier rows;
    
    public OutputListCategoryBridge(final IntSupplier rowSupplier) {
        
        this.rows = rowSupplier;
    }
    
    @Override
    public <G> void initializeGui(final IGuiIngredientGroup<G> group, final JeiCoordinateFixer coordinateFixer) {
    
        for (int i = 0, r = this.rows.getAsInt(); i < r; ++i) {
            
            for (int j = 0; j < 9; ++j) {
                
                group.init(i * 9 + j, false, coordinateFixer.fixX(j * 18 + 1), coordinateFixer.fixY(i * 18 + 1));
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
    public boolean allowCustomTooltips() {
        
        return false;
    }
    
    @Override
    public void drawAdditionalComponent(final MatrixStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer) {}
    
    @Override
    public List<MCTextComponent> getTooltips(final double x, final double y, final IGuiHelper helper, final Consumer<RecipeGraphics> graphicsConsumer) {
        
        return Collections.emptyList();
    }
    
}
