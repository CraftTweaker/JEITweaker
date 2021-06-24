package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.jeitweaker.actions.*;
import it.unimi.dsi.fastutil.Stack;
import org.apache.commons.lang3.tuple.Pair;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

@ZenCodeType.Name("mods.jei.JEI")
@ZenRegister
public class JEIManager {
    
    public static final List<IItemStack> HIDDEN_ITEMS = new ArrayList<>();
    public static final List<IFluidStack> HIDDEN_FLUIDS = new ArrayList<>();
    
    public static final List<String> HIDDEN_RECIPE_CATEGORIES = new ArrayList<>();
    public static final List<Pair<String, String>> HIDDEN_RECIPES = new ArrayList<>();
    
    public static final Map<IItemStack, String[]> ITEM_DESCRIPTIONS = new HashMap<>();
    public static final Map<IFluidStack, String[]> FLUID_DESCRIPTIONS = new HashMap<>();
    
    public static final List<IItemStack> CUSTOM_ITEMS = new ArrayList<>();
    
    @ZenCodeType.Method
    public static void addItem(IItemStack stack) {
        
        CraftTweakerAPI.apply(new ActionAddItem(stack));
    }
    
    @ZenCodeType.Method
    public static void hideMod(String modid, @ZenCodeType.Optional("(name as string) => {return false;}") IRecipeManager.RecipeFilter exclude) {
        
        CraftTweakerAPI.apply(new ActionHideMod(modid, exclude));
    }
    
    @ZenCodeType.Method
    public static void hideRegex(String regex) {
        
        CraftTweakerAPI.apply(new ActionHideRegex(regex));
    }
    
    @ZenCodeType.Method
    public static void hideItem(IItemStack stack) {
        
        CraftTweakerAPI.apply(new ActionHideItem(stack));
    }
    
    @ZenCodeType.Method
    public static void hideFluid(IFluidStack stack) {
        
        CraftTweakerAPI.apply(new ActionHideFluid(stack));
    }
    
    @ZenCodeType.Method
    public static void addInfo(IItemStack stack, String[] description) {
        
        CraftTweakerAPI.apply(new ActionAddInfo<>(ITEM_DESCRIPTIONS, stack, description));
    }
    
    @ZenCodeType.Method
    public static void addInfo(IFluidStack stack, String[] description) {
        
        CraftTweakerAPI.apply(new ActionAddInfo<>(FLUID_DESCRIPTIONS, stack, description));
    }
    
    @ZenCodeType.Method
    public static void hideCategory(String category) {
        
        CraftTweakerAPI.apply(new ActionHideCategory(category));
    }
    
    @ZenCodeType.Method
    public static void hideRecipe(String category, String recipeName) {
        
        CraftTweakerAPI.apply(new ActionHideRecipe(category, recipeName));
    }
    
}
