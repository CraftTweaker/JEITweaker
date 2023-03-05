package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.jeitweaker.common.api.JeiTweakerApi;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Provides a series of extension-like methods that facilitate operating with {@link JeiIngredient}s.
 *
 * <p>Refer to the {@code JeiIngredient} documentation for more specific details.</p>
 *
 * @apiNote This class is compatible with the extension function provided by Manifold.
 *
 * @see JeiIngredient
 * @since 4.0.0
 */
public final class JeiIngredients {
    private JeiIngredients() {}
    
    /**
     * Obtains the {@link IIngredientType} of the given {@link JeiIngredient}.
     *
     * <p>It essentially automatically handles the conversion between the {@linkplain JeiIngredientType ingredient type}
     * used by JeiTweaker to the representation used by JEI itself. Note that this conversion is lossy, as the type
     * information related to the script objects stored by ingredients is lost.</p>
     *
     * @param jeiIngredient The ingredient whose JEI type should be obtained.
     * @return The {@link IIngredientType} of the given ingredient.
     * @param <J> The type of the JEI object of the ingredient.
     * @param <Z> The type of the script object of the ingredient.
     * @throws NullPointerException If the given ingredient is {@code null}.
     *
     * @since 4.0.0
     */
    public static <J, Z> IIngredientType<J> jeiIngredientTypeOf(final JeiIngredient<J, Z> jeiIngredient) {
        return JeiIngredientTypes.toJeiType(Objects.requireNonNull(jeiIngredient, "jeiIngredient").type());
    }
    
    /**
     * Converts the given {@link ZenJeiIngredient} instance to a regular {@link JeiIngredient}.
     *
     * <p>This method should be used whenever a method receives an ingredient from the ZenCode script interoperability
     * interface, allowing for fast unwrapping of the holder. Clients should never operate with raw
     * {@code ZenJeiIngredient} instances.</p>
     *
     * @param zenJeiIngredient The ingredient to unwrap.
     * @return The unwrapped ingredient.
     * @param <J> The type of the JEI object of the ingredient.
     * @param <Z> The type of the script object of the ingredient.
     * @throws NullPointerException If the argument is {@code null}.
     * @throws IllegalArgumentException If the argument was not obtained from either a call to the
     * {@link #toZenIngredient(JeiIngredient)} method or directly from the ZenCode script interoperability interface.
     *
     * @since 4.0.0
     */
    public static <J, Z> JeiIngredient<J, Z> toJeiIngredient(final ZenJeiIngredient zenJeiIngredient) {
        return JeiTweakerApi.get().ingredientJeiFromZen(zenJeiIngredient);
    }
    
    /**
     * Converts the given {@link JeiIngredient} to a {@link ZenJeiIngredient} for script interoperability.
     *
     * <p>This method should be used solely if a client requires exposing a particular {@link JeiIngredient} instance to
     * a script through the ZenCode script interoperability interface. Clients should never operate with raw
     * {@code ZenJeiIngredient} instances.</p>
     *
     * @param jeiIngredient The ingredient to wrap.
     * @return The wrapped ingredient.
     * @param <J> The type of the JEI object of the ingredient.
     * @param <Z> The type of the script object of the ingredient.
     * @throws NullPointerException If the argument is {@code null}.
     *
     * @since 4.0.0
     */
    public static <J, Z> ZenJeiIngredient toZenIngredient(final JeiIngredient<J, Z> jeiIngredient) {
        return JeiTweakerApi.get().ingredientZenFromJei(jeiIngredient);
    }
    
    /**
     * Obtains the <em>command string</em> of the given {@link JeiIngredient}.
     *
     * <p>The command string is a string representation of the way the given object can be obtained by a scriptwriter.
     * It is usually in the form of a bracket expression, but no restrictions on it are placed. The returned string will
     * be a valid expression in the ZenCode programming language, representing the given ingredient as closely as
     * possible.</p>
     *
     * @param jeiIngredient The ingredient to convert.
     * @return The command string of the given ingredient.
     * @param <J> The type of the JEI object of the ingredient.
     * @param <Z> The type of the script object of the ingredient.
     * @throws NullPointerException If the argument is {@code null}.
     *
     * @since 4.0.0
     */
    public static <J, Z> String toCommandString(final JeiIngredient<J, Z> jeiIngredient) {
        return Objects.requireNonNull(jeiIngredient, "jeiIngredient").toString(); // Must be overridden to return the command string by contract
    }
    
    /**
     * Obtains the registry name of the given {@link JeiIngredient}.
     *
     * <p>The registry name is the unique name that identifies the object itself or a part of it within a registry. It
     * is not thus not necessary for this conversion to represent a bijective relation: multiple objects may be assigned
     * the same registry name. Moreover, the name itself might not represent an actual name within a real registry,
     * rather it might be a "fictitious" name that might uniquely identify the object if it were placed within an
     * actual registry.</p>
     *
     * @param jeiIngredient The ingredient to convert.
     * @return The registry name of the given ingredient.
     * @param <J> The type of the JEI object of the ingredient.
     * @param <Z> The type of the script object of the ingredient.
     * @throws NullPointerException If the argument is {@code null}.
     *
     * @since 4.0.0
     */
    public static <J, Z> ResourceLocation toRegistryName(final JeiIngredient<J, Z> jeiIngredient) {
        return JeiIngredientTypes.converterFor(Objects.requireNonNull(jeiIngredient, "jeiIngredient").type()).toRegistryNameFromZen(jeiIngredient.zenContent());
    }
}
