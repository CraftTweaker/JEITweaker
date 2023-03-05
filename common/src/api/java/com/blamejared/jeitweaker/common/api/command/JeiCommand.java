package com.blamejared.jeitweaker.common.api.command;

import java.util.Objects;

/**
 * Represents a command that JEI should execute during plugin reloading.
 *
 * <p>Every command has a {@linkplain JeiCommandType type} associated to it, allowing for separation of the commands
 * into the various phases of plugin reloading that JEI provides. Additionally, every command performs its action
 * according to a {@linkplain JeiCommandExecutor command executor}.</p>
 *
 * <p>Every action that JeiTweaker must perform has to be encoded into one or multiple commands, according to the
 * specific action requirements. It is not mandatory that the command is as minimal as possible, although the more work
 * can be done in advance, the better.</p>
 *
 * <p>Instances of this class can be constructed through the {@link #of(JeiCommandType, JeiCommandExecutor)} factory
 * method.</p>
 *
 * @param <T> The type associated to this command's type, representing the JEI object this command will consume in its
 *            executor.
 *
 * @since 4.0.0
 */
public final class JeiCommand<T> {
    private final JeiCommandType<T> type;
    private final JeiCommandExecutor<T> command;
    
    private JeiCommand(final JeiCommandType<T> type, final JeiCommandExecutor<T> command) {
        this.type = type;
        this.command = command;
    }
    
    /**
     * Creates a new {@link JeiCommand} with the given type and executor.
     *
     * @param type The type of the command to create.
     * @param command The actual command that needs to be executed, represented through a {@link JeiCommandExecutor}.
     * @return A new {@link JeiCommand} representing the specified set of operations.
     * @param <T> The type associated to the command's type, representing the JEI object the executor will consume.
     * @throws NullPointerException If either the type or the executor is {@code null}.
     *
     * @since 4.0.0
     */
    public static <T> JeiCommand<T> of(final JeiCommandType<T> type, final JeiCommandExecutor<T> command) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(command, "command");
        return new JeiCommand<>(type, command);
    }
    
    /**
     * Gets the {@linkplain JeiCommandType command type} associated to this command.
     *
     * @return The command's type.
     *
     * @since 4.0.0
     */
    public JeiCommandType<T> type() {
        return this.type;
    }
    
    /**
     * Executes this command, consuming the specified argument.
     *
     * <p>The execution of a command implies that the corresponding {@linkplain JeiCommandExecutor executor} will
     * eventually be invoked. No guarantees are provided nor shall be assumed regarding whether additional code might or
     * might not be executed as part of a command's execution. In other words, this method should always be preferred
     * over directly invoking {@link JeiCommandExecutor#execute(Object)}.</p>
     *
     * @param argument The argument that the command should consumer.
     *
     * @since 4.0.0
     */
    public void execute(final T argument) {
        this.command.accept(argument);
    }
}
