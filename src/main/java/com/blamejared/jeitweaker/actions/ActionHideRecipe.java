package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.jeitweaker.JEIManager;
import net.minecraftforge.fml.LogicalSide;
import org.apache.commons.lang3.tuple.Pair;

public class ActionHideRecipe implements IUndoableAction {
    
    
    private final String category;
    private final String recipeName;
    
    public ActionHideRecipe(String category, String recipeName) {
        
        this.category = category;
        
        this.recipeName = recipeName;
    }
    
    @Override
    public void apply() {
        
        JEIManager.HIDDEN_RECIPES.add(Pair.of(category, recipeName));
    }
    
    @Override
    public void undo() {
        
        JEIManager.HIDDEN_RECIPES.removeIf(next -> next.getLeft().equals(category) && next.getRight()
                .equals(recipeName));
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI hiding recipe: " + recipeName + " in category: " + category;
    }
    
    @Override
    public String describe() {
        
        return "JEI Hiding recipe: " + recipeName + " in category: " + category;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
