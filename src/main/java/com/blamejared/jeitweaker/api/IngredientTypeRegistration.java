package com.blamejared.jeitweaker.api;

import net.minecraft.util.ResourceLocation;

import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Handles the registration of {@link IngredientType}s.
 *
 * <p>JeiTweaker plugins can obtain an instance of this interface through their {@link JeiTweakerPluginProvider}.</p>
 *
 * @since 1.1.0
 */
public interface IngredientTypeRegistration {
    
    /**
     * Creates an {@link IngredientType} with the given data and registers it in the process.
     *
     * <p>Refer to the documentation of {@code IngredientType} for more information on what the various parameters of
     * this method expect.</p>
     *
     * @param id The unique name that identifies the ingredient type.
     * @param jeiTweakerType The class of the exposed type of the ingredient type.
     * @param jeiType The class of the internal type of the ingredient type.
     * @param toJeiTypeConverter A {@link Function} that accepts an instance of the exposed type and converts it to the
     *                           corresponding instance of the internal type.
     * @param toJeiTweakerTypeConverter A {@link Function} that accepts an instance of the internal type and converts it
     *                                  to the corresponding instance of the exposed type.
     * @param toIdentifierConverter A {@link Function} that accepts an instance of the exposed type and converts it into
     *                              an ID in {@link ResourceLocation} form specific to the given ingredient.
     * @param matcher A {@link BiPredicate} which is used to verify whether two instances of the exposed type match, for
     *                any definition of matching that the ingredient type wants to indicate.
     * @param <T> The type of the exposed type of the ingredient type.
     * @param <U> The type of the internal type of the ingredient type.
     * @return A newly created instance of {@link IngredientType} built with the given information.
     * @throws IllegalArgumentException If any ingredient type with the same ID has already been registered.
     *
     * @see IngredientType
     * @since 1.1.0
     */
    <T, U> IngredientType<T, U> registerIngredientType(
            final ResourceLocation id,
            final Class<T> jeiTweakerType,
            final Class<U> jeiType,
            final Function<T, U> toJeiTypeConverter,
            final Function<U, T> toJeiTweakerTypeConverter,
            final Function<T, ResourceLocation> toIdentifierConverter,
            final BiPredicate<T, T> matcher
    );
    
}
