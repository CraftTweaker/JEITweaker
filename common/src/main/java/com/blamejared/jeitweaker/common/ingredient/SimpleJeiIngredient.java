package com.blamejared.jeitweaker.common.ingredient;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;

import java.util.Objects;

abstract sealed class SimpleJeiIngredient<J, Z> implements JeiIngredient<J, Z> permits BothJeiIngredient, JeiOnlyJeiIngredient, ZenOnlyJeiIngredient {
    private final JeiIngredientType<J, Z> type;
    
    protected SimpleJeiIngredient(final JeiIngredientType<J, Z> type) {
        this.type = Objects.requireNonNull(type, "type");
    }
    
    @Override
    public JeiIngredientType<J, Z> type() {
        return this.type;
    }
    
    @Override
    public final String toString() {
        return this.converter().toCommandStringFromJei(this.jeiContent());
    }
    
}
