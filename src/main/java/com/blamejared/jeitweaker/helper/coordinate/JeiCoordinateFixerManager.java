package com.blamejared.jeitweaker.helper.coordinate;

import com.blamejared.jeitweaker.plugin.JeiTweakerIngredientType;
import com.mojang.datafixers.util.Pair;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public final class JeiCoordinateFixerManager {
    
    private static final JeiCoordinateFixer IDENTITY = JeiCoordinateFixer.of(IntUnaryOperator.identity());
    private static final Map<JeiTweakerIngredientType<?, ?>, JeiCoordinateFixer> RAW_FIXERS = new HashMap<>();
    
    private final Map<IIngredientType<?>, JeiCoordinateFixer> fixers;
    
    public JeiCoordinateFixerManager(final IIngredientManager manager) {
        
        this.fixers = this.computeFixers(manager);
    }
    
    public static <T, U> void registerRawFixer(final JeiTweakerIngredientType<T, U> type, final JeiCoordinateFixer fixer) {
        
        if (RAW_FIXERS.containsKey(type)) return;
        
        RAW_FIXERS.put(type, fixer);
    }
    
    public JeiCoordinateFixer findFor(final IIngredientType<?> type) {
        
        return this.fixers.getOrDefault(type, IDENTITY);
    }
    
    private Map<IIngredientType<?>, JeiCoordinateFixer> computeFixers(final IIngredientManager manager) {
        
        return RAW_FIXERS.entrySet()
                .stream()
                .map(it -> Pair.of(it.getKey().toJeiType(manager), it.getValue()))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }
}
