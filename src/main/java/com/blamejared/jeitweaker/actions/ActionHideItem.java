package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.jeitweaker.JEIManager;
import net.minecraftforge.fml.LogicalSide;

public class ActionHideItem implements IUndoableAction {
    
    private final IItemStack stack;
    
    public ActionHideItem(IItemStack stack) {
        
        this.stack = stack;
    }
    
    @Override
    public void apply() {
        
        JEIManager.HIDDEN_ITEMS.add(stack);
    }
    
    @Override
    public void undo() {
        
        JEIManager.HIDDEN_ITEMS.remove(stack);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI hiding Item: " + stack.getCommandString();
    }
    
    @Override
    public String describe() {
        
        return "JEI Hiding Item: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
