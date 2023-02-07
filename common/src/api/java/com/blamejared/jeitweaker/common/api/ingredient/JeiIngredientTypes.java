package com.blamejared.jeitweaker.common.api.ingredient;

import mezz.jei.api.ingredients.IIngredientType;

import java.util.Objects;

public final class JeiIngredientTypes {
    private JeiIngredientTypes() {}
    
    public static <J, Z> IIngredientType<J> toJeiType(final JeiIngredientType<J, Z> type) {
        return Objects.requireNonNull(type, "type").converter().toJeiIngredientType();
    }
}
