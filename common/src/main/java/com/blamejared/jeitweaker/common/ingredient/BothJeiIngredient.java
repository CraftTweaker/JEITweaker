package com.blamejared.jeitweaker.common.ingredient;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;

import java.util.Objects;
import java.util.function.UnaryOperator;

public final class BothJeiIngredient<J, Z> extends SimpleJeiIngredient<J, Z> {
    private final J jeiIngredient;
    private final Z zenIngredient;
    private final UnaryOperator<J> jeiCopier;
    private final UnaryOperator<Z> zenCopier;
    
    private BothJeiIngredient(
            final JeiIngredientType<J, Z> type,
            final J jeiIngredient,
            final UnaryOperator<J> jeiCopier,
            final Z zenIngredient,
            final UnaryOperator<Z> zenCopier
    ) {
        super(type);
        this.jeiIngredient = jeiIngredient;
        this.zenIngredient = zenIngredient;
        this.jeiCopier = jeiCopier;
        this.zenCopier = zenCopier;
    }
    
    public static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final J jeiIngredient, final Z zenIngredient) {
        return of(type, jeiIngredient, UnaryOperator.identity(), zenIngredient);
    }
    
    public static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final J jeiIngredient, final UnaryOperator<J> jeiCopier, final Z zenIngredient) {
        return of(type, jeiIngredient, jeiCopier, zenIngredient, UnaryOperator.identity());
    }
    
    public static <J, Z> JeiIngredient<J, Z> of(
            final JeiIngredientType<J, Z> type,
            final J jeiIngredient,
            final UnaryOperator<J> jeiCopier,
            final Z zenIngredient,
            final UnaryOperator<Z> zenCopier
    ) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(jeiIngredient, "jeiIngredient");
        Objects.requireNonNull(jeiCopier, "jeiCopier");
        Objects.requireNonNull(zenIngredient, "zenIngredient");
        Objects.requireNonNull(zenCopier, "zenCopier");
        
        final J storedJeiIngredient = jeiCopier.apply(jeiIngredient);
        final Z storedZenIngredient = zenCopier.apply(zenIngredient);
        
        Objects.requireNonNull(storedJeiIngredient, "jeiCopier.apply(jeiIngredient)");
        Objects.requireNonNull(storedZenIngredient, "zenCopier.apply(zenIngredient)");
        return new BothJeiIngredient<>(type, storedJeiIngredient, jeiCopier, storedZenIngredient, zenCopier);
    }
    
    @Override
    public J jeiContent() {
        return this.jeiCopier.apply(this.jeiIngredient);
    }
    
    @Override
    public Z zenContent() {
        return this.zenCopier.apply(this.zenIngredient);
    }
    
}
