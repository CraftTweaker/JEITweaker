package com.blamejared.jeitweaker.api;


import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

public final class BuiltinIngredientTypes {
    
    public static final IngredientTypeHolder<IItemStack, ItemStack> ITEM = IngredientTypeHolder.of(
            new ResourceLocation("item"),
            IItemStack.class,
            ItemStack.class,
            IItemStack::getInternal,
            MCItemStack::new,
            IItemStack::matches
    );
    
    public static final IngredientTypeHolder<IFluidStack, FluidStack> FLUID = IngredientTypeHolder.of(
            new ResourceLocation("fluid"),
            IFluidStack.class,
            FluidStack.class,
            IFluidStack::getInternal,
            MCFluidStack::new,
            Objects::equals
    );
}
