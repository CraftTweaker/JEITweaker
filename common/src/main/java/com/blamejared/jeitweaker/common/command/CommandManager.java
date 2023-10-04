package com.blamejared.jeitweaker.common.command;

import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandType;

public final class CommandManager {
    private final CommandGeneration generation;
    private final TypedCommandQueues queues;
    
    private CommandManager() {
        this.generation = CommandGeneration.of();
        this.queues = TypedCommandQueues.of(JeiCommandType.values());
    }
    
    public static CommandManager of() {
        return new CommandManager();
    }
    
    public <T> void enqueueCommand(final JeiCommand<T> command) {
        this.generation.awaitingReproduction(newGeneration -> {
            this.queues.queueFor(command.type()).add(new GenerativeCommand<>(command, newGeneration));
        });
    }
    
    public void populateCommands() {
        this.generation.reproduce();
    }
    
    public <T> void executeCommands(final JeiCommandType<T> type, final T argument) {
        this.queues.queueFor(type).runCommands(this.generation.currentGeneration(), argument);
    }
}
