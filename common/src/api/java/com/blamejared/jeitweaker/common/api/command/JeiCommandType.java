package com.blamejared.jeitweaker.common.api.command;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public final class JeiCommandType<T> {
    private static final Map<String, JeiCommandType<?>> CATALOG_TYPE_CATALOG = new Object2ObjectArrayMap<>();
    private static final Collection<JeiCommandType<?>> VALUES = Collections.unmodifiableCollection(CATALOG_TYPE_CATALOG.values());
    
    static {
        JeiCommandTypes.init(); // Force-load the class containing all command types
    }
    
    private final String id;
    private final TypeToken<T> argumentType;
    
    private JeiCommandType(final String id, final TypeToken<T> argumentType) {
        this.id = id;
        this.argumentType = argumentType;
    }
    
    public static <T> JeiCommandType<T> of(final String id) {
        return GenericUtil.uncheck(CATALOG_TYPE_CATALOG.get(id));
    }
    
    public static Collection<JeiCommandType<?>> values() {
        return VALUES;
    }
    
    static <T> JeiCommandType<T> of(final String id, final Class<T> argumentType) {
        return of(id, TypeToken.of(argumentType));
    }
    
    static <T> JeiCommandType<T> of(final String id, final TypeToken<T> argumentType) {
        final JeiCommandType<T> type = new JeiCommandType<>(id, argumentType);
        CATALOG_TYPE_CATALOG.put(id, type);
        return type;
    }
    
    public String id() {
        return this.id;
    }
    
    public TypeToken<T> argumentType() {
        return this.argumentType;
    }
    
    @Override
    public String toString() {
        return "JeiCommandType[%s@%s]".formatted(this.id(), this.argumentType());
    }
    
}
