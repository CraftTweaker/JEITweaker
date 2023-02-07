package com.blamejared.jeitweaker.common.ingredient;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientTypes;

import java.util.Objects;

abstract sealed class SimpleJeiIngredient<J, Z> implements JeiIngredient<J, Z> permits BothJeiIngredient, JeiOnlyJeiIngredient, ZenOnlyJeiIngredient {
    private final JeiIngredientType<J, Z> type;
    
    protected SimpleJeiIngredient(final JeiIngredientType<J, Z> type) {
        this.type = Objects.requireNonNull(type, "type");
    }
    
    @Override
    public final JeiIngredientType<J, Z> type() {
        return this.type;
    }
    
    protected final JeiIngredientConverter<J, Z> converter() {
        return JeiIngredientTypes.converterFor(this.type());
    }
    
    @Override
    public final String toString() {
        return this.converter().toCommandStringFromJei(this.jeiContent());
    }
    
}
