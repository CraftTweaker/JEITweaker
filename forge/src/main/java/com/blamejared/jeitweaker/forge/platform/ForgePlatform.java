package com.blamejared.jeitweaker.forge.platform;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import com.blamejared.jeitweaker.forge.ingredient.builtin.ForgeJeiIngredientTypes;
import net.minecraftforge.fml.ModList;

public class ForgePlatform implements PlatformBridge {
    
    @Override
    public JeiIngredientType<?, ?> fluidJeiIngredient() {
        return ForgeJeiIngredientTypes.FLUID_STACK;
    }
    
    @Override
    public boolean isModLoaded(final String modId) {
        return ModList.get().isLoaded(modId);
    }
    
}
