package com.blamejared.jeitweaker.common;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.jeitweaker.common.command.CommandManager;
import com.blamejared.jeitweaker.common.plugin.core.PluginManager;
import com.blamejared.jeitweaker.common.registry.JeiTweakerRegistries;
import org.apache.logging.log4j.Logger;

public final class JeiTweakerInitializer {
    private static final JeiTweakerInitializer INSTANCE = new JeiTweakerInitializer();
    
    private final Logger jeiTweakerLogger;
    private final JeiTweakerRegistries registries;
    private final CommandManager commandManager;
    private final PluginManager pluginManager;
    
    private boolean init;
    
    private JeiTweakerInitializer() {
        this.jeiTweakerLogger = CraftTweakerAPI.LOGGER; // Preparing for 1.19.3 :P
        this.registries = new JeiTweakerRegistries();
        this.commandManager = CommandManager.of();
        this.pluginManager = PluginManager.of(this.jeiTweakerLogger, this.registries);
        this.init = false;
    }
    
    public static JeiTweakerInitializer get() {
        return INSTANCE;
    }
    
    public void initialize() {
        if (this.init) return;
        this.pluginManager.discoverPlugins();
        this.init = true;
    }
    
    public CommandManager commandManager() {
        this.ensureInit();
        return this.commandManager;
    }
    
    public PluginManager pluginManager() {
        this.ensureInit();
        return this.pluginManager;
    }
    
    public JeiTweakerRegistries registries() {
        this.ensureInit();
        return this.registries;
    }
    
    public Logger jeiTweakerLogger() {
        this.ensureInit();
        return this.jeiTweakerLogger;
    }
    
    private void ensureInit() {
        if (this.init) return;
        this.initialize();
    }
}
