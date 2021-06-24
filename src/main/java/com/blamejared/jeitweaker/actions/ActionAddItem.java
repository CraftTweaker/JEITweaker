package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.jeitweaker.JEIManager;

public class ActionAddItem implements IUndoableAction {
    
    private final IItemStack stack;
    
    public ActionAddItem(IItemStack stack) {
        
        this.stack = stack;
    }
    
    @Override
    public void apply() {
        
        JEIManager.CUSTOM_ITEMS.add(stack);
    }
    
    @Override
    public String describe() {
        
        return "Adding custom JEI item: " + stack.getCommandString();
    }
    
    @Override
    public void undo() {
        
        JEIManager.CUSTOM_ITEMS.remove(stack);
    }
    
    @Override
    public String describeUndo() {
    
        return "Undoing addition of custom JEI item: " + stack.getCommandString();
    }
    
}
