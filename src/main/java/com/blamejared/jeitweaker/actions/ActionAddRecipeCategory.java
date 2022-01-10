package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import net.minecraftforge.fml.LogicalSide;

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
    public boolean shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource source) {
        
        return ScriptLoadingOptions.CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE.equals(source);
    }
}
