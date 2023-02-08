package com.blamejared.jeitweaker.common.zen;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.common.action.AddIngredientAction;
import com.blamejared.jeitweaker.common.action.AddIngredientInformationAction;
import com.blamejared.jeitweaker.common.action.HideCategoryAction;
import com.blamejared.jeitweaker.common.action.HideIngredientsByRegexAction;
import com.blamejared.jeitweaker.common.action.HideIngredientAction;
import com.blamejared.jeitweaker.common.action.HideModIngredientsAction;
import com.blamejared.jeitweaker.common.action.HideRecipeAction;
import com.blamejared.jeitweaker.common.api.zen.JeiTweakerZenConstants;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Predicate;

@Document("mods/JeiTweaker/API/Jei")
@ZenCodeType.Name(JeiTweakerZenConstants.ZEN_MODULE_ROOT + ".Jei")
@ZenRegister
public final class JeiTweaker {
    @ZenCodeType.Method
    public static void addIngredient(final ZenJeiIngredient ingredient) {
        CraftTweakerAPI.apply(AddIngredientAction.of(ingredient));
    }
    
    @ZenCodeType.Method
    public static void addIngredientInformation(final ZenJeiIngredient ingredient, final Component... info) {
        CraftTweakerAPI.apply(AddIngredientInformationAction.of(ingredient, info));
    }
    
    @ZenCodeType.Method
    public static void hideCategory(final ResourceLocation category) {
        CraftTweakerAPI.apply(HideCategoryAction.of(category));
    }
    
    @ZenCodeType.Method
    public static void hideIngredient(final ZenJeiIngredient ingredient) {
        CraftTweakerAPI.apply(HideIngredientAction.of(ingredient));
    }
    
    @ZenCodeType.Method
    public static void hideModIngredients(final String modId, @ZenCodeType.Optional("(name as string) => { return false; }") final Predicate<String> exclusionFilter) {
        CraftTweakerAPI.apply(HideModIngredientsAction.of(modId, exclusionFilter));
    }
    
    @ZenCodeType.Method
    public static void hideIngredientsByRegex(final String pattern) {
        CraftTweakerAPI.apply(HideIngredientsByRegexAction.of(pattern));
    }
    
    @ZenCodeType.Method
    public static void hideRecipe(final ResourceLocation category, final ResourceLocation recipe) {
        CraftTweakerAPI.apply(HideRecipeAction.of(category, recipe));
    }
}
