package com.blamejared.jeitweaker.plugin;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class JeiCoordinateFixer {
    
    private static final IntUnaryOperator IDENTITY = it -> it;
    private static final Map<JeiTweakerIngredientType<?, ?>, IntUnaryOperator> RAW_FIXERS = Util.make(new HashMap<>(), map -> {
        
        map.put(JeiTweakerIngredientType.ITEM, it -> it - 1);
    });
    
    private final Supplier<Map<IIngredientType<?>, IntUnaryOperator>> fixers;
    
    JeiCoordinateFixer(final IIngredientManager manager) {
    
        this.fixers = Suppliers.memoize(() -> this.computeFixers(manager));
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
