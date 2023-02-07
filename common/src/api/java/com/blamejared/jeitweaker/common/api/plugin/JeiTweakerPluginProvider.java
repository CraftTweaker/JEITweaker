package com.blamejared.jeitweaker.common.api.plugin;

public interface JeiTweakerPluginProvider {
    default void registerIngredientTypes(final JeiIngredientTypeRegistration registration) {}
}
