package com.blamejared.jeitweaker.bridge;

import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;

public final class ShapelessOnlyRecipeGraphics implements RecipeGraphics {
    
    private final Runnable shapelessMaker;
    
    public ShapelessOnlyRecipeGraphics(final Runnable shapelessMaker) {
        
        this.shapelessMaker = shapelessMaker;
    }
    
    @Override
    public void showShapelessMarker() {
        
        this.shapelessMaker.run();
    }
    
    @Override
    public void setExtraComponent(final String key, final MCTextComponent component) {}
    
    @Override
    public void addTooltip(final String key, final MCTextComponent... lines) {}
    
    @Override
    public void addTooltip(final int x, final int y, final int activeAreaWidth, final int activeAreaHeight, final MCTextComponent... lines) {}
}
