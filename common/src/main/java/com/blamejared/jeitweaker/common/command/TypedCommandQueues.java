package com.blamejared.jeitweaker.common.command;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.jeitweaker.common.api.command.JeiCommandType;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Collection;
import java.util.Map;

public final class TypedCommandQueues {
    private final Map<JeiCommandType<?>, CommandQueue<?>> delegate;
    
    private TypedCommandQueues(final Map<JeiCommandType<?>, CommandQueue<?>> delegate) {
        this.delegate = delegate;
    }
    
    static TypedCommandQueues of(final Collection<JeiCommandType<?>> types) {
        final int length = types.size();
        final JeiCommandType<?>[] keys = types.toArray(JeiCommandType[]::new);
        final CommandQueue<?>[] values = new CommandQueue<?>[length];
        
        for (int i = 0; i < length; ++i) {
            values[i] = CommandQueue.of();
        }
        
        final Map<JeiCommandType<?>, CommandQueue<?>> delegate = Object2ObjectMaps.unmodifiable(new Object2ObjectOpenHashMap<>(keys, values));
        return new TypedCommandQueues(delegate);
    }
    
    public <T> CommandQueue<T> queueFor(final JeiCommandType<T> type) {
        return GenericUtil.uncheck(this.delegate.get(type));
    }
    
}
