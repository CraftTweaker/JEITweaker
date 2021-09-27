package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import net.minecraftforge.fml.LogicalSide;

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
    public boolean validate(final ILogger logger) {
    
        return this.category.getRecipeValidator().test(this.recipe, logger);
    }
    
    @Override
    public boolean shouldApplyOn(final LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
