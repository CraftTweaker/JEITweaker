package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.jeitweaker.state.JeiStateManager;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;
import net.minecraftforge.fml.LogicalSide;

public final class ActionAddInfo<T, U> implements IUndoableAction {
    
    private final JeiIngredient<T, U> ingredient;
    private final String[] description;
    
    public ActionAddInfo(final JeiIngredient<T, U> ingredient, final String... description) {
        
        this.ingredient = ingredient;
        this.description = description;
    }
    
    @Override
    public void apply() {
        
        JeiStateManager.INSTANCE.addDescription(this.ingredient, this.description);
    }
    
    @Override
    public void undo() {
        
        JeiStateManager.INSTANCE.removeDescription(this.ingredient);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing adding JEI Info for: " + this.ingredient.getCommandString();
    }
    
    @Override
    public String describe() {
        
        return "Adding JEI Info for: " + this.ingredient.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
