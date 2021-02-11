package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.*;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.regex.Pattern;

@ZenCodeType.Name("mods.jei.JEI")
@ZenRegister
public class JEIManager {
    
    public static final List<IItemStack> HIDDEN_ITEMS = new ArrayList<>();
    public static final List<IFluidStack> HIDDEN_FLUIDS = new ArrayList<>();
    
    public static final List<String> HIDDEN_RECIPE_CATEGORIES = new ArrayList<>();
    
    public static final Map<IItemStack, String[]> ITEM_DESCRIPTIONS = new HashMap<>();
    public static final Map<IFluidStack, String[]> FLUID_DESCRIPTIONS = new HashMap<>();
    
    @ZenCodeType.Method
    public static void hideMod(String modid, @ZenCodeType.Optional("(name as string) => {return false;}") IRecipeManager.RecipeFilter exclude) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
               ForgeRegistries.ITEMS.getEntries()
                       .stream()
                       .filter(entry -> entry.getKey().getLocation().getNamespace().equalsIgnoreCase(modid))
                       .filter(entry -> !exclude.test(entry.getKey().getLocation().getPath()))
                       .map(registryKeyItemEntry -> new ItemStack(registryKeyItemEntry.getValue()))
                       .filter(itemStack -> !itemStack.isEmpty())
                       .forEach(stack -> HIDDEN_ITEMS.add(new MCItemStackMutable(stack)));
                
            }
            
            @Override
            public String describe() {
                return "JEI Hiding all Items from Mod: " + modid;
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
    
    @ZenCodeType.Method
    public static void hideRegex(String regex) {
        Pattern compile = Pattern.compile(regex);
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                ForgeRegistries.ITEMS.getEntries()
                        .stream()
                        .filter(entry -> compile.matcher(entry.getKey().getLocation().toString()).matches())
                        .map(registryKeyItemEntry -> new ItemStack(registryKeyItemEntry.getValue()))
                        .filter(itemStack -> !itemStack.isEmpty())
                        .forEach(stack -> HIDDEN_ITEMS.add(new MCItemStackMutable(stack)));
                
            }
            
            @Override
            public String describe() {
                return "JEI Hiding all Items from by Regex: " + regex;
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
    
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
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
    
    @ZenCodeType.Method
    public static void hideFluid(IFluidStack stack) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                HIDDEN_FLUIDS.add(stack);
            }
            
            @Override
            public String describe() {
                return "JEI Hiding Fluid: " + stack.getCommandString();
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
    
    @ZenCodeType.Method
    public static void addInfo(IItemStack stack, String[] string) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                ITEM_DESCRIPTIONS.put(stack, string);
            }
            
            @Override
            public String describe() {
                return "Adding JEI Info for: " + stack.getCommandString();
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
    
    @ZenCodeType.Method
    public static void addInfo(IFluidStack stack, String[] string) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                FLUID_DESCRIPTIONS.put(stack, string);
            }
            
            @Override
            public String describe() {
                return "Adding JEI Info for: " + stack.getCommandString();
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return !CraftTweaker.serverOverride && side.isClient();
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
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
}
