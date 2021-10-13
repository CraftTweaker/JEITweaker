package com.blamejared.jeitweaker.bridge;

import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;

import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;

public interface JeiCategoryPluginBridge {
    
    <G> void initializeGui(final IGuiIngredientGroup<G> group, final IntUnaryOperator coordinateFixer);
    
    int getInputSlotsAmount();
    
    int getOutputSlotsAmount();
    
    boolean allowShapelessMarker();
    
    void drawAdditionalComponent(final MatrixStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer);
}
