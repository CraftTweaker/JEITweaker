package com.blamejared.jeitweaker.common.api.ingredient;

import com.google.common.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Represents the type of content that can be stored within a {@link JeiIngredient}.
 *
 * <p>An ingredient type is essentially a tuple of two types, representing the type of JEI objects and the type of
 * script objects that can be stored within a {@code JeiIngredient} instance.</p>
 *
 * <p>Additionally, an ingredient type is also uniquely identified through an identifier in the form of a
 * {@link ResourceLocation}, which allows for lookups of foreign ingredient types. It is up to the creator of the
 * ingredient type to ensure no conflicts with other known types exist. Moreover, it should also be noted that two
 * ingredient types are considered to be the same solely based on the given identifier. It is thus up to clients of this
 * API to ensure that the generic types match with the expected output.</p>
 *
 * <p>Every ingredient type must be registered through a plugin in the
 * {@link com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPluginProvider#registerIngredientTypes(com.blamejared.jeitweaker.common.api.plugin.JeiIngredientTypeRegistration)}
 * method in order to be discoverable by JeiTweaker and allow proper operation.</p>
 *
 * <p>A new instance of an ingredient type can be obtained through the use of the {@code of} family of static methods.
 * An already existing type can be looked up through {@link JeiIngredientTypes#findById(ResourceLocation)} instead.
 * Commonly used ingredient types are also available through the {@link BuiltinJeiIngredientTypes} class.</p>
 *
 * <p>Additional methods that facilitate operations on an ingredient type are provided in the {@link JeiIngredientTypes}
 * class, which acts as a sort of "extension methods" holder, behaving similarly to the {@code java.util.Arrays} class
 * of the standard library.</p>
 *
 * @param <J> The type of JEI objects that can be stored in an ingredient with this type.
 * @param <Z> The type of script objects that can be stored in an ingredient with this type.
 *
 * @see BuiltinJeiIngredientTypes
 * @see JeiIngredientTypes
 * @since 4.0.0
 */
@SuppressWarnings("UnstableApiUsage")
public final class JeiIngredientType<J, Z> {
    private final ResourceLocation id;
    private final TypeToken<J> jeiType;
    private final TypeToken<Z> zenType;
    
    private JeiIngredientType(final ResourceLocation id, final TypeToken<J> jeiType, final TypeToken<Z> zenType) {
        this.id = id;
        this.jeiType = jeiType;
        this.zenType = zenType;
    }
    
    /**
     * Creates a new ingredient type with the given identifier and the specified couple of types.
     *
     * <p>The two specified types are both considered raw types as far as the ingredient type is concerned, meaning that
     * generic parameters that might be necessary are ignored. If any of the types is supposed to be generic, then usage
     * of the methods accepting a {@link TypeToken} is suggested instead.</p>
     *
     * @param id The identifier that uniquely identifies the ingredient type.
     * @param jeiType The raw type ({@link Class}) of the JEI objects that can be stored in an ingredient with this
     *                type.
     * @param zenType The raw type ({@link Class}) of the script objects that can be stored in an ingredient with this
     *                type.
     * @return A newly created {@link JeiIngredientType} with the specified parameters.
     * @param <J> The type of the JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of the script objects that can be stored in an ingredient with this type.
     * @throws NullPointerException If any of the arguments is null.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final Class<J> jeiType, final Class<Z> zenType) {
        return of(id, TypeToken.of(jeiType), TypeToken.of(zenType));
    }
    
    /**
     * Creates a new ingredient type with the given identifier and the specified couple of types.
     *
     * <p>The type specified for the JEI object is considered a raw type as far as the ingredient type is concerned,
     * meaning that generic parameters that might be necessary are ignored. If the type is supposed to be generic, then
     * usage of the methods accepting a {@link TypeToken} is suggested instead.</p>
     *
     * @param id The identifier that uniquely identifies the ingredient type.
     * @param jeiType The raw type ({@link Class}) of the JEI objects that can be stored in an ingredient with this
     *                type.
     * @param zenType The {@link TypeToken} wrapping the generic type of the script objects that can be stored in an
     *                ingredient with this type.
     * @return A newly created {@link JeiIngredientType} with the specified parameters.
     * @param <J> The type of the JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of the script objects that can be stored in an ingredient with this type.
     * @throws NullPointerException If any of the arguments is null.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final Class<J> jeiType, final TypeToken<Z> zenType) {
        return of(id, TypeToken.of(jeiType), zenType);
    }
    
    /**
     * Creates a new ingredient type with the given identifier and the specified couple of types.
     *
     * <p>The type specified for the script object is considered a raw type as far as the ingredient type is concerned,
     * meaning that generic parameters that might be necessary are ignored. If the type is supposed to be generic, then
     * usage of the methods accepting a {@link TypeToken} is suggested instead.</p>
     *
     * @param id The identifier that uniquely identifies the ingredient type.
     * @param jeiType The {@link TypeToken} wrapping the generic type of the JEI objects that can be stored in an
     *                ingredient with this type.
     * @param zenType The raw type ({@link Class}) of the script objects that can be stored in an ingredient with this
     *                type.
     * @return A newly created {@link JeiIngredientType} with the specified parameters.
     * @param <J> The type of the JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of the script objects that can be stored in an ingredient with this type.
     * @throws NullPointerException If any of the arguments is null.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final TypeToken<J> jeiType, final Class<Z> zenType) {
        return of(id, jeiType, TypeToken.of(zenType));
    }
    
    /**
     * Creates a new ingredient type with the given identifier and the specified couple of types.
     *
     * @param id The identifier that uniquely identifies the ingredient type.
     * @param jeiType The {@link TypeToken} wrapping the generic type of the JEI objects that can be stored in an
     *                ingredient with this type.
     * @param zenType The {@link TypeToken} wrapping the generic type of the script objects that can be stored in an
     *                ingredient with this type.
     * @return A newly created {@link JeiIngredientType} with the specified parameters.
     * @param <J> The type of the JEI objects that can be stored in an ingredient with this type.
     * @param <Z> The type of the script objects that can be stored in an ingredient with this type.
     * @throws NullPointerException If any of the arguments is null.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final TypeToken<J> jeiType, final TypeToken<Z> zenType) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(jeiType, "jeiType");
        Objects.requireNonNull(zenType, "zenType");
        return new JeiIngredientType<>(id, jeiType, zenType);
    }
    
    /**
     * Gets the unique identifier of this ingredient type as a {@link ResourceLocation}.
     *
     * @return The unique identifier.
     *
     * @since 4.0.0
     */
    public ResourceLocation id() {
        return this.id;
    }
    
    /**
     * Gets the type of JEI objects that an ingredient with this type can store.
     *
     * <p>The type is returned as a {@link TypeToken} regardless of whether the ingredient was created with a raw type
     * or a generic type. It is therefore suggested to leverage the {@code TypeToken} interface for queries, rather than
     * unwrapping it into either a raw type or the {@code java.lang.reflect.Type} equivalent, to ensure proper generic
     * handling.</p>
     *
     * @return The type of JEI objects.
     *
     * @since 4.0.0
     */
    public TypeToken<J> jeiType() {
        return this.jeiType;
    }
    
    /**
     * Gets the type of script objects that an ingredient with this type can store.
     *
     * <p>The type is returned as a {@link TypeToken} regardless of whether the ingredient was created with a raw type
     * or a generic type. It is therefore suggested to leverage the {@code TypeToken} interface for queries, rather than
     * unwrapping it into either a raw type or the {@code java.lang.reflect.Type} equivalent, to ensure proper generic
     * handling.</p>
     *
     * @return The type of script objects.
     *
     * @since 4.0.0
     */
    public TypeToken<Z> zenType() {
        return this.zenType;
    }
    
    @Override
    public boolean equals(final Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        final JeiIngredientType<?, ?> that = (JeiIngredientType<?, ?>) o;
        return this.id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
    
    @Override
    public String toString() {
        return "JeiIngredientType[%s]{%s->%s}".formatted(this.id(), this.jeiType(), this.zenType());
    }
    
}
