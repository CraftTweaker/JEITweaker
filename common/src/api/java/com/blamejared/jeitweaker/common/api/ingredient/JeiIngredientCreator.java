package com.blamejared.jeitweaker.common.api.ingredient;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface JeiIngredientCreator {
    @FunctionalInterface
    interface Creator<J, Z> extends Function<JeiIngredientType<J, Z>, JeiIngredient<J, Z>> {}
    
    interface FromZen {
        <J, Z> Creator<J, Z> of(final Z zenContent);
        <J, Z> Creator<J, Z> of(final Z zenContent, final UnaryOperator<Z> copier);
    }
    
    interface FromJei {
        <J, Z> Creator<J, Z> of(final J jeiContent);
        <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> copier);
    }
    
    interface FromBoth {
        <J, Z> Creator<J, Z> of(final J jeiContent, final Z zenContent);
        <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> jeiCopier, final Z zenContent);
        <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> jeiCopier, final Z zenContent, final UnaryOperator<Z> zenCopier);
    }
    
    FromZen fromZen();
    FromJei fromJei();
    FromBoth fromBoth();
}
