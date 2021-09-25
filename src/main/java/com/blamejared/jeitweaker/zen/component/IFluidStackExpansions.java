package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.state.JeiTweakerIngredientType;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEITweaker/IFluidStackExpansions")
@ZenCodeType.Expansion("crafttweaker.api.fluid.IFluidStack")
@ZenRegister
public final class IFluidStackExpansions {
    @ZenCodeType.Caster(implicit = true)
    public static HackyJeiIngredientToMakeZenCodeHappy asJeiIngredient(final IFluidStack stack) {
        return new JeiIngredient<>(JeiTweakerIngredientType.FLUID, stack);
    }
}
