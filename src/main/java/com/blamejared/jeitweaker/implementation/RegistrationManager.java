package com.blamejared.jeitweaker.implementation;

import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.api.CoordinateFixerRegistration;
import com.blamejared.jeitweaker.api.IngredientType;
import com.blamejared.jeitweaker.api.IngredientTypeRegistration;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiPredicate;
import java.util.function.Function;

final class RegistrationManager implements CoordinateFixerRegistration, IngredientTypeRegistration {
    
    @Override
    public <T, U> void registerFixer(final IngredientType<T, U> type, final CoordinateFixer fixer) {
        
        StateManager.INSTANCE.registrationState().registerCoordinateFixer(type, fixer);
    }
    
    @Override
    public <T, U> IngredientType<T, U> registerIngredientType(
            final ResourceLocation id,
            final Class<T> jeiTweakerType,
            final Class<U> jeiType,
            final Function<T, U> toJeiTypeConverter,
            final Function<U, T> toJeiTweakerTypeConverter,
            final BiPredicate<T, T> matcher
    ) {
        
        final JeiTweakerIngredientType<T, U> type = new JeiTweakerIngredientType<>(
                id,
                jeiTweakerType,
                jeiType,
                toJeiTypeConverter,
                toJeiTweakerTypeConverter,
                matcher,
                manager -> manager.getIngredientType(jeiType)
        );
        StateManager.INSTANCE.registrationState().registerIngredientType(id, type);
        return type;
    }
    
}