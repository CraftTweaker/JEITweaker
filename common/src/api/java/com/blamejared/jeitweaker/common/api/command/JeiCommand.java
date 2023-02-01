package com.blamejared.jeitweaker.common.api.command;

import java.util.Objects;
import java.util.function.Consumer;

public final class JeiCommand<T> {
    private final JeiCommandType<T> type;
    private final Consumer<T> command;
    
    private JeiCommand(final JeiCommandType<T> type, final Consumer<T> command) {
        this.type = type;
        this.command = command;
    }
    
    public static <T> JeiCommand<T> of(final JeiCommandType<T> type, final Consumer<T> command) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(command, "command");
        return new JeiCommand<>(type, command);
    }
    
    public JeiCommandType<T> type() {
        return this.type;
    }
    
    public void execute(final T argument) {
        this.command.accept(argument);
    }
}
