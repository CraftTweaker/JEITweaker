package com.blamejared.jeitweaker.bridge;

import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;

import java.util.List;
import java.util.function.Consumer;

public interface JeiCategoryPluginBridge {

    <G> void initializeGui(final IGuiIngredientGroup<G> group, final CoordinateFixer coordinateFixer);

    int getInputSlotsAmount();

    int getOutputSlotsAmount();

    boolean allowShapelessMarker();

    boolean allowCustomTooltips();

    void drawAdditionalComponent(final MatrixStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer);

    List<MCTextComponent> getTooltips(final double x, final double y, final IGuiHelper helper, final Consumer<RecipeGraphics> graphicsConsumer);
}
