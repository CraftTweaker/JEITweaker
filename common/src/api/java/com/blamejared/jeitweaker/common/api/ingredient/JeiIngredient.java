package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.jeitweaker.common.api.JeiTweakerApi;

/**
 * Represents an ingredient that shows up in the JEI ingredient panel.
 *
 * <p>For context, the JEI ingredient panel is the list of ingredients visible by default on the right side of the
 * screen in any container.</p>
 *
 * <p>Every ingredient has a {@linkplain JeiIngredientType type} associated to it, along with some content. The content
 * is represented as either <em>JEI content</em>, <em>Zen content</em>, or both. <em>JEI content</em> identifies an
 * object that JEI can understand directly, but that it might or might not be accessible to scripts. On the other hand,
 * <em>Zen content</em> represents an object that scripts can interact with, but that might or might not be understood
 * by JEI. It is thus possible for both types of content to coincide, but they do not need to do so.</p>
 *
 * <p>An ingredient can be queried for both types of content regardless of the actual content stored within: the
 * implementation shall perform any necessary conversions that might be required to satisfy the request.</p>
 *
 * <p>Every JEI ingredient is <strong>immutable</strong>, meaning that the content objects returned by the various
 * getters cannot be modified externally. In other words, users are free to modify objects obtained through either
 * {@link #jeiContent()} or {@link #zenContent()} if they allow so: any such changes will not affect the contents stored
 * by the ingredient itself.</p>
 *
 * <p>While implementations of this interface are allowed as long as they follow the contract outlined below, it is
 * highly suggested that clients leverage the static factory methods {@link #ofZen(JeiIngredientType, Object)},
 * {@link #ofJei(JeiIngredientType, Object)}, and {@link #of(JeiIngredientType, Object, Object)} to create an instance
 * of this type as needed.</p>
 *
 * <p>Additional methods that facilitate operations on an ingredient are provided in the {@link JeiIngredients} class,
 * which acts as a sort-of "extension methods" holder, behaving like the {@code java.util.Arrays} class of the standard
 * library.</p>
 *
 * @param <J> The type of the content as understood by JEI.
 * @param <Z> The type of the content as understood by scripts.
 *
 * @see JeiIngredients
 * @since 4.0.0
 */
public interface JeiIngredient<J, Z> {
    
    /**
     * Creates a new {@link JeiIngredient} with the specified type and with the given script object as the content.
     *
     * @param type The type of the ingredient that should be created.
     * @param zenContent The script object that should be used to construct the ingredient.
     * @return A new ingredient with the given type and content.
     * @param <J> The type of the content as understood by JEI.
     * @param <Z> The type of the content as understood by scripts.
     * @throws NullPointerException If either of the parameters is {@code null}.
     *
     * @since 4.0.0
     */
    static <J, Z> JeiIngredient<J, Z> ofZen(final JeiIngredientType<J, Z> type, final Z zenContent) {
        return JeiIngredientTypes.converterFor(type).toFullIngredientFromZen(JeiTweakerApi.get().ingredientCreator().fromZen(), zenContent).apply(type);
    }
    
    /**
     * Creates a new {@link JeiIngredient} with the specified type and with the given JEI object as the content.
     *
     * @param type The type of the ingredient that should be created.
     * @param jeiContent The JEI object that should be used to construct the ingredient.
     * @return A new ingredient with the given type and content.
     * @param <J> The type of the content as understood by JEI.
     * @param <Z> The type of the content as understood by scripts.
     * @throws NullPointerException If either of the parameters is {@code null}.
     *
     * @since 4.0.0
     */
    static <J, Z> JeiIngredient<J, Z> ofJei(final JeiIngredientType<J, Z> type, final J jeiContent) {
        return JeiIngredientTypes.converterFor(type).toFullIngredientFromJei(JeiTweakerApi.get().ingredientCreator().fromJei(), jeiContent).apply(type);
    }
    
    /**
     * Creates a new {@link JeiIngredient} with the specified type and with the given couple of objects as the content.
     *
     * <p>Although not enforced by API checks, it is expected that both {@code jeiContent} and {@code zenContent} are
     * two representations of the same underlying object. If the two objects do not respect this property, they are
     * defined as <em>disjointed</em>: such an ingredient is to be considered ill-formed and no guarantees are made with
     * respect to the behavior of the program with such instances.</p>
     *
     * @param type The type of the ingredient that should be created.
     * @param jeiContent The JEI object that should be used to construct the ingredient.
     * @param zenContent The script object that should be used to construct the ingredient.
     * @return A new ingredient with the given type and content.
     * @param <J> The type of the content as understood by JEI.
     * @param <Z> The type of the content as understood by scripts.
     * @throws NullPointerException If any of the parameters is {@code null}.
     *
     * @since 4.0.0
     */
    static <J, Z> JeiIngredient<J, Z> of(final JeiIngredientType<J, Z> type, final J jeiContent, final Z zenContent) {
        return JeiIngredientTypes.converterFor(type).toFullIngredientFromBoth(JeiTweakerApi.get().ingredientCreator().fromBoth(), jeiContent, zenContent).apply(type);
    }
    
    /**
     * Obtains the {@link JeiIngredientType} associated with this ingredient.
     *
     * @return The ingredient's type.
     *
     * @since 4.0.0
     */
    JeiIngredientType<J, Z> type();
    
    /**
     * Obtains the JEI object contained within this ingredient.
     *
     * <p>Any conversions that might be required to obtain the content in JEI form might be performed during the
     * invocation of the method.</p>
     *
     * <p>Moreover, the returned object may be safely operated upon without altering the contents of this
     * ingredient.</p>
     *
     * @return The content as a JEI object.
     *
     * @since 4.0.0
     */
    J jeiContent();
    
    /**
     * Obtains the script object contained within this ingredient.
     *
     * <p>Any conversions that might be required to obtain the content in script form might be performed during the
     * invocation of the method.</p>
     *
     * <p>Moreover, the returned object may be safely operated upon without altering the contents of this
     * ingredient.</p>
     *
     * @return The content as a script object.
     *
     * @since 4.0.0
     */
    Z zenContent();
    
    /**
     * Returns a human-readable representation of this ingredient.
     *
     * @return A human-readable representation of this ingredient.
     *
     * @implSpec It is mandatory for the returned string to match one of the possible ways that a scriptwriter might use
     * to obtain an instance of the content as returned by {@link #zenContent()}. This string, known as a <em>command
     * string</em>, generally refers to a bracket expression that results in the target object. It is not allowed to
     * return any additional information in the string, nor report invalid script code. The default {@link Object}
     * implementation is therefore invalid.
     *
     * @since 4.0.0
     */
    @Override String toString();
}
