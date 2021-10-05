package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

//@Document("mods/JEI/Component/IIngredientExpansions")
@ZenCodeType.Expansion("crafttweaker.api.item.IIngredient")
@ZenRegister
public final class IIngredientExpansions {
    
    @ZenCodeType.Caster(implicit = true)
    public static HackyJeiIngredientToMakeZenCodeHappy[] asJeiIngredientArray(final IIngredient ingredient) {
        
        return Arrays.stream(ingredient.getItems())
                .map(IItemStackExpansions::asJeiIngredient)
                .toArray(HackyJeiIngredientToMakeZenCodeHappy[]::new);
    }
}
