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

public final class SimpleInputOutputCategoryBridge implements JeiCategoryPluginBridge {
    
    @Override
    public <G> void initializeGui(final IGuiIngredientGroup<G> group, final JeiCoordinateFixer coordinateFixer) {
        
        group.init(0, true, coordinateFixer.fixX(1), coordinateFixer.fixY(8));
        group.init(1, false, coordinateFixer.fixX(61), coordinateFixer.fixY(8));
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
