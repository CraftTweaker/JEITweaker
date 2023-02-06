package com.blamejared.jeitweaker.forge.ingredient.builtin;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import net.minecraftforge.fluids.FluidStack;

public final class ForgeJeiIngredientTypes {
    public static final JeiIngredientType<FluidStack, IFluidStack> FLUID_STACK = JeiIngredientType.of(
            JeiTweakerConstants.rl("fluid_stack"),
            FluidStack.class,
            IFluidStack.class,
            new FluidConverters()
    );
    
    private ForgeJeiIngredientTypes() {}
}
