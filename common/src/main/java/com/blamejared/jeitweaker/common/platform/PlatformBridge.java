package com.blamejared.jeitweaker.common.platform;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import net.minecraft.Util;

import java.util.ServiceLoader;

public interface PlatformBridge {
    PlatformBridge INSTANCE = Util.make(() -> ServiceLoader.load(PlatformBridge.class).findFirst().orElseThrow());
    
    JeiIngredientType<?, ?> fluidJeiIngredient();
    
    boolean isModLoaded(final String modId);
}
