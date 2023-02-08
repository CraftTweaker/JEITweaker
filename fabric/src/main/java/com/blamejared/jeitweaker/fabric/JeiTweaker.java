package com.blamejared.jeitweaker.fabric;

import com.blamejared.jeitweaker.common.JeiTweakerInitializer;
import net.fabricmc.api.ModInitializer;

public final class JeiTweaker implements ModInitializer {
    
    @Override
    public void onInitialize() {
        JeiTweakerInitializer.get().initialize();
    }
    
}
