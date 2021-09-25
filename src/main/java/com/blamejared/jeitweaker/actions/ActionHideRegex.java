package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.jeitweaker.plugin.JeiStateManager;
import com.blamejared.jeitweaker.zen.component.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.component.IItemStackExpansions;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ActionHideRegex implements IUndoableAction {

    private final Pattern regex;
    // TODO("Make generic enough so that it can also handle other more generic JEI ingredients")
    private List<JeiIngredient<IItemStack, ItemStack>> collectedStacks;

    public ActionHideRegex(final String regex) {

        this.regex = Pattern.compile(regex);
    }

    @Override
    public void apply() {

        collectedStacks = ForgeRegistries.ITEMS.getEntries()
                .stream()
                .filter(entry -> this.regex.matcher(entry.getKey().getLocation().toString()).matches())
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

        collectedStacks.forEach(JeiStateManager.INSTANCE::show);
    }

    @Override
    public String describeUndo() {

        return "Undoing JEI hiding all Items from Mod: " + this.regex;
    }

    @Override
    public String describe() {

        return "JEI Hiding all Items from by Regex: " + this.regex;
    }

    @Override
    public boolean shouldApplyOn(final LogicalSide side) {

        return !CraftTweakerAPI.isServer();
    }

}
