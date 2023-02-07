package com.blamejared.jeitweaker.common.api;

import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;

public interface JeiTweakerApi {
    static JeiTweakerApi get() {
        return ApiBridgeInstanceHolder.get();
    }
    
    JeiIngredientCreator ingredientCreator();
    
    JeiIngredientType<?, ?> fluidJeiIngredient();
    
    <J, Z> ZenJeiIngredient ingredientZenFromJei(final JeiIngredient<J, Z> jeiIngredient);
    <J, Z> JeiIngredient<J, Z> ingredientJeiFromZen(final ZenJeiIngredient zenJeiIngredient);
    
    <T> void enqueueCommand(final JeiCommand<T> command);
}
