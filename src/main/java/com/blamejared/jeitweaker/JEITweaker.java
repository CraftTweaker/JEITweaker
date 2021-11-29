package com.blamejared.jeitweaker;

import com.blamejared.jeitweaker.implementation.JeiTweakerInitializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JEITweaker.MOD_ID)
public class JEITweaker {
    @SuppressWarnings("SpellCheckingInspection") public static final String MOD_ID = "jeitweaker";
    
    public JEITweaker() {
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupDedicated);
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
        
        MinecraftForge.EVENT_BUS.register(new Events());
        JeiTweakerInitializer.initialize();
    }
    
    private void setupDedicated(final FMLDedicatedServerSetupEvent event) {
        
        // TODO("Remove in 1.17")
        JeiTweakerInitializer.initialize();
    }
}
