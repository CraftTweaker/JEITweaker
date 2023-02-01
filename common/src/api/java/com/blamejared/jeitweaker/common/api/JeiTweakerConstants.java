package com.blamejared.jeitweaker.common.api;

import net.minecraft.resources.ResourceLocation;

public final class JeiTweakerConstants {
    public static final String MOD_ID = "jeitweaker";
    
    private JeiTweakerConstants() {}
    
    public static ResourceLocation rl(final String path) {
        
        return new ResourceLocation(MOD_ID, path);
    }
}
