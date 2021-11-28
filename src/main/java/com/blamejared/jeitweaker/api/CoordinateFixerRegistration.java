package com.blamejared.jeitweaker.api;

/**
 * Handles the registration of {@link CoordinateFixer}s.
 *
 * <p>JeiTweaker plugins can obtain an instance of this interface through their {@link JeiTweakerPluginProvider}.</p>
 *
 * @since 1.1.0
 */
public interface CoordinateFixerRegistration {
    
    /**
     * Registers a {@link CoordinateFixer} for that specific {@link IngredientType}.
     *
     * <p>Each ingredient type can be associated to at most one coordinate fixer. Multiple fixers can be registered to
     * different ingredient types.</p>
     *
     * @param type The ingredient type for which the coordinate fixer is for.
     * @param fixer The coordinate fixer for that ingredient type.
     * @param <T> The exposed type of the ingredient type.
     * @param <U> The internal type of the ingredient type.
     * @throws IllegalArgumentException If the same ingredient type has already a fixer associated to it.
     *
     * @since 1.1.0
     */
    <T, U> void registerFixer(final IngredientType<T, U> type, final CoordinateFixer fixer);
}
