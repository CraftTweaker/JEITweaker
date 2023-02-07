package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.jeitweaker.common.api.JeiTweakerApi;

public interface JeiIngredient<J, Z> {
    static <J, Z> JeiIngredient<J, Z> ofZen(final JeiIngredientType<J, Z> type, final Z zenContent) {
        return JeiIngredientTypes.converterFor(type).toFullIngredientFromZen(JeiTweakerApi.get().ingredientCreator().fromZen(), zenContent).apply(type);
    }
    
    static <J, Z> JeiIngredient<J, Z> ofJei(final JeiIngredientType<J, Z> type, final J jeiContent) {
        return JeiIngredientTypes.converterFor(type).toFullIngredientFromJei(JeiTweakerApi.get().ingredientCreator().fromJei(), jeiContent).apply(type);
    }
    
    static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final J jeiContent, final Z zenContent) {
        return JeiIngredientTypes.converterFor(type).toFullIngredientFromBoth(JeiTweakerApi.get().ingredientCreator().fromBoth(), jeiContent, zenContent).apply(type);
    }
    
    JeiIngredientType<J, Z> type();
    J jeiContent();
    Z zenContent();
    
    @Override String toString();
}
