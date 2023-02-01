package com.blamejared.jeitweaker.forge;

import com.blamejared.jeitweaker.common.JeiTweakerInitializer;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import net.minecraftforge.fml.common.Mod;

@Mod(JeiTweakerConstants.MOD_ID)
public final class JeiTweaker {
    public JeiTweaker() {
        JeiTweakerInitializer.get().initialize();
    }
}
