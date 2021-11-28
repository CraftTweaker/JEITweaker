package com.blamejared.jeitweaker.implementation.state;


public enum StateManager {
    INSTANCE;
    
    private final ActionsState actionsState;
    private final JeiGlobalState jeiGlobalState;
    private final RegistrationState registrationState;
    
    StateManager() {
        
        this.actionsState = new ActionsState();
        this.jeiGlobalState = new JeiGlobalState();
        this.registrationState = new RegistrationState();
    }
    
    public ActionsState actionsState() {
        
        return this.actionsState;
    }
    
    public JeiGlobalState jeiGlobalState() {
        
        return this.jeiGlobalState;
    }
    
    public RegistrationState registrationState() {
        
        return this.registrationState;
    }
}
