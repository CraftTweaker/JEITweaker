package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import net.minecraftforge.fml.LogicalSide;

import java.util.Map;

public class ActionAddInfo<T extends CommandStringDisplayable> implements IUndoableAction {
    
    private final Map<T, String[]> descriptions;
    private final T stack;
    private final String[] description;
    
    public ActionAddInfo(Map<T, String[]> descriptions, T stack, String[] description) {
        
        this.descriptions = descriptions;
        this.stack = stack;
        this.description = description;
    }
    
    @Override
    public void apply() {
        
        descriptions.put(stack, description);
    }
    
    @Override
    public void undo() {
        
        descriptions.remove(stack);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing adding JEI Info for: " + stack.getCommandString();
    }
    
    @Override
    public String describe() {
        
        return "Adding JEI Info for: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
