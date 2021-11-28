package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;

public final class ActionAddIngredient<T, U> implements IUndoableAction {

    private final JeiIngredient<T, U> ingredient;

    public ActionAddIngredient(final JeiIngredient<T, U> ingredient) {

        this.ingredient = ingredient;
    }

    @Override
    public void apply() {
    
        StateManager.INSTANCE.actionsState().addCustomIngredient(this.ingredient);
    }

    @Override
    public String describe() {

        return "Adding custom JEI ingredient: " + this.ingredient.getCommandString();
    }

    @Override
    public void undo() {
    
        StateManager.INSTANCE.actionsState().removeCustomIngredient(this.ingredient);
    }

    @Override
    public String describeUndo() {

        return "Undoing addition of custom JEI ingredient: " + this.ingredient.getCommandString();
    }

}
