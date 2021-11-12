package com.blamejared.jeitweaker.api;

public interface CoordinateFixerRegistration {
    
    <T, U> void registerFixer(final IngredientType<T, U> type, final CoordinateFixer fixer);
}
