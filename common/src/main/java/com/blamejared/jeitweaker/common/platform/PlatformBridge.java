package com.blamejared.jeitweaker.common.platform;

import net.minecraft.Util;

import java.util.ServiceLoader;

public interface PlatformBridge {
    PlatformBridge INSTANCE = Util.make(() -> ServiceLoader.load(PlatformBridge.class).findFirst().orElseThrow());
    
    boolean isModLoaded(final String modId);
    
    boolean isDevEnv();
    
}
