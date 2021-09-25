package com.blamejared.jeitweaker.plugin;

import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;

public final class JeiTweakerRecipe {
    private final JeiRecipe recipe;
    // TODO("Extras")
    
    JeiTweakerRecipe(final JeiRecipe recipe) {
        
        this.recipe = recipe;
    }
    
    JeiCategory getOwningCategory() {
        
        return this.recipe.owningCategory();
    }
}
