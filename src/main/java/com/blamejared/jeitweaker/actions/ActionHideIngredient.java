package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.jeitweaker.plugin.JeiStateManager;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;
import net.minecraftforge.fml.LogicalSide;

public final class ActionHideIngredient<T, U> implements IUndoableAction {
    
    private final JeiIngredient<T, U> ingredient;
    
    public ActionHideIngredient(final JeiIngredient<T, U> ingredient) {
        
        this.ingredient = ingredient;
    }
    
    @Override
    public void apply() {
    
        JeiStateManager.INSTANCE.hide(this.ingredient);
    }
    
    @Override
    public void undo() {
        
        JeiStateManager.INSTANCE.show(this.ingredient);
    }
    
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI hiding ingredient: " + this.ingredient.getCommandString();
    }
    
    @Override
    public String describe() {
        
        return "Hiding Ingredient " + this.ingredient.getCommandString() + " from JEI";
    }
    
    @Override
    public boolean shouldApplyOn(final LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
}
