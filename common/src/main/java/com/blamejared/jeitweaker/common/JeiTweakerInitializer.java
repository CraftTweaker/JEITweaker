package com.blamejared.jeitweaker.common;

import com.blamejared.jeitweaker.common.command.CommandManager;

public final class JeiTweakerInitializer {
    private static final JeiTweakerInitializer INSTANCE = new JeiTweakerInitializer();
    
    private final CommandManager manager;
    
    private boolean init;
    
    private JeiTweakerInitializer() {
        this.manager = CommandManager.of();
        this.init = false;
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
    
    private void assertInit() {
        if (this.init) return;
        throw new IllegalStateException("Out of order initialization");
    }
}
