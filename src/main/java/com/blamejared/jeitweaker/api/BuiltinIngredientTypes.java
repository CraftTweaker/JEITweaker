package com.blamejared.jeitweaker.api;


import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

/**
 * Stores all {@link IngredientType}s that are provided by JeiTweaker by default.
 *
 * <p>The various ingredients are stored in {@link IngredientTypeHolder}s to avoid out-of-order initialization.
 * For this reason, these ingredients are only safe to dereference after
 * {@link JeiTweakerPluginProvider#registerIngredientTypes(IngredientTypeRegistration)} has been called.</p>
 *
 * @since 1.1.0
 */
public final class BuiltinIngredientTypes {
    
    /**
     * The item ingredient type, representing {@link IItemStack}s.
     *
     * @since 1.1.0
     */
    public static final IngredientTypeHolder<IItemStack, ItemStack> ITEM = IngredientTypeHolder.of(
            new ResourceLocation("item"),
            IItemStack.class,
            ItemStack.class,
            IItemStack::getInternal,
            MCItemStack::new,
            IItemStack::getRegistryName,
            IItemStack::matches
    );
    
    /**
     * The fluid ingredient type, representing {@link IFluidStack}s.
     *
     * @since 1.1.0
     */
    public static final IngredientTypeHolder<IFluidStack, FluidStack> FLUID = IngredientTypeHolder.of(
            new ResourceLocation("fluid"),
            IFluidStack.class,
            FluidStack.class,
            IFluidStack::getInternal,
            MCFluidStack::new,
            IFluidStack::getRegistryName,
            Objects::equals
    );
}
