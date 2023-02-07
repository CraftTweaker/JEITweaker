package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.jeitweaker.common.api.JeiTweakerApi;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public final class JeiIngredientTypes {
    private JeiIngredientTypes() {}
    
    public static <J, Z> JeiIngredientType<J, Z> findById(final ResourceLocation id) {
        return JeiTweakerApi.get().ingredientTypeFromIdentifier(id);
    }
    
    public static <J, Z> JeiIngredientType<J, Z> fromJeiType(final IIngredientType<J> jeiType) {
        return Objects.requireNonNull(fromJeiTypeOrNull(jeiType), () -> "Unknown type " + jeiType + " (class " + jeiType.getIngredientClass().getName() + ')');
    }
    
    public static <J, Z> JeiIngredientType<J, Z> fromJeiTypeOrNull(final IIngredientType<J> jeiType) {
        return JeiTweakerApi.get().ingredientTypeFromJei(jeiType);
    }
    
    public static <J, Z> JeiIngredientConverter<J, Z> converterFor(final JeiIngredientType<J, Z> type) {
        return JeiTweakerApi.get().ingredientConverterFromIngredientType(type);
    }
    
    public static <J, Z> IIngredientType<J> toJeiType(final JeiIngredientType<J, Z> type) {
        return JeiTweakerApi.get().jeiFromIngredientType(type);
    }
}
