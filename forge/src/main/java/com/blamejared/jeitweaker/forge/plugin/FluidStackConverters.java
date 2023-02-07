package com.blamejared.jeitweaker.forge.plugin;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

final class FluidStackConverters implements JeiIngredientConverter<FluidStack, IFluidStack> {
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
    
    @Override
    @SuppressWarnings("deprecation") // I don't care, I am not using ForgeRegistries
    public ResourceLocation toRegistryNameFromJei(final FluidStack jeiType) {
        return Registry.FLUID.getKey(jeiType.getFluid());
    }
    
}
