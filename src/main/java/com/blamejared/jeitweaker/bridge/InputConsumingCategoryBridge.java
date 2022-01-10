package com.blamejared.jeitweaker.bridge;

import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public final class InputConsumingCategoryBridge implements JeiCategoryPluginBridge {

    private static final class GatherExtraRecipeGraphics implements RecipeGraphics {

        private static final String EXTRA_COMPONENT = "result_text";

        private Component component;

        GatherExtraRecipeGraphics() {

            this.component = null;
        }

        @Override
        public void setExtraComponent(final String key, final Component component) {

            if (EXTRA_COMPONENT.equals(key)) this.component = component;
        }

    }

    private static final int OUTPUT_ANIMATION_LOCATION_X = 97;
    private static final int OUTPUT_ANIMATION_LOCATION_Y = 4;
    private static final int TEXT_LOCATION_Y = 36;
    private static final int TEXT_COLOR = 0xFFE5E5E5;
    private static final int BACKGROUND_WIDTH = 162;
    private static final int TEXT_PADDING = 4;

    private final Pair<JeiDrawable, JeiDrawable> outputExtras;
    private final Component baseExtra;

    public InputConsumingCategoryBridge(final Pair<JeiDrawable, JeiDrawable> outputExtras, final Component baseExtra) {

        this.outputExtras = outputExtras;
        this.baseExtra = baseExtra;
    }

    @Override
    public <G> void initializeGui(final IGuiIngredientGroup<G> group, final CoordinateFixer coordinateFixer) {

        group.init(0, true, coordinateFixer.fixX(41), coordinateFixer.fixY(8));
    }

    @Override
    public int getInputSlotsAmount() {

        return 1;
    }

    @Override
    public int getOutputSlotsAmount() {

        return 0;
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
    public void drawAdditionalComponent(final PoseStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer) {

        this.drawAnimation(poseStack, guiHelper);
        this.drawResultText(poseStack, graphicsConsumer);
    }

    @Override
    public List<Component> getTooltips(double x, double y, IGuiHelper helper, Consumer<RecipeGraphics> graphicsConsumer) {

        return Collections.emptyList();
    }

    private void drawAnimation(final PoseStack poseStack, final IGuiHelper helper) {

        final IDrawable background = this.outputExtras.getFirst().getDrawable(helper);
        final IDrawable animation = this.outputExtras.getSecond().getDrawable(helper);

        this.drawAnimation(poseStack, background, animation);
    }

    private void drawAnimation(final PoseStack poseStack, final IDrawable background, final IDrawable animation) {

        background.draw(poseStack, OUTPUT_ANIMATION_LOCATION_X, OUTPUT_ANIMATION_LOCATION_Y);

        if (animation != null) {

            animation.draw(poseStack, OUTPUT_ANIMATION_LOCATION_X, OUTPUT_ANIMATION_LOCATION_Y);
        }
    }

    private void drawResultText(final PoseStack poseStack, final Consumer<RecipeGraphics> graphicsConsumer) {

        final GatherExtraRecipeGraphics graphics = new GatherExtraRecipeGraphics();
        graphicsConsumer.accept(graphics);
        this.drawResultText(poseStack, this.baseExtra, graphics.component);
    }

    private void drawResultText(final PoseStack poseStack, final Component base, final Component result) {

        if (base == null && result == null) return;

        if (base == null) {

            this.drawResultText(poseStack, result);
        } else if (result == null) {

            this.drawResultText(poseStack, base);
        } else {

            this.drawResultText(poseStack, new TextComponent("").append(base).append(" ").append(result));
        }
    }

    private void drawResultText(final PoseStack poseStack, final Component component) {

        final Font font = Minecraft.getInstance().font;
        final int width = font.width(component); // MCP names are awful, change my mind
        final int x = (BACKGROUND_WIDTH - (TEXT_PADDING * 2) - width) / 2;
        font.drawShadow(poseStack, component, x, TEXT_LOCATION_Y, TEXT_COLOR);
    }

}
