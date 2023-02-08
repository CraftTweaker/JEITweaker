package com.blamejared.jeitweaker.common.command;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.jeitweaker.common.JeiTweakerInitializer;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandExecutor;
import com.blamejared.jeitweaker.common.api.command.JeiCommandType;
import com.blamejared.jeitweaker.common.platform.PlatformBridge;

import java.util.Objects;
import java.util.function.UnaryOperator;

final class SafeJeiCommandManager {
    @SuppressWarnings("ClassCanBeRecord")
    private static final class SafeExecutor<T> implements JeiCommandExecutor<T> {
        private final JeiCommandExecutor<T> delegate;
        private final JeiCommandType<T> type;
        
        SafeExecutor(final JeiCommandExecutor<T> delegate, final JeiCommandType<T> type) {
            this.delegate = Objects.requireNonNull(delegate, "delegate");
            this.type = Objects.requireNonNull(type, "type");
        }
    
        @Override
        public void execute(final T argument) {
            try {
                this.delegate.execute(argument);
            } catch (final Throwable t) {
                final String message = "An error occurred during execution of the command %s of type %s".formatted(this.delegate, this.type);
                JeiTweakerInitializer.get().jeiTweakerLogger().error(message, t);
            }
        }
    }
    
    @FunctionalInterface
    private interface SafeWrapper<T> extends UnaryOperator<JeiCommand<T>> {
        default JeiCommand<T> wrap(final JeiCommand<T> t) {
            return this.apply(t);
        }
    }
    
    private static final SafeWrapper<?> WRAPPER = PlatformBridge.INSTANCE.isDevEnv()? SafeJeiCommandManager::passThrough : SafeJeiCommandManager::makeSafe;
    
    private SafeJeiCommandManager() {}
    
    public static <T> JeiCommand<T> safeOf(final JeiCommand<T> command) {
        return GenericUtil.uncheck(WRAPPER.wrap(GenericUtil.uncheck(Objects.requireNonNull(command, "command"))));
    }
    
    private static <T> JeiCommand<T> makeSafe(final JeiCommand<T> command) {
        final JeiCommandType<T> type = command.type();
        return JeiCommand.of(type, new SafeExecutor<>(command::execute, type));
    }
    
    private static <T> JeiCommand<T> passThrough(final JeiCommand<T> command) {
        return command;
    }
}
