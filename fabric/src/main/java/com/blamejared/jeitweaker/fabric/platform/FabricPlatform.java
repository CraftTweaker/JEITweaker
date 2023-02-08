package com.blamejared.jeitweaker.fabric.platform;

import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public final class FabricPlatform implements PlatformBridge {
    private final FabricLoader fabricLoader;
    
    public FabricPlatform() {
        this.fabricLoader = FabricLoader.getInstance();
    }
    
    @Override
    public boolean isModLoaded(final String modId) {
        return this.fabricLoader.isModLoaded(modId);
    }
    
    @Override
    public boolean isClient() {
        return this.fabricLoader.getEnvironmentType() == EnvType.CLIENT;
    }
    
    @Override
    public boolean isDevEnv() {
        return this.fabricLoader.isDevelopmentEnvironment();
    }
    
}
