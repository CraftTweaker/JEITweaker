package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.jeitweaker.JEIManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class ActionHideMod implements IUndoableAction {
    
    private final String modid;
    private final IRecipeManager.RecipeFilter exclude;
    List<MCItemStackMutable> collectedStacks;
    
    public ActionHideMod(String modid, IRecipeManager.RecipeFilter exclude) {
        
        this.modid = modid;
        this.exclude = exclude;
    }
    
    @Override
    public void apply() {
        
        collectedStacks = ForgeRegistries.ITEMS.getEntries()
                .stream()
                .filter(entry -> entry.getKey().getLocation().getNamespace().equalsIgnoreCase(modid))
                .filter(entry -> !exclude.test(entry.getKey().getLocation().getPath()))
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
        
        return "Undoing JEI hiding all Items from Mod: " + modid;
    }
    
    @Override
    public String describe() {
        
        return "JEI Hiding all Items from Mod: " + modid;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
