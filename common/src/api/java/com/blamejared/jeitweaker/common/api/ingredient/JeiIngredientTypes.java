package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.jeitweaker.common.api.JeiTweakerApi;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Provides a series of extension-like methods that facilitate operations with {@link JeiIngredientType}s.
 *
 * <p>Refer to the {@link JeiIngredientType} documentation for more specific details.</p>
 *
 * @apiNote This class is compatible with the extension function provided by Manifold.
 *
 * @see JeiIngredientType
 * @since 4.0.0
 */
public final class JeiIngredientTypes {
    private JeiIngredientTypes() {}
    
    /**
     * Attempts to locate a {@link JeiIngredientType} with the given identifier, in {@link ResourceLocation} form.
     *
     * <p>The identifier must have been registered to the registry before this method can be used: in other words, the
     * plugin lifecycle must have completed before results of this method are to be considered accurate.</p>
     *
     * <p>It is the responsibility of the client invoking this method to ensure that the generic types given to it are
     * accurate: the API performs no checking of the specified types before returning the found ingredient type, if
     * any.</p>
     *
     * @param id The unique identifier of the ingredient type to find.
     * @return The corresponding {@link JeiIngredientType}, if it exists.
     * @param <J> The type of JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of script objects that can be stored in an ingredient with this type.
     * @throws NullPointerException If the given identifier is {@code null}.
     * @throws IllegalArgumentException If no identifier with the given name is known to JeiTweaker.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredientType<J, Z> findById(final ResourceLocation id) {
        return JeiTweakerApi.get().ingredientTypeFromIdentifier(id);
    }
    
    /**
     * Attempts to identify the {@link JeiIngredientType} that corresponds to a certain JEI {@link IIngredientType}.
     *
     * <p>If no type exists, then the method will throw an exception. If such behavior is not desired, then refer to
     * {@link #fromJeiTypeOrNull(IIngredientType)}.</p>
     *
     * <p>This method essentially acts as the bridge between the JEI API and the JeiTweaker API. The results returned by
     * this method can be relied upon only after the plugin lifecycle has completed.</p>
     *
     * <p>It is the responsibility of the client invoking this method to ensure that the generic types returned match
     * expectations: the API offers no such guarantees.</p>
     *
     * @param jeiType The {@link IIngredientType} to use for the lookup.
     * @return The {@link JeiIngredientType} that maps the given ingredient type, if it exists.
     * @param <J> The type of JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of script objects that can be stored in an ingredient with this type.
     * @throws NullPointerException If the given argument is {@code null}, or if no {@code JeiIngredientType} is
     * registered as the mapping for the specified {@link IIngredientType}.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredientType<J, Z> fromJeiType(final IIngredientType<J> jeiType) {
        return Objects.requireNonNull(fromJeiTypeOrNull(jeiType), () -> "Unknown type " + jeiType + " (class " + jeiType.getIngredientClass().getName() + ')');
    }
    
    /**
     * Attempts to identify the {@link JeiIngredientType} that corresponds to a certain JEI {@link IIngredientType}.
     *
     * <p>If no type exists, then the method will return {@code null}. If an exception is desired instead, then refer to
     * {@link #fromJeiType(IIngredientType)}. It is guaranteed that, for a {@code null} argument, this method will
     * return {@code null}.</p>
     *
     * <p>This method essentially acts as the bridge between the JEI API and the JeiTweaker API. The results returned by
     * this method can be relied upon only after the plugin lifecycle has completed.</p>
     *
     * <p>It is the responsibility of the client invoking this method to ensure that the generic types returned match
     * expectations: the API offers no such guarantees.</p>
     *
     * @param jeiType The {@link IIngredientType} to use for the lookup, it might be {@code null}.
     * @return The {@link JeiIngredientType} that maps the given ingredient type, if it exists; {@code null} otherwise.
     * @param <J> The type of JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of script objects that can be stored in an ingredient with this type.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredientType<J, Z> fromJeiTypeOrNull(final IIngredientType<J> jeiType) {
        return JeiTweakerApi.get().ingredientTypeFromJei(jeiType);
    }
    
    /**
     * Gets the {@link JeiIngredientConverter} instance for a given {@link JeiIngredientType}.
     *
     * <p>The results of this method are to be considered accurate only after the plugin lifecycle has completed.</p>
     *
     * @param type The ingredient type for which the converter should be obtained.
     * @return The corresponding converter.
     * @param <J> The type of JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of script objects that can be stored in an ingredient with this type.
     * @throws NullPointerException If the given ingredient type is {@code null}.
     * @throws IllegalArgumentException If the given type was not registered during the plugin lifecycle.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredientConverter<J, Z> converterFor(final JeiIngredientType<J, Z> type) {
        return JeiTweakerApi.get().ingredientConverterFromIngredientType(type);
    }
    
    /**
     * Converts the given {@link JeiIngredientType} to a JEI {@link IIngredientType}.
     *
     * <p>This method essentially acts as the bridge between the JeiTweaker API and the JEI API. The results returned by
     * this method can be relied upon only after the plugin lifecycle has completed.</p>
     *
     * @param type The {@link JeiIngredientType} to convert.
     * @return The matching {@link IIngredientType}.
     * @param <J> The type of JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of script objects that can be stored in an ingredient with this type.
     * @throws NullPointerException If the given argument is {@code null}.
     *
     * @since 4.0.0
     */
    public static <J, Z> IIngredientType<J> toJeiType(final JeiIngredientType<J, Z> type) {
        return JeiTweakerApi.get().jeiFromIngredientType(type);
    }
}
