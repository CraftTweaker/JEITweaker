package com.blamejared.jeitweaker.forge.ingredient.builtin;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraftforge.fluids.FluidStack;

final class FluidConverters implements JeiIngredientConverter<FluidStack, IFluidStack> {
    @Override
    public JeiIngredientCreator.Creator<FluidStack, IFluidStack> toFullIngredientFromJei(final JeiIngredientCreator.FromJei creator, final FluidStack jeiType) {
        return creator.of(jeiType, FluidStack::copy);
    }
    
    @Override
    public JeiIngredientCreator.Creator<FluidStack, IFluidStack> toFullIngredientFromZen(final JeiIngredientCreator.FromZen creator, final IFluidStack zenType) {
        return creator.of(zenType.asImmutable());
    }
    
    @Override
    public JeiIngredientCreator.Creator<FluidStack, IFluidStack> toFullIngredientFromBoth(
            final JeiIngredientCreator.FromBoth creator,
            final FluidStack jeiType,
            final IFluidStack zenType
    ) {
        return creator.of(jeiType, FluidStack::copy, zenType.asImmutable());
    }
    
    @Override
    public IIngredientType<FluidStack> toJeiIngredientType() {
        return ForgeTypes.FLUID_STACK;
    }
    
    @Override
    public FluidStack toJeiFromZen(final IFluidStack zenType) {
        return zenType.getInternal();
    }
    
    @Override
    public IFluidStack toZenFromJei(final FluidStack jeiType) {
        return IFluidStack.of(jeiType);
    }
    
    @Override
    public String toCommandStringFromZen(final IFluidStack zenType) {
        return zenType.getCommandString();
    }
    
}
