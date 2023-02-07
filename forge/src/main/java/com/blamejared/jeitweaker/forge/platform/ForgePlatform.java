package com.blamejared.jeitweaker.forge.platform;

import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ForgePlatform implements PlatformBridge {
    
    @Override
    public boolean isModLoaded(final String modId) {
        return ModList.get().isLoaded(modId);
    }
    
    @Override
    public boolean isDevEnv() {
        return !FMLEnvironment.production;
    }
    
}
