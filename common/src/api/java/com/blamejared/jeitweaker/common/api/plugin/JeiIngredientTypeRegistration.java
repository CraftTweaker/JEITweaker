package com.blamejared.jeitweaker.common.api.plugin;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import mezz.jei.api.ingredients.IIngredientType;

public interface JeiIngredientTypeRegistration {
    <J, Z> void registerIngredientType(final JeiIngredientType<J, Z> type, final JeiIngredientConverter<J, Z> converter, final IIngredientType<J> jeiType);
}
