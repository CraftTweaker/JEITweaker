package com.blamejared.jeitweaker.common;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.jeitweaker.common.command.CommandManager;
import com.blamejared.jeitweaker.common.plugin.core.PluginManager;
import com.blamejared.jeitweaker.common.registry.JeiTweakerRegistries;
import com.google.common.base.Suppliers;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public final class JeiTweakerInitializer {
    private static final JeiTweakerInitializer INSTANCE = new JeiTweakerInitializer();
    
    private final Supplier<Logger> jeiTweakerLogger;
    private final JeiTweakerRegistries registries;
    private final Supplier<CommandManager> commandManager;
    private final Supplier<PluginManager> pluginManager;
    
    private JeiTweakerInitializer() {
        this.jeiTweakerLogger = Suppliers.memoize(() -> CraftTweakerAPI.getLogger("JEITweaker"));
        this.registries = new JeiTweakerRegistries();
        this.commandManager = Suppliers.memoize(CommandManager::of);
        this.pluginManager = Suppliers.memoize(() -> PluginManager.of(this.jeiTweakerLogger.get(), this.registries));
        this.initialize();
    }
    
    public static JeiTweakerInitializer get() {
        return INSTANCE;
    }
    
    public CommandManager commandManager() {
        return this.commandManager.get();
    }
    
    public PluginManager pluginManager() {
        return this.pluginManager.get();
    }
    
    public JeiTweakerRegistries registries() {
        return this.registries;
    }
    
    public Logger jeiTweakerLogger() {
        return this.jeiTweakerLogger.get();
    }
    
    private void initialize() {
        this.pluginManager.get().discoverPlugins();
    }
}
