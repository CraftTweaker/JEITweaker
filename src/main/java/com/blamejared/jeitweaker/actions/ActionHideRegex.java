package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.jeitweaker.JEIManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActionHideRegex implements IUndoableAction {
    
    private List<MCItemStackMutable> collectedStacks;
    private final String regex;
    
    public ActionHideRegex(String regex) {
        
        this.regex = regex;
    }
    
    @Override
    public void apply() {
        
        Pattern compile = Pattern.compile(regex);
        collectedStacks = ForgeRegistries.ITEMS.getEntries()
                .stream()
                .filter(entry -> compile.matcher(entry.getKey().getLocation().toString()).matches())
                .map(registryKeyItemEntry -> new ItemStack(registryKeyItemEntry.getValue()))
                .filter(itemStack -> !itemStack.isEmpty())
                .map(MCItemStackMutable::new)
                .collect(Collectors.toList());
        
        JEIManager.HIDDEN_ITEMS.addAll(collectedStacks);
    }
    
    @Override
    public void undo() {
        
        collectedStacks.forEach(JEIManager.HIDDEN_ITEMS::remove);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI hiding all Items from Mod: " + regex;
    }
    
    @Override
    public String describe() {
        
        return "JEI Hiding all Items from by Regex: " + regex;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
