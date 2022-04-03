package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import com.blamejared.jeitweaker.zen.category.JeiCategory;

public final class ActionAddRecipeCategory implements IUndoableAction {
    
    private final JeiCategory category;
    
    public ActionAddRecipeCategory(final JeiCategory category) {
        
        this.category = category;
    }
    
    @Override
    public void apply() {
        
        StateManager.INSTANCE.actionsState().addCustomCategory(this.category);
    }
    
    @Override
    public void undo() {
        
        StateManager.INSTANCE.actionsState().removeCustomCategory(this.category);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of custom JEI category " + this.category.id();
    }
    
    @Override
    public String describe() {
        
        return "Adding custom JEI category " + this.category.id();
    }
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source) {
        
        return Services.DISTRIBUTION.getDistributionType().isClient();
    }
    
}
