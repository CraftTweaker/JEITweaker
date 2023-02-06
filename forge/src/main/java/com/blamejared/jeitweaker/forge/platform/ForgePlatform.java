package com.blamejared.jeitweaker.forge.platform;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import com.blamejared.jeitweaker.forge.ingredient.builtin.ForgeJeiIngredientTypes;

public class ForgePlatform implements PlatformBridge {
    
    @Override
    public JeiIngredientType<?, ?> fluidJeiIngredient() {
        return ForgeJeiIngredientTypes.FLUID_STACK;
    }
    
}
