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
    
    private JeiTweakerInitializer() {
        this.jeiTweakerLogger = CraftTweakerAPI.LOGGER; // Preparing for 1.19.3 :P
        this.registries = new JeiTweakerRegistries();
        this.commandManager = CommandManager.of();
        this.pluginManager = PluginManager.of(this.jeiTweakerLogger, this.registries);
        this.initialize();
    }
    
    public static JeiTweakerInitializer get() {
        return INSTANCE;
    }
    
    public CommandManager commandManager() {
        return this.commandManager;
    }
    
    public PluginManager pluginManager() {
        return this.pluginManager;
    }
    
    public JeiTweakerRegistries registries() {
        return this.registries;
    }
    
    public Logger jeiTweakerLogger() {
        return this.jeiTweakerLogger;
    }
    
    private void initialize() {
        this.pluginManager.discoverPlugins();
    }
}
