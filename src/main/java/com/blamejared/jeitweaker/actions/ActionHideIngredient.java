package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;

public final class ActionHideIngredient<T, U> implements IUndoableAction {
    
    private final JeiIngredient<T, U> ingredient;
    
    public ActionHideIngredient(final JeiIngredient<T, U> ingredient) {
        
        this.ingredient = ingredient;
    }
    
    @Override
    public void apply() {
        
        StateManager.INSTANCE.actionsState().hide(this.ingredient);
    }
    
    @Override
    public void undo() {
        
        StateManager.INSTANCE.actionsState().show(this.ingredient);
    }
    
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI hiding ingredient: " + this.ingredient.getCommandString();
    }
    
    @Override
    public String describe() {
        
        return "Hiding Ingredient " + this.ingredient.getCommandString() + " from JEI";
    }
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source) {
        
        return Services.DISTRIBUTION.getDistributionType().isClient();
    }
    
}
