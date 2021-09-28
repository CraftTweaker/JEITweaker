package com.blamejared.jeitweaker;

import com.blamejared.jeitweaker.plugin.JeiCoordinateFixer;
import com.blamejared.jeitweaker.plugin.JeiTweakerIngredientType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JEITweaker.MOD_ID)
public class JEITweaker {
    public static final String MOD_ID = "jeitweaker";
    
    public JEITweaker() {
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
        
        MinecraftForge.EVENT_BUS.register(new Events());
        JeiCoordinateFixer.registerRawFixer(JeiTweakerIngredientType.ITEM, it -> it - 1);
    }
    
}
