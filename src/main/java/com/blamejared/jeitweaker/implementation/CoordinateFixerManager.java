package com.blamejared.jeitweaker.implementation;

import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public final class CoordinateFixerManager {

    private static final CoordinateFixer IDENTITY = CoordinateFixer.of(IntUnaryOperator.identity());

    private final Map<IIngredientType<?>, CoordinateFixer> fixers;

    private CoordinateFixerManager(final Map<IIngredientType<?>, CoordinateFixer> fixers) {

        this.fixers = fixers;
    }
    
    public static CoordinateFixerManager of(final IIngredientManager manager) {
        
        return new CoordinateFixerManager(computeFixers(manager));
    }
    
    private static Map<IIngredientType<?>, CoordinateFixer> computeFixers(final IIngredientManager manager) {
        
        return StateManager.INSTANCE
                .registrationState()
                .rawFixers()
                .map(it -> new AbstractMap.SimpleImmutableEntry<>(it.getKey().toJeiIngredientType(manager), it.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    public CoordinateFixer findFor(final IIngredientType<?> type) {

        return this.fixers.getOrDefault(type, IDENTITY);
    }
}
