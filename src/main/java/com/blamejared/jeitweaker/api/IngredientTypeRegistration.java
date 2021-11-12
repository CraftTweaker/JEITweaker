package com.blamejared.jeitweaker.api;

import net.minecraft.util.ResourceLocation;

import java.util.function.BiPredicate;
import java.util.function.Function;

public interface IngredientTypeRegistration {
    
    <T, U> IngredientType<T, U> registerIngredientType(
            final ResourceLocation id,
            final Class<T> jeiTweakerType,
            final Class<U> jeiType,
            final Function<T, U> toJeiTypeConverter,
            final Function<U, T> toJeiTweakerTypeConverter,
            final BiPredicate<T, T> matcher
    );
    
}
