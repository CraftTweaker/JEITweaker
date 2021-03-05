package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ZenCodeType.Name("mods.jei.JEI")
@ZenRegister
public class JEIManager {
    
    public static final List<IItemStack> HIDDEN_ITEMS = new ArrayList<>();
    public static final List<IFluidStack> HIDDEN_FLUIDS = new ArrayList<>();
    
    public static final List<String> HIDDEN_RECIPE_CATEGORIES = new ArrayList<>();
    public static final List<Pair<String, String>> HIDDEN_RECIPES = new ArrayList<>();
    
    
    public static final Map<IItemStack, String[]> ITEM_DESCRIPTIONS = new HashMap<>();
    public static final Map<IFluidStack, String[]> FLUID_DESCRIPTIONS = new HashMap<>();
    
    @ZenCodeType.Method
    public static void hideMod(String modid, @ZenCodeType.Optional("(name as string) => {return false;}") IRecipeManager.RecipeFilter exclude) {
        
        CraftTweakerAPI.apply(new IUndoableAction() {
            
            List<MCItemStackMutable> collectedStacks;
            
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
                HIDDEN_ITEMS.addAll(collectedStacks);
            }
            
            @Override
            public void undo() {
                
                collectedStacks.forEach(HIDDEN_ITEMS::remove);
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
                
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
    
    @ZenCodeType.Method
    public static void hideRegex(String regex) {
        
        Pattern compile = Pattern.compile(regex);
        CraftTweakerAPI.apply(new IUndoableAction() {
    
            List<MCItemStackMutable> collectedStacks;
            @Override
            public void apply() {
    
                collectedStacks = ForgeRegistries.ITEMS.getEntries()
                        .stream()
                        .filter(entry -> compile.matcher(entry.getKey().getLocation().toString()).matches())
                        .map(registryKeyItemEntry -> new ItemStack(registryKeyItemEntry.getValue()))
                        .filter(itemStack -> !itemStack.isEmpty())
                        .map(MCItemStackMutable::new)
                        .collect(Collectors.toList());
    
                HIDDEN_ITEMS.addAll(collectedStacks);
            }
    
            @Override
            public void undo() {
        
                collectedStacks.forEach(HIDDEN_ITEMS::remove);
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
                
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
    
    @ZenCodeType.Method
    public static void hideItem(IItemStack stack) {
        
        CraftTweakerAPI.apply(new IUndoableAction() {
            @Override
            public void apply() {
                
                HIDDEN_ITEMS.add(stack);
            }
    
            @Override
            public void undo() {
                HIDDEN_ITEMS.remove(stack);
            }
    
            @Override
            public String describeUndo() {
    
                return "Undoing JEI hiding Item: " + stack.getCommandString();
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
        
        CraftTweakerAPI.apply(new IUndoableAction() {
           
            @Override
            public void apply() {
                
                HIDDEN_FLUIDS.add(stack);
            }
            
            @Override
            public void undo() {
                HIDDEN_FLUIDS.remove(stack);
            }
    
    
            @Override
            public String describeUndo() {
        
                return "Undoing JEI hiding Fluid: " + stack.getCommandString();
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
        
        CraftTweakerAPI.apply(new IUndoableAction() {
           
    
            @Override
            public void apply() {
                
                ITEM_DESCRIPTIONS.put(stack, string);
            }
    
            @Override
            public void undo() {
                ITEM_DESCRIPTIONS.remove(stack);
            }
    
            @Override
            public String describeUndo() {
        
                return "Undoing adding JEI Info for: " + stack.getCommandString();
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
        
        CraftTweakerAPI.apply(new IUndoableAction() {
            
    
            @Override
            public void apply() {
                
                FLUID_DESCRIPTIONS.put(stack, string);
            }
    
            @Override
            public void undo() {
                FLUID_DESCRIPTIONS.remove(stack);
            }
    
            @Override
            public String describeUndo() {
        
                return "Undoing adding JEI Info for: " + stack.getCommandString();
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
        
        CraftTweakerAPI.apply(new IUndoableAction() {
           
    
            @Override
            public void apply() {
                
                HIDDEN_RECIPE_CATEGORIES.add(category);
            }
    
            @Override
            public void undo() {
                HIDDEN_RECIPE_CATEGORIES.remove(category);
            }
    
            @Override
            public String describeUndo() {
    
                return "Undoing JEI hiding Category: " + category;
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
    
    @ZenCodeType.Method
    public static void hideRecipe(String category, String recipeName) {
        
        CraftTweakerAPI.apply(new IUndoableAction() {
    
            @Override
            public void apply() {
                
                HIDDEN_RECIPES.add(Pair.of(category, recipeName));
            }
    
            @Override
            public void undo() {
    
                HIDDEN_RECIPES.removeIf(next -> next.getLeft().equals(category) && next.getRight().equals(recipeName));
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
                
                return !CraftTweaker.serverOverride && side.isClient();
            }
        });
    }
    
}
