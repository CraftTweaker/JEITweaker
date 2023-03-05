package com.blamejared.jeitweaker.common.ingredient;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;

import java.util.Objects;
import java.util.function.UnaryOperator;

public final class ZenOnlyJeiIngredient<J, Z> extends SimpleJeiIngredient<J, Z> {
    
    private final Z zenIngredient;
    private final UnaryOperator<Z> copier;
    
    ZenOnlyJeiIngredient(final JeiIngredientType<J, Z> type, final Z zenIngredient, final UnaryOperator<Z> copier) {
        super(type);
        this.zenIngredient = zenIngredient;
        this.copier = copier;
    }
    
    public static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final Z zenIngredient) {
        return of(type, zenIngredient, UnaryOperator.identity());
    }
    
    public static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final Z zenIngredient, final UnaryOperator<Z> copier) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(zenIngredient, "zenIngredient");
        Objects.requireNonNull(copier, "copier");
        
        final Z storedZenIngredient = copier.apply(zenIngredient);
        Objects.requireNonNull(storedZenIngredient, "copier.apply(zenIngredient)");
        
        return new ZenOnlyJeiIngredient<>(type, storedZenIngredient, copier);
    }
    
    @Override
    public J jeiContent() {
        return this.converter().toJeiFromZen(this.zenContent());
    }
    
    @Override
    public Z zenContent() {
        return this.copier.apply(this.zenIngredient);
    }
    
    
}
