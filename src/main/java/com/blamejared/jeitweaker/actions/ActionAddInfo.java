package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.LogicalSide;

public final class ActionAddInfo<T, U> implements IUndoableAction {

    private final JeiIngredient<T, U> ingredient;
    private final Component[] description;

    public ActionAddInfo(final JeiIngredient<T, U> ingredient, final Component... description) {

        this.ingredient = ingredient;
        this.description = description;
    }

    @Override
    public void apply() {

        StateManager.INSTANCE.actionsState().addDescription(this.ingredient, this.description);
    }

    @Override
    public void undo() {
    
        StateManager.INSTANCE.actionsState().removeDescription(this.ingredient);
    }

    @Override
    public String describeUndo() {

        return "Undoing adding JEI Info for: " + this.ingredient.getCommandString();
    }

    @Override
    public String describe() {

        return "Adding JEI Info for: " + this.ingredient.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource source) {
        
        return ScriptLoadingOptions.CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE.equals(source);
    }

}
