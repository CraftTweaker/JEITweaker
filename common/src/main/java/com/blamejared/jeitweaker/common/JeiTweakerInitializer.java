package com.blamejared.jeitweaker.common;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.jeitweaker.common.command.CommandManager;
import org.apache.logging.log4j.Logger;

public final class JeiTweakerInitializer {
    private static final JeiTweakerInitializer INSTANCE = new JeiTweakerInitializer();
    
    private final CommandManager manager;
    private final Logger jeiTweakerLogger;
    
    private boolean init;
    
    private JeiTweakerInitializer() {
        this.manager = CommandManager.of();
        this.init = false;
        this.jeiTweakerLogger = CraftTweakerAPI.LOGGER; // Preparing for 1.19.3 :P
    }
    
    public static JeiTweakerInitializer get() {
        return INSTANCE;
    }
    
    public void initialize() {
        // Do initialization stuff if required
        this.init = true;
    }
    
    public CommandManager commandManager() {
        this.assertInit();
        return this.manager;
    }
    
    public Logger jeiTweakerLogger() {
        this.assertInit();
        return this.jeiTweakerLogger;
    }
    
    private void assertInit() {
        if (this.init) return;
        throw new IllegalStateException("Out of order initialization");
    }
}
