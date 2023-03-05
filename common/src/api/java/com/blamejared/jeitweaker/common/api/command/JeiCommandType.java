package com.blamejared.jeitweaker.common.api.command;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Represents a {@link JeiCommand}'s type.
 *
 * <p>The type is made up of an {@linkplain #id() identifier}, which acts as a human-readable name for logging and
 * debugging purposes, and an {@linkplain #argumentType() argument type}. The argument type is a {@link TypeToken} that
 * represents the kind of argument that a {@link JeiCommand} with this type will accept in its
 * {@link JeiCommand#execute(Object)} method.</p>
 *
 * <p>In other words, the type is used to associate every command with a specific stage of the JEI plugin reloading
 * routine, allowing it to also consume the object given to a JEI plugin in that specific phase. The type is not
 * necessarily the same as the one supplied by JEI.</p>
 *
 * <p>This type is represented as a <strong>catalog type</strong>, which is to say it acts like an {@code enum}, without
 * being one. This restriction is in place due to the generic nature of the class, which prevents it from being an
 * actual enumeration. Clients should nevertheless consider this type as if it were an {@code enum}. A constant can be
 * obtained either by its {@link String} identifier through {@link #of(String)} or through the values listed in
 * {@link JeiCommandTypes}. It is guaranteed that all values of {@link JeiCommandTypes} represent all possible values
 * that this catalog type can assume. A list of all values is also available through {@link #values()}.</p>
 *
 * @param <T> The type of the argument that a potential {@link JeiCommand} of this type will consume during execution.
 *
 * @since 4.0.0
 */
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
    
    /**
     * Finds the {@link JeiCommandType} with the given id, if it exists.
     *
     * <p>No checking is performed on whether the generic value and the argument type match: clients of the API assume
     * all checking responsibility.</p>
     *
     * <p>Note that usage of this method is discouraged unless dynamic lookup is needed, clients are strongly advised to
     * refer to the list of types available in {@link JeiCommandTypes}.</p>
     *
     * @param id The human-readable identifier of the type.
     * @return The type with the given id, if it exists; {@code null} otherwise.
     * @param <T> The argument type of the command type.
     *
     * @since 4.0.0
     */
    public static <T> JeiCommandType<T> of(final String id) {
        return GenericUtil.uncheck(CATALOG_TYPE_CATALOG.get(id));
    }
    
    /**
     * Obtains a collection of all {@link JeiCommandType}s that can be used.
     *
     * <p>This method acts as a parallel to the {@code values} method of an {@link Enum}.</p>
     *
     * <p>The returned {@link Collection} is immutable.</p>
     *
     * @return All known values of this catalog type.
     *
     * @since 4.0.0
     */
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
    
    /**
     * Gets the human-readable identifier of this type.
     *
     * @return The human-readable identifier.
     *
     * @since 4.0.0
     */
    public String id() {
        return this.id;
    }
    
    /**
     * Gets the type of the argument that a {@link JeiCommand} of this type will consume.
     *
     * @return The argument type.
     *
     * @since 4.0.0
     */
    public TypeToken<T> argumentType() {
        return this.argumentType;
    }
    
    @Override
    public String toString() {
        return "JeiCommandType[%s@%s]".formatted(this.id(), this.argumentType());
    }
    
}
