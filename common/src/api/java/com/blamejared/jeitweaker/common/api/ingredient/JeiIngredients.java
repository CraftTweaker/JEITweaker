package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.jeitweaker.common.api.JeiTweakerApi;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import mezz.jei.api.ingredients.IIngredientType;

import java.util.Objects;

public final class JeiIngredients {
    private JeiIngredients() {}
    
    public static <J, Z> IIngredientType<J> jeiIngredientTypeOf(final JeiIngredient<J, Z> jeiIngredient) {
        return JeiIngredientTypes.toJeiType(Objects.requireNonNull(jeiIngredient, "jeiIngredient").type());
    }
    
    public static <J, Z> JeiIngredient<J, Z> toJeiIngredient(final ZenJeiIngredient zenJeiIngredient) {
        return JeiTweakerApi.get().ingredientJeiFromZen(zenJeiIngredient);
    }
    
    public static <J, Z> ZenJeiIngredient toZenIngredient(final JeiIngredient<J, Z> jeiIngredient) {
        return JeiTweakerApi.get().ingredientZenFromJei(jeiIngredient);
    }
    
    public static <J, Z> String toCommandString(final JeiIngredient<J, Z> jeiIngredient) {
        return Objects.requireNonNull(jeiIngredient, "jeiIngredient").toString(); // Must be overridden to return the command string by contract
    }
    
    public static <J, Z> String toRegistryName(final JeiIngredient<J, Z> jeiIngredient) {
        return JeiIngredientTypes.converterFor(Objects.requireNonNull(jeiIngredient, "jeiIngredient").type()).toRegistryNameFromZen(jeiIngredient.zenContent());
    }
}
