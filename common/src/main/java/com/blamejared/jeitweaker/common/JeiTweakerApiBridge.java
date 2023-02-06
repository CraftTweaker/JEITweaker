package com.blamejared.jeitweaker.common;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.jeitweaker.common.api.JeiTweakerApi;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import com.blamejared.jeitweaker.common.ingredient.SimpleJeiIngredientCreator;
import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import com.blamejared.jeitweaker.common.zen.ingredient.JeiIngredientBundlingZenJeiIngredient;

public final class JeiTweakerApiBridge implements JeiTweakerApi {
    
    @Override
    public JeiIngredientCreator ingredientCreator() {
        return SimpleJeiIngredientCreator.get();
    }
    
    @Override
    public JeiIngredientType<?, ?> fluidJeiIngredient() {
        return PlatformBridge.INSTANCE.fluidJeiIngredient();
    }
    
    @Override
    public <J, Z> ZenJeiIngredient ingredientZenFromJei(final JeiIngredient<J, Z> jeiIngredient) {
        return JeiIngredientBundlingZenJeiIngredient.of(jeiIngredient);
    }
    
    @Override
    public <J, Z> JeiIngredient<J, Z> ingredientJeiFromZen(final ZenJeiIngredient zenJeiIngredient) {
        if (zenJeiIngredient instanceof final JeiIngredientBundlingZenJeiIngredient<?, ?> wrapped) {
            return GenericUtil.uncheck(wrapped.unwrap());
        }
        throw new IllegalArgumentException("Illegal ZenJeiIngredient: not obtained through JeiIngredients");
    }
    
}
