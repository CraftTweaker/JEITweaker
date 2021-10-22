package com.blamejared.jeitweaker.bridge;

import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.helper.coordinate.JeiCoordinateFixer;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public final class InputConsumingCategoryBridge implements JeiCategoryPluginBridge {
    
    private static final class GatherExtraRecipeGraphics implements RecipeGraphics {
    
        private static final String EXTRA_COMPONENT = "result_text";
        
        private MCTextComponent component;
        
        GatherExtraRecipeGraphics() {
            
            this.component = null;
        }
    
        @Override
        public void setExtraComponent(final String key, final MCTextComponent component) {
            
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
    private final MCTextComponent baseExtra;
    
    public InputConsumingCategoryBridge(final Pair<JeiDrawable, JeiDrawable> outputExtras, final MCTextComponent baseExtra) {
        
        this.outputExtras = outputExtras;
        this.baseExtra = baseExtra;
    }
    
    @Override
    public <G> void initializeGui(final IGuiIngredientGroup<G> group, final JeiCoordinateFixer coordinateFixer) {
        
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
    public void drawAdditionalComponent(final MatrixStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer) {

        this.drawAnimation(poseStack, guiHelper);
        this.drawResultText(poseStack, graphicsConsumer);
    }
    
    @Override
    public List<MCTextComponent> getTooltips(double x, double y, IGuiHelper helper, Consumer<RecipeGraphics> graphicsConsumer) {
        
        return Collections.emptyList();
    }
    
    private void drawAnimation(final MatrixStack poseStack, final IGuiHelper helper) {
        
        final IDrawable background = this.outputExtras.getFirst().getDrawable(helper);
        final IDrawable animation = this.outputExtras.getSecond().getDrawable(helper);
        
        this.drawAnimation(poseStack, background, animation);
    }
    
    private void drawAnimation(final MatrixStack poseStack, final IDrawable background, final IDrawable animation) {
        
        background.draw(poseStack, OUTPUT_ANIMATION_LOCATION_X, OUTPUT_ANIMATION_LOCATION_Y);
        
        if (animation != null) {
            
            animation.draw(poseStack, OUTPUT_ANIMATION_LOCATION_X, OUTPUT_ANIMATION_LOCATION_Y);
        }
    }
    
    private void drawResultText(final MatrixStack poseStack, final Consumer<RecipeGraphics> graphicsConsumer) {
    
        final GatherExtraRecipeGraphics graphics = new GatherExtraRecipeGraphics();
        graphicsConsumer.accept(graphics);
        this.drawResultText(poseStack, this.baseExtra, graphics.component);
    }
    
    private void drawResultText(final MatrixStack poseStack, final MCTextComponent base, final MCTextComponent result) {
    
        if (base == null && result == null) return;
        
        if (base == null) {
        
            this.drawResultText(poseStack, result.getInternal());
        } else if (result == null) {
        
            this.drawResultText(poseStack, base.getInternal());
        } else {
        
            this.drawResultText(poseStack, MCTextComponent.createStringTextComponent("").opShLeft(base).opLShift(" ").opShLeft(result).getInternal());
        }
    }
    
    private void drawResultText(final MatrixStack poseStack, final ITextComponent component) {
        
        final FontRenderer font = Minecraft.getInstance().fontRenderer;
        final int width = font.getStringPropertyWidth(component); // MCP names are awful, change my mind
        final int x = (BACKGROUND_WIDTH - (TEXT_PADDING * 2) - width) / 2;
        font.func_243246_a(poseStack, component, x, TEXT_LOCATION_Y, TEXT_COLOR);
    }
    
}
