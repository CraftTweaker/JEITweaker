package com.blamejared.jeitweaker.plugin;

import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;
import java.util.List;

public final class JeiTweakerCategory implements IRecipeCategory<JeiTweakerRecipe> {
    
    private final JeiCategory zenCategory;
    private final IJeiHelpers helpers;
    private final JeiCategoryPluginBridge bridge;
    
    JeiTweakerCategory(final JeiCategory zenCategory, final IJeiHelpers helpers) {
        
        this.zenCategory = zenCategory;
        this.helpers = helpers;
        this.bridge = zenCategory.getBridgeCreator().get();
    }
    
    @Override
    public ResourceLocation getUid() {
        
        return this.zenCategory.id();
    }
    
    @Override
    public Class<? extends JeiTweakerRecipe> getRecipeClass() {
        
        return JeiTweakerRecipe.class;
    }
    
    @Deprecated
    @Override
    public String getTitle() {
        
        return this.getTitleAsTextComponent().getString();
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.zenCategory.background().getDrawable(this.helpers.getGuiHelper());
    }
    
    @Override
    public IDrawable getIcon() {
    
        return this.zenCategory.icon().getDrawable(this.helpers.getGuiHelper());
    }
    
    @Override
    public void setIngredients(final JeiTweakerRecipe recipe, final IIngredients ingredients) {
        
        recipe.setIngredients(ingredients);
    }
    
    @Override
    public void setRecipe(final IRecipeLayout recipeLayout, final JeiTweakerRecipe recipe, final IIngredients ingredients) {
        
        final long slotsData = ((long) this.bridge.getOutputSlotsAmount()) << 32 | ((long) this.bridge.getInputSlotsAmount());
        recipe.setRecipe(recipeLayout, this.bridge::initializeGui, slotsData, this.bridge.allowShapelessMarker());
    }
    
    @Override
    public ITextComponent getTitleAsTextComponent() {
        
        return this.zenCategory.name().getInternal();
    }
    
    @Override
    public void draw(final JeiTweakerRecipe recipe, final MatrixStack matrixStack, final double mouseX, final double mouseY) {
        
        this.bridge.drawAdditionalComponent(matrixStack, mouseX, mouseY, recipe::populateGraphics);
    }
    
    @Override
    public List<ITextComponent> getTooltipStrings(final JeiTweakerRecipe recipe, final double mouseX, final double mouseY) {
        
        return Collections.emptyList();
    }
    
    @Override
    public boolean handleClick(final JeiTweakerRecipe recipe, final double mouseX, final double mouseY, final int mouseButton) {
        
        return false;
    }
    
    @Override
    public boolean isHandled(final JeiTweakerRecipe recipe) {
        
        return recipe.getOwningCategory() == this.zenCategory;
    }
    
}
