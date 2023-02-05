package com.blamejared.jeitweaker.common.api.command;

import java.util.function.Consumer;

@FunctionalInterface
public interface JeiCommandExecutor<T> extends Consumer<T> {
    void execute(final T argument);
    
    @Override
    default void accept(final T t) {
        this.execute(t);
    }
}
