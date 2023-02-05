package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.jeitweaker.common.api.JeiTweakerApi;

public interface JeiIngredient<J, Z> {
    static <J, Z> JeiIngredient<J, Z> ofZen(final JeiIngredientType<J, Z> type, final Z zenContent) {
        return type.converter().toFullIngredientFromZen(JeiTweakerApi.get().ingredientCreator().fromZen(), zenContent).apply(type);
    }
    
    static <J, Z> JeiIngredient<J, Z> ofJei(final JeiIngredientType<J, Z> type, final J jeiContent) {
        return type.converter().toFullIngredientFromJei(JeiTweakerApi.get().ingredientCreator().fromJei(), jeiContent).apply(type);
    }
    
    static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final J jeiContent, final Z zenContent) {
        return type.converter().toFullIngredientFromBoth(JeiTweakerApi.get().ingredientCreator().fromBoth(), jeiContent, zenContent).apply(type);
    }
    
    JeiIngredientType<J, Z> type();
    J jeiContent();
    Z zenContent();
    
    default JeiIngredientConverter<J, Z> converter() {
        return this.type().converter();
    }
    
    @Override String toString();
}
