package com.blamejared.jeitweaker.api;

/**
 * Identifies a JeiTweaker plugin.
 *
 * <p>JeiTweaker plugins are the way other mods can conditionally communicate with JeiTweaker and register elements
 * that may be required to ease compatibility between JeiTweaker and themselves. A JeiTweaker plugin is not the only
 * element mods may want to interface with for the API, <em>at the moment</em>. Refer to the package documentation for
 * more details on the current state of the API.</p>
 *
 * <p>Implementations of this class that want to be loaded by JeiTweaker must be annotated with the
 * {@link JeiTweakerPlugin} annotation. Refer to the documentation of that annotation for more details.</p>
 *
 * @since 1.1.0
 */
public interface JeiTweakerPluginProvider {
    
    /**
     * Handles the registration of additional {@link IngredientType}s to JeiTweaker.
     *
     * @param registration The handler that manages ingredient type registration and creation.
     *
     * @since 1.1.0
     */
    default void registerIngredientTypes(final IngredientTypeRegistration registration) {}
    
    /**
     * Handles the registration of additional {@link CoordinateFixer}s to JeiTweaker.
     *
     * @param registration The handler that manages coordinate fixer registration.
     *
     * @since 1.1.0
     */
    default void registerCoordinateFixers(final CoordinateFixerRegistration registration) {}
    
    /**
     * Handles the registration of {@link IngredientEnumerator}s to JeiTweaker.
     *
     * @param registration The handler that manages ingredient enumerator registration.
     *
     * @since 1.1.0
     */
    default void registerIngredientEnumerators(final IngredientEnumeratorRegistration registration) {}
}
