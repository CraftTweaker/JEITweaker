package com.blamejared.jeitweaker.implementation.state;

import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.api.IngredientEnumerator;
import com.blamejared.jeitweaker.api.IngredientType;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class RegistrationState {
    
    private final Map<ResourceLocation, IngredientType<?, ?>> ingredientTypes;
    private final Map<IngredientType<?, ?>, CoordinateFixer> coordinateFixers;
    private final Map<IngredientType<?, ?>, IngredientEnumerator<?, ?>> ingredientEnumerators;
    
    RegistrationState() {
        
        this.ingredientTypes = new HashMap<>();
        this.coordinateFixers = new HashMap<>();
        this.ingredientEnumerators = new HashMap<>();
    }
    
    public <T, U> void registerIngredientType(final ResourceLocation id, final IngredientType<T, U> ingredientType) {
        
        if (this.ingredientTypes.containsKey(id)) {
            throw new IllegalArgumentException("Ingredient type " + id + " is already known");
        }
        
        this.ingredientTypes.put(id, ingredientType);
    }
    
    public <T, U> void registerCoordinateFixer(final IngredientType<T, U> ingredientType, final CoordinateFixer fixer) {
        
        if (this.coordinateFixers.containsKey(ingredientType)) {
            throw new IllegalArgumentException("Ingredient type " + ingredientType + " is already coordinate-fixed");
        }
        
        this.coordinateFixers.put(ingredientType, fixer);
    }
    
    public <T, U> void registerIngredientEnumerator(final IngredientType<T, U> ingredientType, final IngredientEnumerator<T, U> enumerator) {
        
        if (this.ingredientEnumerators.containsKey(ingredientType)) {
            throw new IllegalArgumentException("Ingredient type " + ingredientType + " already has an enumerator");
        }
        
        this.ingredientEnumerators.put(ingredientType, enumerator);
    }
    
    public Stream<IngredientType<?, ?>> ingredientTypes() {
        
        return this.ingredientTypes.values().stream();
    }
    
    public Stream<Map.Entry<IngredientType<?, ?>, CoordinateFixer>> rawFixers() {
        
        return this.coordinateFixers.entrySet().stream();
    }
    
    public Stream<Map.Entry<IngredientType<?, ?>, IngredientEnumerator<?, ?>>> ingredientEnumerators() {
        
        return this.ingredientEnumerators.entrySet().stream();
    }
}
