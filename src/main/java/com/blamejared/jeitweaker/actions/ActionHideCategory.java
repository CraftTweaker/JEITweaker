package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.jeitweaker.state.JeiStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;

public final class ActionHideCategory implements IUndoableAction {
    
    private final ResourceLocation category;
    
    public ActionHideCategory(final ResourceLocation category) {
        
        this.category = category;
    }
    
    @Override
    public void apply() {
        
        JeiStateManager.INSTANCE.hideRecipeCategory(this.category);
    }
    
    @Override
    public void undo() {
        
        JeiStateManager.INSTANCE.showRecipeCategory(this.category);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI Category hiding: " + this.category;
    }
    
    @Override
    public String describe() {
        
        return "Hiding Category '" + this.category + "' from JEI";
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
