package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.jeitweaker.state.JeiStateManager;
import com.blamejared.jeitweaker.zen.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.IItemStackExpansions;
import com.blamejared.jeitweaker.zen.JeiIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ActionHideMod implements IUndoableAction {
    
    private final String modId;
    private final IRecipeManager.RecipeFilter exclude;
    // TODO("Make generic enough so that it can also handle other more generic JEI ingredients")
    private List<JeiIngredient<IItemStack, ItemStack>> collectedStacks;
    
    public ActionHideMod(final String modId, final IRecipeManager.RecipeFilter exclude) {
        
        this.modId = modId;
        this.exclude = exclude;
    }
    
    @Override
    public void apply() {
        
        this.collectedStacks = ForgeRegistries.ITEMS.getEntries()
                .stream()
                .filter(entry -> entry.getKey().getLocation().getNamespace().equalsIgnoreCase(this.modId))
                .filter(entry -> !this.exclude.test(entry.getKey().getLocation().getPath()))
                .map(registryKeyItemEntry -> new ItemStack(registryKeyItemEntry.getValue()))
                .filter(itemStack -> !itemStack.isEmpty())
                .map(MCItemStackMutable::new)
                .map(IItemStackExpansions::asJeiIngredient)
                .map(HackyJeiIngredientToMakeZenCodeHappy::<IItemStack, ItemStack>cast) // TODO("Remove")
                .peek(JeiStateManager.INSTANCE::hide)
                .collect(Collectors.toList());
    }
    
    @Override
    public void undo() {
        
        this.collectedStacks.forEach(JeiStateManager.INSTANCE::show);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing JEI hiding all Items from Mod: " + this.modId;
    }
    
    @Override
    public String describe() {
        
        return "JEI Hiding all Items from Mod: " + this.modId;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
