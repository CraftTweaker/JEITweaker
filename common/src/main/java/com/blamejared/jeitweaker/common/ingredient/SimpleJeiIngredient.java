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
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof JeiIngredient<?, ?> ingredient)) {
            return false;
        }
        // Note we require only either the jei content or the zen content to be the same. This is because ingredients
        // need not store both types, meaning that one might be created from the other. In case that one of the types
        // does not properly implement equals (i.e. relies on identity), but the other is in fact equal according to
        // some definition, we want to declare the two ingredients as equal. Essentially, one of the two getters might
        // be computing a new object every time that relies on identity for equality, whereas the other implements
        // equals properly.
        return Objects.equals(this.type(), ingredient.type()) &&
                (Objects.equals(this.jeiContent(), ingredient.jeiContent()) || Objects.equals(this.zenContent(), ingredient.zenContent()));
    }
    
    @Override
    public int hashCode() {
        // According to equals, this is the only object that can remain constant and, since we do not know which one
        // of the two objects is equal and #hashCode should be independent of #equals, this is the only realistic
        // approach we can take.
        return Objects.hashCode(this.type());
    }
    
    @Override
    public final String toString() {
        return this.converter().toCommandStringFromJei(this.jeiContent());
    }
    
}
