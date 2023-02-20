package com.blamejared.jeitweaker.forge.platform;

import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;

public final class ForgePlatform implements PlatformBridge {
    
    @Override
    public boolean isModLoaded(final String modId) {
        return ModList.get().isLoaded(modId);
    }
    
    @Override
    public boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }
    
    @Override
    public boolean isDevEnv() {
        return !FMLEnvironment.production;
    }
    
}
