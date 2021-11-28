package com.blamejared.jeitweaker.api;

/**
 * Handles the registration of {@link IngredientEnumerator}s.
 *
 * <p>JeiTweaker plugins can obtain an instance of this class through their {@link JeiTweakerPluginProvider}.</p>
 *
 * @since 1.1.0
 */
public interface IngredientEnumeratorRegistration {
    
    /**
     * Registers an {@link IngredientEnumerator} for that specific {@link IngredientType}.
     *
     * <p>Each ingredient type must be associated to one enumerator.</p>
     *
     * <p>Although discouraged, the same enumerator can be used for different ingredient types.</p>
     *
     * @param type The ingredient type for which the enumerator is for.
     * @param enumerator The enumerator for that ingredient type.
     * @param <T> The exposed type of the ingredient type.
     * @param <U> The internal type of the ingredient type.
     * @throws IllegalArgumentException If an enumerator has already been registered for the given ingredient type.
     *
     * @since 1.1.0
     */
    <T, U> void registerEnumerator(final IngredientType<T, U> type, final IngredientEnumerator<T, U> enumerator);
}
