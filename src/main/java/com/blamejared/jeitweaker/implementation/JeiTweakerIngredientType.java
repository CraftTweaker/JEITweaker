package com.blamejared.jeitweaker.implementation;

import com.blamejared.jeitweaker.api.IngredientType;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiPredicate;
import java.util.function.Function;

public final class JeiTweakerIngredientType<T, U> implements IngredientType<T, U> {
    private final ResourceLocation id;
    private final Class<T> jeiTweakerClassType;
    private final Class<U> internalClassType;
    private final Function<T, U> toInternalConverter;
    private final Function<U, T> toJeiTweakerConverter;
    private final Function<T, ResourceLocation> toIdentifierConverter;
    private final BiPredicate<T, T> matcher;
    private final Function<IIngredientManager, IIngredientType<U>> toJeiTypeConverter;
    
    JeiTweakerIngredientType(final ResourceLocation id, final Class<T> jeiTweakerClassType, final Class<U> internalClassType,
                             final Function<T, U> toInternalConverter, final Function<U, T> toJeiTweakerConverter,
                             final Function<T, ResourceLocation> toIdentifierConverter, final BiPredicate<T, T> matcher,
                             final Function<IIngredientManager, IIngredientType<U>> toJeiTypeConverter) {
        this.id = id;
        this.jeiTweakerClassType = jeiTweakerClassType;
        this.internalClassType = internalClassType;
        this.toInternalConverter = toInternalConverter;
        this.toJeiTweakerConverter = toJeiTweakerConverter;
        this.toIdentifierConverter = toIdentifierConverter;
        this.matcher = matcher;
        this.toJeiTypeConverter = toJeiTypeConverter;
    }
    
    @Override
    public ResourceLocation id() {
        return this.id;
    }
    
    @Override
    public Class<T> jeiTweakerType() {
        return this.jeiTweakerClassType;
    }
    
    @Override
    public Class<U> jeiType() {
        return this.internalClassType;
    }
    
    @Override
    public U toJeiType(final T t) {
        return this.toInternalConverter.apply(t);
    }
    
    @Override
    public T toJeiTweakerType(final U u) {
        return this.toJeiTweakerConverter.apply(u);
    }
    
    @Override
    public IIngredientType<U> toJeiIngredientType(final IIngredientManager manager) {
        return this.toJeiTypeConverter.apply(manager);
    }
    
    @Override
    public ResourceLocation toIngredientIdentifier(final T t) {
        return this.toIdentifierConverter.apply(t);
    }
    
    @Override
    public boolean match(final T a, final T b) {
        return this.matcher.test(a, b);
    }
    
    @Override
    public String toString() {
        return this.id.toString();
    }
    
}
