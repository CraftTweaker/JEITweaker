package com.blamejared.jeitweaker.jei;

import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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
    @SuppressWarnings("removal")
    public ResourceLocation getUid() {
        
        return this.zenCategory.id();
    }
    
    @Override
    @SuppressWarnings("removal")
    public Class<? extends JeiTweakerRecipe> getRecipeClass() {
        
        return JeiTweakerRecipe.class;
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
    public Component getTitle() {
        
        return this.zenCategory.name();
    }
    
    @Override
    public void draw(final JeiTweakerRecipe recipe, final PoseStack matrixStack, final double mouseX, final double mouseY) {
        
        this.bridge.drawAdditionalComponent(matrixStack, mouseX, mouseY, this.helpers.getGuiHelper(), recipe::populateGraphics);
    }
    
    @Override
    public List<Component> getTooltipStrings(final JeiTweakerRecipe recipe, final double mouseX, final double mouseY) {
        
        final List<Component> categoryTips = this.bridge.getTooltips(mouseX, mouseY, this.helpers.getGuiHelper(), recipe::populateGraphics);
        final List<Component> recipeTips = this.bridge.allowCustomTooltips() ? recipe.getTooltips(mouseX, mouseY) : Collections.emptyList();
        
        if(categoryTips.isEmpty() && recipeTips.isEmpty()) {
            return Collections.emptyList();
        }
        
        final Stream<Component> stream;
        
        if(categoryTips.isEmpty()) {
            
            stream = recipeTips.stream();
        } else if(recipeTips.isEmpty()) {
            
            stream = categoryTips.stream();
        } else {
            
            stream = Stream.concat(categoryTips.stream(), recipeTips.stream());
        }
        
        return stream.toList();
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
