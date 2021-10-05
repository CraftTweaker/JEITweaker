package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.jeitweaker.plugin.JeiTweakerIngredientType;
import org.openzen.zencode.java.ZenCodeType;

//@Document("mods/JEI/Component/IItemStackExpansions")
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
@ZenRegister
public final class IItemStackExpansions {
    @ZenCodeType.Caster(implicit = true)
    public static HackyJeiIngredientToMakeZenCodeHappy asJeiIngredient(final IItemStack stack) {
        
        return new JeiIngredient<>(JeiTweakerIngredientType.ITEM, stack);
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static JeiDrawable asJeiDrawable(final IItemStack stack) {
        
        return JeiIngredientExpansions.asJeiDrawable(asJeiIngredient(stack));
    }
}
