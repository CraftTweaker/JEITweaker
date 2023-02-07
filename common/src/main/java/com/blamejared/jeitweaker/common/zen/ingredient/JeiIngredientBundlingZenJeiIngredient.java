package com.blamejared.jeitweaker.common.zen.ingredient;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;

import java.util.Objects;

public final class JeiIngredientBundlingZenJeiIngredient<J, Z> implements ZenJeiIngredient {
    private final JeiIngredient<J, Z> wrapped;
    
    private JeiIngredientBundlingZenJeiIngredient(final JeiIngredient<J, Z> ingredient) {
        this.wrapped = ingredient;
    }
    
    public static <J, Z> ZenJeiIngredient of(final JeiIngredient<J, Z> ingredient) {
        return new JeiIngredientBundlingZenJeiIngredient<>(Objects.requireNonNull(ingredient));
    }
    
    public JeiIngredient<J, Z> unwrap() {
        return this.wrapped;
    }
    
    @Override
    public String getCommandString() {
        return JeiIngredients.toCommandString(this.wrapped);
    }
    
    @Override
    public String toString() {
        return this.getCommandString();
    }
    
}
