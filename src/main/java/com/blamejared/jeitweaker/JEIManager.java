package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraftforge.fml.LogicalSide;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

@ZenCodeType.Name("mods.jei.JEI")
@ZenRegister
public class JEIManager {
    
    public static final List<IItemStack> HIDDEN_ITEMS = new ArrayList<>();
    public static final List<String> HIDDEN_RECIPE_CATEGORIES = new ArrayList<>();
    
    
    @ZenCodeType.Method
    public static void hideItem(IItemStack stack) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                HIDDEN_ITEMS.add(stack);
            }
            
            @Override
            public String describe() {
                return "JEI Hiding Item: " + stack.getCommandString();
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return side.isClient();
            }
        });
    }
    
    @ZenCodeType.Method
    public static void hideCategory(String category) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                HIDDEN_RECIPE_CATEGORIES.add(category);
            }
            
            @Override
            public String describe() {
                return "JEI Hiding Category: " + category;
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return side.isClient();
            }
        });
    }
}
