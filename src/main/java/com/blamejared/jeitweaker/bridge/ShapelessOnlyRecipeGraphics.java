package com.blamejared.jeitweaker.bridge;

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
    
}
