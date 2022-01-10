package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import net.minecraft.resources.ResourceLocation;

public final class ActionHideRecipe implements IUndoableAction {

    private final ResourceLocation category;
    private final ResourceLocation recipeName;

    public ActionHideRecipe(final ResourceLocation category, final ResourceLocation recipeName) {

        this.category = category;
        this.recipeName = recipeName;
    }

    @Override
    public void apply() {
    
        StateManager.INSTANCE.actionsState().hideRecipe(this.category, this.recipeName);
    }

    @Override
    public void undo() {
    
        StateManager.INSTANCE.actionsState().showRecipe(this.category, this.recipeName);
    }

    @Override
    public String describeUndo() {

        return "Undoing JEI hiding recipe: " + this.recipeName + " in category: " + this.category;
    }

    @Override
    public String describe() {

        return "JEI Hiding recipe: " + this.recipeName + " in category: " + this.category;
    }
    
    @Override
    public boolean shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource source) {
        
        return ScriptLoadingOptions.CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE.equals(source);
    }

}
