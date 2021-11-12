package com.blamejared.jeitweaker.api;

import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

public final class IngredientTypeHolder<T, U> implements Supplier<IngredientType<T, U>> {
    
    private Function<IngredientTypeRegistration, IngredientType<T, U>> registrationFunction;
    private IngredientType<T, U> ingredient;
    
    private IngredientTypeHolder(final Function<IngredientTypeRegistration, IngredientType<T, U>> registration) {
        this.registrationFunction = registration;
        this.ingredient = null;
    }
    
    public static <T, U> IngredientTypeHolder<T, U> of(
            final ResourceLocation id,
            final Class<T> jeiTweakerType,
            final Class<U> jeiType,
            final Function<T, U> toJeiTypeConverter,
            final Function<U, T> toJeiTweakerTypeConverter,
            final BiPredicate<T, T> matcher
    ) {
        
        return new IngredientTypeHolder<>(
                reg -> reg.registerIngredientType(id, jeiTweakerType, jeiType, toJeiTypeConverter, toJeiTweakerTypeConverter, matcher)
        );
    }
    
    @Override
    public IngredientType<T, U> get() {
        return Objects.requireNonNull(this.ingredient, "Queried ingredient too early");
    }
    
    public void registerTo(final IngredientTypeRegistration registration) {
        this.ingredient = this.registrationFunction.apply(registration);
        this.registrationFunction = null;
    }
}
