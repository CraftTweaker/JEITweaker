package com.blamejared.jeitweaker.common.ingredient;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;

import java.util.Objects;
import java.util.function.UnaryOperator;

public final class JeiOnlyJeiIngredient<J, Z> extends SimpleJeiIngredient<J, Z> {
    
    private final J jeiIngredient;
    private final UnaryOperator<J> copier;
    
    JeiOnlyJeiIngredient(final JeiIngredientType<J, Z> type, final J jeiIngredient, final UnaryOperator<J> copier) {
        super(type);
        this.jeiIngredient = jeiIngredient;
        this.copier = copier;
    }
    
    public static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final J jeiIngredient) {
        return of(type, jeiIngredient, UnaryOperator.identity());
    }
    
    public static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final J jeiIngredient, final UnaryOperator<J> copier) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(jeiIngredient, "jeiIngredient");
        Objects.requireNonNull(copier, "copier");
        return new JeiOnlyJeiIngredient<>(type, jeiIngredient, copier);
    }
    
    @Override
    public J jeiContent() {
        return this.copier.apply(this.jeiIngredient);
    }
    
    @Override
    public Z zenContent() {
        return this.converter().toZenFromJei(this.jeiContent());
    }
    
}
