package com.blamejared.jeitweaker.common;

import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.blamejared.jeitweaker.common.util.EnvironmentVerifier;

@CraftTweakerPlugin(JeiTweakerConstants.MOD_ID + ":common")
public final class JeiTweakerCraftTweakerPlugin implements ICraftTweakerPlugin {
    
    @Override
    public void initialize() {
        EnvironmentVerifier.scanAndReportEnvironment(JeiTweakerInitializer.get().jeiTweakerLogger());
        JeiTweakerInitializer.get().pluginManager().initializePlugins();
    }
    
}
