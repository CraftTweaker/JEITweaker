package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.jeitweaker.state.JeiStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;

public final class ActionHideRecipe implements IUndoableAction {
    
    private final ResourceLocation category;
    private final ResourceLocation recipeName;
    
    public ActionHideRecipe(final ResourceLocation category, final ResourceLocation recipeName) {
        
        this.category = category;
        this.recipeName = recipeName;
    }
    
    @Override
    public void apply() {
        
        JeiStateManager.INSTANCE.hideRecipe(this.category, this.recipeName);
    }
    
    @Override
    public void undo() {
        
        JeiStateManager.INSTANCE.showRecipe(this.category, this.recipeName);
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
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
