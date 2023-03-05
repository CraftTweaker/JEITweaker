package com.blamejared.jeitweaker.common.api.plugin;

/**
 * Identifies a JeiTweaker Plugin.
 *
 * <p>Classes that implement this interface <strong>must</strong> be annotated with {@link JeiTweakerPlugin} to
 * enable automatic discovery by JeiTweaker. Refer to the documentation of the annotation for more details on class
 * structure.</p>
 *
 * <p>All the methods will be invoked automatically by JeiTweaker as needed. Methods should not rely on global state
 * when performing their tasks, as invocation might occur out-of-order or without any particular concurrency
 * guarantees.</p>
 *
 * @see JeiTweakerPlugin
 * @since 4.0.0
 */
public interface JeiTweakerPluginProvider {
    
    /**
     * Manages plugin initialization tasks that might be required prior to the plugin lifecycle.
     *
     * <p>This method is guaranteed to be called before any other registration methods, but after all plugins have
     * been discovered.</p>
     *
     * @since 4.0.0
     */
    default void initialize() {}
    
    /**
     * Manages the registration of {@link com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType}s
     *
     * @param registration The {@link JeiIngredientTypeRegistration} instance to use for ingredient type registration.
     *
     * @see JeiIngredientTypeRegistration
     * @see com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType
     * @since 4.0.0
     */
    default void registerIngredientTypes(final JeiIngredientTypeRegistration registration) {}
}
