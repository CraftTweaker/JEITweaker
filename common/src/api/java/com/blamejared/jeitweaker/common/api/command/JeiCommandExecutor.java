package com.blamejared.jeitweaker.common.api.command;

import java.util.function.Consumer;

/**
 * Represents the actual code that should be executed by a {@link JeiCommand} when it is invoked.
 *
 * <p>The executor consumes an argument, which represents the JEI object that can be consumed by the executor to
 * correctly carry out the operation. The argument obtained is directly tied to the command's
 * {@linkplain JeiCommandType type}.</p>
 *
 * <p>This interface essentially acts as a more specialized version of {@link Consumer}, allowing for more specific
 * typing in JeiTweaker's APIs.</p>
 *
 * <p>This interface is a {@linkplain FunctionalInterface functional interface} whose functional method is
 * {@link #execute(Object)}.</p>
 *
 * @param <T> The type of the argument the executor consumes.
 *
 * @since 4.0.0
 */
@FunctionalInterface
public interface JeiCommandExecutor<T> extends Consumer<T> {
    
    /**
     * Executes the command leveraging the given argument if needed.
     *
     * @param argument The argument to leverage.
     *
     * @since 4.0.0
     */
    void execute(final T argument);
    
    /**
     * Executes the command leveraging the given argument if needed.
     *
     * @param t The argument to leverage.
     *
     * @implNote This method acts as a simple bouncer to {@link #execute(Object)}, allowing for usage of this executor
     * as a consumer, while also providing a more meaningful method name when dealing with the API directly.
     *
     * @since 4.0.0
     */
    @Override
    default void accept(final T t) {
        this.execute(t);
    }
}
