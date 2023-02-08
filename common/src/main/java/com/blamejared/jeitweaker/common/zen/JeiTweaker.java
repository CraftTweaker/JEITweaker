package com.blamejared.jeitweaker.common.zen;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.common.action.AddIngredientAction;
import com.blamejared.jeitweaker.common.action.HideByRegexAction;
import com.blamejared.jeitweaker.common.action.HideIngredientAction;
import com.blamejared.jeitweaker.common.action.HideModAction;
import com.blamejared.jeitweaker.common.api.zen.JeiTweakerZenConstants;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
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
    public static void hideIngredient(final ZenJeiIngredient ingredient) {
        CraftTweakerAPI.apply(HideIngredientAction.of(ingredient));
    }
    
    @ZenCodeType.Method
    public static void hideMod(final String modId, @ZenCodeType.Optional("(name as string) => { return false; }") final Predicate<String> exclusionFilter) {
        CraftTweakerAPI.apply(HideModAction.of(modId, exclusionFilter));
    }
    
    @ZenCodeType.Method
    public static void hideByRegex(final String pattern) {
        CraftTweakerAPI.apply(HideByRegexAction.of(pattern));
    }
}