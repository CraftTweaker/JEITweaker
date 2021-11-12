package com.blamejared.jeitweaker.implementation.state;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.api.IngredientType;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class RegistrationState {
    
    private final Map<ResourceLocation, IngredientType<?, ?>> ingredientTypes;
    private final Map<IngredientType<?, ?>, CoordinateFixer> coordinateFixers;
    
    RegistrationState() {
        
        this.ingredientTypes = new HashMap<>();
        this.coordinateFixers = new HashMap<>();
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
    
    public Stream<IngredientType<?, ?>> ingredientTypes() {
        
        return this.ingredientTypes.values().stream();
    }
    
    public Stream<Map.Entry<IngredientType<?, ?>, CoordinateFixer>> rawFixers() {
        
        return this.coordinateFixers.entrySet().stream();
    }
}
