package com.blamejared.jeitweaker.forge.plugin;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.plugin.JeiIngredientTypeRegistration;
import com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPlugin;
import com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPluginProvider;
import mezz.jei.api.forge.ForgeTypes;
import net.minecraftforge.fluids.FluidStack;

@JeiTweakerPlugin(JeiTweakerConstants.MOD_ID + ":forge")
public final class ForgeJeiTweakerPlugin implements JeiTweakerPluginProvider {
    @Override
    public void registerIngredientTypes(final JeiIngredientTypeRegistration registration) {
        registration.registerIngredientType(
                JeiIngredientType.of(JeiTweakerConstants.rl("fluid_stack"), FluidStack.class, IFluidStack.class),
                new FluidStackConverters(),
                ForgeTypes.FLUID_STACK
        );
    }
    
}
