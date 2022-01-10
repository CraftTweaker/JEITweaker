package com.blamejared.jeitweaker.bridge;

import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.zen.category.CustomRecipeCategory;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class CustomRecipeCategoryBridge implements JeiCategoryPluginBridge {
    
    private final Collection<CustomRecipeCategory.SlotData> slots;
    private final Collection<CustomRecipeCategory.DrawableData> drawables;
    private final Collection<CustomRecipeCategory.TextData> tips;
    private final Collection<CustomRecipeCategory.TextData> text;
    private final boolean canBeShapeless;
    private final int inputSlots;
    
    public CustomRecipeCategoryBridge(
            final Collection<CustomRecipeCategory.SlotData> slots,
            final Collection<CustomRecipeCategory.DrawableData> drawables,
            final Collection<CustomRecipeCategory.TextData> tips,
            final Collection<CustomRecipeCategory.TextData> text,
            final boolean canBeShapeless
    ) {
        
        this.slots = slots;
        this.drawables = drawables;
        this.tips = tips;
        this.text = text;
        this.canBeShapeless = canBeShapeless;
        this.inputSlots = (int) slots.stream().filter(CustomRecipeCategory.SlotData::isInput).count();
    }
    
    @Override
    public <G> void initializeGui(final IGuiIngredientGroup<G> group, final CoordinateFixer coordinateFixer) {
        
        this.slots.forEach(slot -> {
            
            final int x = coordinateFixer.fixX(slot.coordinates().x());
            final int y = coordinateFixer.fixY(slot.coordinates().y());
            group.init(slot.index(), slot.isInput(), x, y);
        });
    }
    
    @Override
    public int getInputSlotsAmount() {
        
        return this.inputSlots;
    }
    
    @Override
    public int getOutputSlotsAmount() {
        
        return this.slots.size() - this.inputSlots;
    }
    
    @Override
    public boolean allowShapelessMarker() {
        
        return this.canBeShapeless;
    }
    
    @Override
    public boolean allowCustomTooltips() {
        
        return true;
    }
    
    @Override
    public void drawAdditionalComponent(final PoseStack poseStack, final double mouseX, final double mouseY, final IGuiHelper guiHelper, final Consumer<RecipeGraphics> graphicsConsumer) {
        
        this.drawText(poseStack);
        this.drawDrawables(poseStack, guiHelper);
    }
    
    @Override
    public List<Component> getTooltips(final double x, final double y, final IGuiHelper helper, final Consumer<RecipeGraphics> graphicsConsumer) {
        
        return this.tips.stream()
                .filter(it -> this.isInside(it, x, y))
                .map(CustomRecipeCategory.TextData::text)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
    
    private void drawText(final PoseStack poseStack) {
        
        final Font font = Minecraft.getInstance().font;
        this.text.forEach(it -> font.drawShadow(poseStack, it.text().get(0), it.topLeft().x(), it.topLeft()
                .y(), 0xFFFFFF));
    }
    
    private void drawDrawables(final PoseStack poseStack, final IGuiHelper guiHelper) {
        
        this.drawables.forEach(it -> it.drawable()
                .getDrawable(guiHelper)
                .draw(poseStack, it.coordinates().x(), it.coordinates().y()));
    }
    
    private boolean isInside(final CustomRecipeCategory.TextData tipData, final double x, final double y) {
        
        return tipData.topLeft().x() <= x && x <= tipData.activeArea().x() && tipData.topLeft()
                .y() <= y && y <= tipData.activeArea().y();
    }
    
}
