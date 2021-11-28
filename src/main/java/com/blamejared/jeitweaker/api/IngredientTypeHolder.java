package com.blamejared.jeitweaker.api;

import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Holds an {@link IngredientType} and manages its registration automatically.
 *
 * <p>This ensures that referencing ingredient types is safe at all times, as they can only be queried once they have
 * been registered to JeiTweaker. More-over, it also ties the registration with the type itself, allowing for storage of
 * the ingredient type in constant fields and avoiding {@code null} checks.</p>
 *
 * <p>After being {@linkplain #of(ResourceLocation, Class, Class, Function, Function, Function, BiPredicate) created},
 * an ingredient type has to be registered via the {@link #registerTo(IngredientTypeRegistration)} method. After the
 * registration, the contained {@link IngredientType} can be queried via {@link #get()}.</p>
 *
 * <p>Refer to {@link BuiltinIngredientTypes} for an example on their usage.</p>
 *
 * @param <T> The type of the exposed type of the ingredient type.
 * @param <U> The type of the internal type of the ingredient type.
 *
 * @see IngredientType
 * @since 1.1.0
 */
public final class IngredientTypeHolder<T, U> implements Supplier<IngredientType<T, U>> {
    
    private Function<IngredientTypeRegistration, IngredientType<T, U>> registrationFunction;
    private IngredientType<T, U> ingredient;
    
    private IngredientTypeHolder(final Function<IngredientTypeRegistration, IngredientType<T, U>> registration) {
        this.registrationFunction = registration;
        this.ingredient = null;
    }
    
    /**
     * Creates a new ingredient type holder which will register an {@link IngredientType} based on the given data.
     *
     * <p>Refer to the documentation of {@code IngredientType} and its methods for more information on what are the
     * various parameters required for this method.</p>
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
     * @return An ingredient type holder which manages registration of the given ingredient type.
     *
     * @see IngredientType
     * @since 1.1.0
     */
    public static <T, U> IngredientTypeHolder<T, U> of(
            final ResourceLocation id,
            final Class<T> jeiTweakerType,
            final Class<U> jeiType,
            final Function<T, U> toJeiTypeConverter,
            final Function<U, T> toJeiTweakerTypeConverter,
            final Function<T, ResourceLocation> toIdentifierConverter,
            final BiPredicate<T, T> matcher
    ) {
        
        return new IngredientTypeHolder<>(
                reg -> reg.registerIngredientType(id, jeiTweakerType, jeiType, toJeiTypeConverter, toJeiTweakerTypeConverter, toIdentifierConverter, matcher)
        );
    }
    
    /**
     * Gets the stored ingredient type if it has already been registered.
     *
     * @return The stored ingredient type if it has already been registered.
     * @throws NullPointerException If the attempt to get the ingredient happens before the registration occurred.
     *
     * @since 1.1.0
     */
    @Override
    public IngredientType<T, U> get() {
        return Objects.requireNonNull(this.ingredient, "Queried ingredient too early");
    }
    
    /**
     * Registers the ingredient type of this holder through the given {@link IngredientTypeRegistration}.
     *
     * <p>This method should be called inside the
     * {@link JeiTweakerPluginProvider#registerIngredientTypes(IngredientTypeRegistration)} method in a JeiTweaker
     * plugin, passing it the given instance of {@code IngredientTypeRegistration}.</p>
     *
     * @param registration The ingredient type registration instance this ingredient type should be registered to.
     * @throws IllegalArgumentException If the ingredient type's ID is already known.
     *
     * @since 1.1.0
     */
    public void registerTo(final IngredientTypeRegistration registration) {
        this.ingredient = this.registrationFunction.apply(registration);
        this.registrationFunction = null;
    }
}
