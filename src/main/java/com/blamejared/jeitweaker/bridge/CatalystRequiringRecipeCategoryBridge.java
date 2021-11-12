package com.blamejared.jeitweaker.bridge;

import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public final class CatalystRequiringRecipeCategoryBridge implements JeiCategoryPluginBridge {

    private static final class TooltipDetailGatherer implements RecipeGraphics {

        private static final String TIP_ID = "process_detail";

        private MCTextComponent[] tip;

        TooltipDetailGatherer() {

            this.tip = null;
        }

        @Override
        public void addTooltip(final String key, final MCTextComponent... lines) {

            if (TIP_ID.equals(key)) this.tip = lines;
        }

        MCTextComponent[] tip() {

            return this.tip;
        }

    }

    private static final int FIRST_IN_X = 44;
    private static final int FIRST_IN_Y = 3;
    private static final int FIRST_OUT_X = 100;
    private static final int FIRST_OUT_Y = 3;
    private static final int CATALYST_X = 72;
    private static final int CATALYST_Y = 23;
    private static final int ARROW_X = 72;
    private static final int ARROW_Y = 5;
    private static final int ARROW_W = 18;
    private static final int ARROW_H = 13;

    private final JeiDrawable cover;
    private final JeiDrawable catalyst;
    private final int inputs;
    private final int outputs;
    private final int maxInputs;
    private final int maxOutputs;
    private final boolean hasCatalyst;
    private final int firstInput;
    private final int inputsAndCatalyst;

    public CatalystRequiringRecipeCategoryBridge(final JeiDrawable cover, final JeiDrawable catalyst, final int inputs, final int outputs, final int maxInputs, final int maxOutputs) {

        this.cover = cover;
        this.catalyst = catalyst;
        this.inputs = inputs;
        this.outputs = outputs;
        this.maxInputs = maxInputs;
        this.maxOutputs = maxOutputs;
        this.hasCatalyst = this.catalyst == null;
        this.firstInput = (this.hasCatalyst? 1 : 0);
        this.inputsAndCatalyst = this.inputs + this.firstInput;
    }

    @Override
    public <G> void initializeGui(final IGuiIngredientGroup<G> group, final CoordinateFixer coordinateFixer) {

        if (this.hasCatalyst) {

            group.init(0, true, coordinateFixer.fixX(CATALYST_X + 1), coordinateFixer.fixY(CATALYST_Y + 1));
        }

        for (int i = 0; i < this.inputs; ++i) {

            final int index = this.firstInput + i;
            final int x = FIRST_IN_X + 1 - (i * 18);
            group.init(index, true, coordinateFixer.fixX(x), coordinateFixer.fixY(FIRST_IN_Y + 1));
        }

        for (int i = 0; i < this.outputs; ++i) {

            final int index = this.inputsAndCatalyst + i;
            final int x = FIRST_OUT_X + 1 + (i * 18);
            group.init(index, false, coordinateFixer.fixX(x), coordinateFixer.fixY(FIRST_OUT_Y + 1));
        }
    }

    @Override
    public int getInputSlotsAmount() {

        return this.inputsAndCatalyst;
    }

    @Override
    public int getOutputSlotsAmount() {

        return this.outputs;
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
    public void drawAdditionalComponent(final MatrixStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer) {

        this.drawCovers(poseStack, guiHelper);
        this.drawCatalyst(poseStack, guiHelper);
    }

    @Override
    public List<MCTextComponent> getTooltips(final double x, final double y, final IGuiHelper helper, final Consumer<RecipeGraphics> graphicsConsumer) {

        if (this.isInsideArrow(x, y)) {

            return this.gatherArrowTip(graphicsConsumer);
        }

        return Collections.emptyList();
    }

    private void drawCovers(final MatrixStack poseStack, final IGuiHelper helper) {

        final IDrawable cover = this.cover.getDrawable(helper);
        this.drawInputCovers(poseStack, cover);
        this.drawOutputCovers(poseStack, cover);
        this.drawCatalystCover(poseStack, cover);
    }

    private void drawInputCovers(final MatrixStack poseStack, final IDrawable cover) {

        for (int i = this.inputs; i < this.maxInputs; ++i) {

            final int x = FIRST_IN_X - (i * 18);
            cover.draw(poseStack, x, FIRST_IN_Y);
        }
    }

    private void drawOutputCovers(final MatrixStack poseStack, final IDrawable cover) {

        for (int i = this.outputs; i < this.maxOutputs; ++i) {

            final int x = FIRST_OUT_X + (i * 18);
            cover.draw(poseStack, x, FIRST_OUT_Y);
        }
    }

    private void drawCatalystCover(final MatrixStack poseStack, final IDrawable cover) {

        if (this.hasCatalyst) return;

        cover.draw(poseStack, CATALYST_X, CATALYST_Y);
    }

    private void drawCatalyst(final MatrixStack poseStack, final IGuiHelper helper) {

        if (this.hasCatalyst) return;

        final IDrawable drawable = this.catalyst.getDrawable(helper);
        drawable.draw(poseStack, CATALYST_X, CATALYST_Y);
    }

    private boolean isInsideArrow(final double mouseX, final double mouseY) {

        return ARROW_X <= mouseX && mouseX <= ARROW_X + ARROW_W && ARROW_Y <= mouseY && mouseY <= ARROW_Y + ARROW_H;
    }

    private List<MCTextComponent> gatherArrowTip(final Consumer<RecipeGraphics> graphicsConsumer) {

        final TooltipDetailGatherer graphics = new TooltipDetailGatherer();
        graphicsConsumer.accept(graphics);
        final MCTextComponent[] tip = graphics.tip();
        return tip == null? Collections.emptyList() : Arrays.asList(tip);
    }

}
