package com.blamejared.jeitweaker.common.api.zen.ingredient;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.common.api.zen.JeiTweakerZenConstants;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JeiTweaker/API/Ingredient/JeiIngredient")
@ZenCodeType.Name(JeiTweakerZenConstants.INGREDIENT_PACKAGE_NAME + ".JeiIngredient")
@ZenRegister
public interface ZenJeiIngredient extends CommandStringDisplayable {}
