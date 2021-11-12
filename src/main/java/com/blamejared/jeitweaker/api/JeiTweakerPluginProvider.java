package com.blamejared.jeitweaker.api;

public interface JeiTweakerPluginProvider {
    
    default void registerIngredientTypes(final IngredientTypeRegistration registration) {}
    
    default void registerCoordinateFixers(final CoordinateFixerRegistration registration) {}
    
}
