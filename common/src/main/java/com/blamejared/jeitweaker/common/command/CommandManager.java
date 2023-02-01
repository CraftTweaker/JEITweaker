package com.blamejared.jeitweaker.common.command;

import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandType;

public final class CommandManager {
    private final TypedCommandQueues queues;
    
    private CommandManager() {
        this.queues = TypedCommandQueues.of(JeiCommandType.values());
    }
    
    public static CommandManager of() {
        return new CommandManager();
    }
    
    public <T> void enqueueCommand(final JeiCommand<T> command) {
        this.queues.queueFor(command.type()).add(command);
    }
    
    public <T> void executeCommands(final JeiCommandType<T> type, final T argument) {
        this.queues.queueFor(type).runCommands(argument);
    }
}
