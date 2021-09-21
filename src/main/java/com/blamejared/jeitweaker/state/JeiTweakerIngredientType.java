package com.blamejared.jeitweaker.state;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

public final class JeiTweakerIngredientType<T, U> {
    public static final JeiTweakerIngredientType<IItemStack, ItemStack> ITEM = of(IItemStack.class, ItemStack.class, IItemStack::getInternal, MCItemStack::new, IItemStack::matches);
    public static final JeiTweakerIngredientType<IFluidStack, FluidStack> FLUID = of(IFluidStack.class, FluidStack.class, IFluidStack::getInternal, MCFluidStack::new, Objects::equals);
    
    private static final Collection<JeiTweakerIngredientType<?, ?>> VALUES = new ArrayList<>();
    private static final Collection<JeiTweakerIngredientType<?, ?>> UNMODIFIABLE_VALUES_VIEW = Collections.unmodifiableCollection(VALUES);
    
    private final Class<T> jeiTweakerClassType;
    private final Class<U> internalClassType;
    private final Function<T, U> toInternalConverter;
    private final Function<U, T> toJeiTweakerConverter;
    private final BiPredicate<T, T> matcher;
    private final Function<IIngredientManager, IIngredientType<U>> toJeiTypeConverter;
    
    private JeiTweakerIngredientType(final Class<T> jeiTweakerClassType, final Class<U> internalClassType,
                                     final Function<T, U> toInternalConverter, final Function<U, T> toJeiTweakerConverter,
                                     final BiPredicate<T, T> matcher, final Function<IIngredientManager, IIngredientType<U>> toJeiTypeConverter) {
        
        this.jeiTweakerClassType = jeiTweakerClassType;
        this.internalClassType = internalClassType;
        this.toInternalConverter = toInternalConverter;
        this.toJeiTweakerConverter = toJeiTweakerConverter;
        this.matcher = matcher;
        this.toJeiTypeConverter = toJeiTypeConverter;
        
        VALUES.add(this);
    }
    
    public static Collection<JeiTweakerIngredientType<?, ?>> values() {
        return UNMODIFIABLE_VALUES_VIEW;
    }
    
    private static <T, U> JeiTweakerIngredientType<T, U> of(
            final Class<T> jeiTweakerType,
            final Class<U> internalType,
            final Function<T, U> toInternal,
            final Function<U, T> toJeiTweaker,
            final BiPredicate<T, T> matcher
    ) {
        return new JeiTweakerIngredientType<>(jeiTweakerType, internalType, toInternal, toJeiTweaker, matcher, manager -> manager.getIngredientType(internalType));
    }
    
    public Class<T> getJeiTweakerType() {
        return this.jeiTweakerClassType;
    }
    
    public Class<U> getInternalType() {
        return this.internalClassType;
    }
    
    public U toInternal(final T t) {
        return this.toInternalConverter.apply(t);
    }
    
    public T toJeiTweaker(final U u) {
        return this.toJeiTweakerConverter.apply(u);
    }
    
    public IIngredientType<U> toJeiType(final IIngredientManager manager) {
        return this.toJeiTypeConverter.apply(manager);
    }
    
    public boolean match(final T a, final T b) {
        return this.matcher.test(a, b);
    }
}
