package com.blamejared.jeitweaker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("jeitweaker")
public class JEITweaker {
    
    public JEITweaker() {
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
        
        MinecraftForge.EVENT_BUS.register(new Events());
    }
    
}
