package com.blamejared.jeitweaker.plugin;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class JeiCoordinateFixer {
    
    private static final IntUnaryOperator IDENTITY = it -> it;
    private static final Map<JeiTweakerIngredientType<?, ?>, IntUnaryOperator> RAW_FIXERS = new HashMap<>();
    
    private final Supplier<Map<IIngredientType<?>, IntUnaryOperator>> fixers;
    
    JeiCoordinateFixer(final IIngredientManager manager) {
    
        this.fixers = Suppliers.memoize(() -> this.computeFixers(manager));
    }
    
    public static <T, U> void registerRawFixer(final JeiTweakerIngredientType<T, U> type, final IntUnaryOperator fixer) {
        
        if (RAW_FIXERS.containsKey(type)) return;
        
        RAW_FIXERS.put(type, fixer);
    }
    
    public IntUnaryOperator findFor(final IIngredientType<?> type) {
        
        return this.fixers.get().getOrDefault(type, IDENTITY);
    }
    
    private Map<IIngredientType<?>, IntUnaryOperator> computeFixers(final IIngredientManager manager) {
        
        return RAW_FIXERS.entrySet()
                .stream()
                .map(it -> Pair.of(it.getKey().toJeiType(manager), it.getValue()))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }
}
