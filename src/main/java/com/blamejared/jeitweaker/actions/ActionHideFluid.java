package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.jeitweaker.JEIManager;
import net.minecraftforge.fml.LogicalSide;

public class ActionHideFluid implements IUndoableAction {
    
    
    private final IFluidStack stack;
    
    public ActionHideFluid(IFluidStack stack) {
        
        this.stack = stack;
    }
    
    @Override
    public void apply() {
        
        JEIManager.HIDDEN_FLUIDS.add(stack);
    }
    
    @Override
    public void undo() {
        
        JEIManager.HIDDEN_FLUIDS.remove(stack);
    }
    
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI hiding Fluid: " + stack.getCommandString();
    }
    
    @Override
    public String describe() {
        
        return "JEI Hiding Fluid: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
