package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.jeitweaker.state.JeiStateManager;
import com.blamejared.jeitweaker.zen.JeiIngredient;

public final class ActionAddIngredient<T extends CommandStringDisplayable, U> implements IUndoableAction {
    
    private final JeiIngredient<T, U> ingredient;
    
    public ActionAddIngredient(final JeiIngredient<T, U> ingredient) {
        
        this.ingredient = ingredient;
    }
    
    @Override
    public void apply() {
        
        JeiStateManager.INSTANCE.addCustomIngredient(this.ingredient);
    }
    
    @Override
    public String describe() {
        
        return "Adding custom JEI ingredient: " + this.ingredient.getCommandString();
    }
    
    @Override
    public void undo() {
        
        JeiStateManager.INSTANCE.removeCustomIngredient(this.ingredient);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of custom JEI ingredient: " + this.ingredient.getCommandString();
    }
    
}
