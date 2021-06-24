package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.jeitweaker.JEIManager;
import net.minecraftforge.fml.LogicalSide;

public class ActionHideCategory implements IUndoableAction {
    
    private final String category;
    
    public ActionHideCategory(String category) {
        
        this.category = category;
    }
    
    @Override
    public void apply() {
        
        JEIManager.HIDDEN_RECIPE_CATEGORIES.add(category);
    }
    
    @Override
    public void undo() {
        
        JEIManager.HIDDEN_RECIPE_CATEGORIES.remove(category);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI hiding Category: " + category;
    }
    
    @Override
    public String describe() {
        
        return "JEI Hiding Category: " + category;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
