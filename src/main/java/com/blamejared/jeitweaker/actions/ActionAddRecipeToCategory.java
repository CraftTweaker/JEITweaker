package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import org.apache.logging.log4j.Logger;

public final class ActionAddRecipeToCategory implements IRuntimeAction {
    
    private final JeiCategory category;
    private final JeiRecipe recipe;

    public ActionAddRecipeToCategory(final JeiCategory category, final JeiRecipe recipe) {
        
        this.category = category;
        this.recipe = recipe;
    }
    
    @Override
    public void apply() {
        
        this.category.addRecipe(this.recipe);
    }
    
    @Override
    public String describe() {
        
        return "Adding recipe " + this.recipe + " to custom JEI category " + this.category.id();
    }
    
    
    @Override
    public boolean validate(Logger logger) {
        
        return this.category.getRecipeValidator().test(this.recipe, logger);
    }
    
    
    @Override
    public boolean shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource source) {

        return ScriptLoadingOptions.CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE.equals(source);
    }
    
    
}
