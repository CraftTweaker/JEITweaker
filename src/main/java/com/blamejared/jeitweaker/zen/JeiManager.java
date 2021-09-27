package com.blamejared.jeitweaker.zen;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.actions.ActionAddInfo;
import com.blamejared.jeitweaker.actions.ActionAddIngredient;
import com.blamejared.jeitweaker.actions.ActionAddRecipeCategory;
import com.blamejared.jeitweaker.actions.ActionHideCategory;
import com.blamejared.jeitweaker.actions.ActionHideIngredient;
import com.blamejared.jeitweaker.actions.ActionHideMod;
import com.blamejared.jeitweaker.actions.ActionHideRecipe;
import com.blamejared.jeitweaker.actions.ActionHideRegex;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.component.IFluidStackExpansions;
import com.blamejared.jeitweaker.zen.component.IItemStackExpansions;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@Document("mods/JEITweaker/JEI")
@ZenCodeType.Name("mods.jei.JEI")
@ZenRegister
public final class JeiManager {
    
    @ZenCodeType.Method
    public static void addCategory(final JeiCategory category) {
        
        CraftTweakerAPI.apply(new ActionAddRecipeCategory(category));
    }
    
    @ZenCodeType.Method
    public static void addDescription(final HackyJeiIngredientToMakeZenCodeHappy ingredient, final MCTextComponent... description) {
        
        CraftTweakerAPI.apply(new ActionAddInfo<>(ingredient.cast(), description));
    }
    
    @ZenCodeType.Method
    public static void addIngredient(final HackyJeiIngredientToMakeZenCodeHappy ingredient) {
        
        CraftTweakerAPI.apply(new ActionAddIngredient<>(ingredient.cast()));
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static void addItem(final IItemStack stack) {
        
        addIngredient(IItemStackExpansions.asJeiIngredient(stack));
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static void addInfo(final IItemStack stack, final String... description) {
        
        addDescription(IItemStackExpansions.asJeiIngredient(stack), Arrays.stream(description).map(MCTextComponent::createTranslationTextComponent).toArray(MCTextComponent[]::new));
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static void addInfo(final IFluidStack stack, final String... description) {
        
        addDescription(IFluidStackExpansions.asJeiIngredient(stack), Arrays.stream(description).map(MCTextComponent::createTranslationTextComponent).toArray(MCTextComponent[]::new));
    }
    
    @ZenCodeType.Method
    public static void hideCategory(final String category) {
        
        CraftTweakerAPI.apply(new ActionHideCategory(new ResourceLocation(category)));
    }
    
    
    @ZenCodeType.Method
    public static void hideIngredient(final HackyJeiIngredientToMakeZenCodeHappy ingredient) {
        
        CraftTweakerAPI.apply(new ActionHideIngredient<>(ingredient.cast()));
    }
    
    @ZenCodeType.Method
    public static void hideMod(final String modId, @ZenCodeType.Optional("(name as string) => {return false;}") final IRecipeManager.RecipeFilter exclude) {
        
        CraftTweakerAPI.apply(new ActionHideMod(modId, exclude));
    }
    
    @ZenCodeType.Method
    public static void hideRecipe(final String categoryId, final String recipeName) {
        
        CraftTweakerAPI.apply(new ActionHideRecipe(new ResourceLocation(categoryId), new ResourceLocation(recipeName)));
    }
    
    @ZenCodeType.Method
    public static void hideRegex(final String regex) {
        
        CraftTweakerAPI.apply(new ActionHideRegex(regex));
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static void hideItem(final IItemStack stack) {
        
        hideIngredient(IItemStackExpansions.asJeiIngredient(stack));
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static void hideFluid(final IFluidStack stack) {
    
        hideIngredient(IFluidStackExpansions.asJeiIngredient(stack));
    }
}
