package com.blamejared.jeitweaker.common.api.ingredient;

import net.minecraft.resources.ResourceLocation;

/**
 * Handles conversions for a particular {@link JeiIngredientType}.
 *
 * <p>A conversion is defined as any attempt of trying to change the representation of an object without changing the
 * actual contents of it. Refer to the documentation of the specific methods that compose this interface to identify
 * expected behavior.</p>
 *
 * <p>Every ingredient type can be associated solely with one converter instance and vice-versa.</p>
 *
 * @param <J> The type of the JEI object of the ingredient type.
 * @param <Z> The type of the script object of the ingredient type.
 *
 * @since 4.0.0
 */
public interface JeiIngredientConverter<J, Z> {
    
    /**
     * Obtains a {@link JeiIngredientCreator.Creator} that can be used to construct a {@link JeiIngredient} from the
     * given JEI content.
     *
     * <p>The given creator should be obtained by leveraging one of the creator methods provided by the
     * {@link JeiIngredientCreator.FromJei} object. Returning a custom creator is allowed but discouraged as a matter of
     * API consistency.</p>
     *
     * @param creator The {@link JeiIngredientCreator.FromJei} instance that should be used to obtain the creator to
     *                return.
     * @param jeiType The content of the ingredient represented by a JEI object.
     * @return A {@link JeiIngredientCreator.Creator} that can be used to build the requested {@link JeiIngredient}.
     * @throws NullPointerException If either of the parameters is {@code null}.
     *
     * @apiNote The suggested way of implementing this method is returning the result of the invocation of
     * {@link JeiIngredientCreator.FromJei#of(Object)} (or its variant for mutable objects) with {@code jeiType} as the
     * parameter.
     *
     * @since 4.0.0
     */
    JeiIngredientCreator.Creator<J, Z> toFullIngredientFromJei(final JeiIngredientCreator.FromJei creator, final J jeiType);
    
    /**
     * Obtains a {@link JeiIngredientCreator.Creator} that can be used to construct a {@link JeiIngredient} from the
     * given script content.
     *
     * <p>The given creator should be obtained by leveraging one of the creator methods provided by the
     * {@link JeiIngredientCreator.FromZen} object. Returning a custom creator is allowed but discouraged as a matter of
     * API consistency.</p>
     *
     * @param creator The {@link JeiIngredientCreator.FromZen} instance that should be used to obtain the creator to
     *                return.
     * @param zenType The content of the ingredient represented by a script object.
     * @return A {@link JeiIngredientCreator.Creator} that can be used to build the requested {@link JeiIngredient}.
     * @throws NullPointerException If either of the parameters is {@code null}.
     *
     * @apiNote The suggested way of implementing this method is returning the result of the invocation of
     * {@link JeiIngredientCreator.FromZen#of(Object)} (or its variant for mutable objects) with {@code zenType} as the
     * parameter.
     *
     * @since 4.0.0
     */
    JeiIngredientCreator.Creator<J, Z> toFullIngredientFromZen(final JeiIngredientCreator.FromZen creator, final Z zenType);
    
    /**
     * Obtains a {@link JeiIngredientCreator.Creator} that can be used to construct a {@link JeiIngredient} from the
     * given content, represented both as a JEI object and as a script object.
     *
     * <p>The given creator should be obtained by leveraging one of the creator methods provided by the
     * {@link JeiIngredientCreator.FromBoth} object. Returning a custom creator is allowed but discouraged as a matter
     * of API consistency.</p>
     *
     * <p>It is expected that both {@code jeiType} and {@code zenType} are two representations of the same underlying
     * object. If this is not the case, then the method call is to be considered ill-formed and no guarantees can be
     * made with respect to the behavior of the application.</p>
     *
     * @param creator The {@link JeiIngredientCreator.FromBoth} instance that should be used to obtain the creator to
     *                return.
     * @param jeiType The content of the ingredient represented by a JEI object.
     * @param zenType The content of the ingredient represented by a script object.
     * @return A {@link JeiIngredientCreator.Creator} that can be used to build the requested {@link JeiIngredient}.
     * @throws NullPointerException If any of the parameters is {@code null}.
     *
     * @apiNote The suggested way of implementing this method is returning the result of the invocation of
     * {@link JeiIngredientCreator.FromBoth#of(Object, Object)} (or one of its variants for mutable objects) with
     * {@code jeiType} and {@code zenType} as its parameters.
     *
     * @implNote Implementations of this method <strong>need not</strong> verify that {@code jeiType} and
     * {@code zenType} represent the same underlying object, but may decide to do so to avoid further issues down the
     * line. It is nevertheless suggested to consider performance implications of such a decision.
     *
     * @since 4.0.0
     */
    JeiIngredientCreator.Creator<J, Z> toFullIngredientFromBoth(final JeiIngredientCreator.FromBoth creator, final J jeiType, final Z zenType);
    
    /**
     * Converts the given script object to a corresponding JEI object.
     *
     * <p>A JEI object is defined as corresponding to a script object if and only if it carries the same level of
     * information as the object. The two objects thus act as different representations of the same underlying concept.
     * Note that the correspondence relation is not necessarily bijective.</p>
     *
     * @param zenType The script object that should be converted.
     * @return The corresponding JEI object.
     * @throws NullPointerException If the parameter is {@code null}.
     *
     * @since 4.0.0
     */
    J toJeiFromZen(final Z zenType);
    
    /**
     * Converts the given JEI object to a corresponding script object.
     *
     * <p>A script object is defined as corresponding to a JEI object if and only if it carries the same level of
     * information as the object. The two objects thus act as different representations of the same underlying concept.
     * Note that the correspondence relation is not necessarily bijective.</p>
     *
     * @param jeiType The JEI object that should be converted.
     * @return The corresponding script object.
     * @throws NullPointerException If the parameter is {@code null}.
     *
     * @since 4.0.0
     */
    Z toZenFromJei(final J jeiType);
    
    /**
     * Obtains the <em>command string</em> of the given script object.
     *
     * <p>The command string is a string representation of the way the given object can be obtained by a scriptwriter.
     * It is usually in the form of a bracket expression, but no restrictions on it are placed. The returned string must
     * be a valid expression in the ZenCode programming language.</p>
     *
     * <p>The returned string should attempt to represent the contents of the given script object as much as possible,
     * carrying with it as much information as it can. Implementations are free to drop some data if they deem their
     * conversion into a ZenCode expression to be too cumbersome, unwieldy, or essentially impossible.</p>
     *
     * @param zenType The script object for which the command string should be obtained.
     * @return The command string.
     * @throws NullPointerException If the parameter is {@code null}.
     *
     * @implNote If the given script object implements the
     * {@link com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable} interface, it is suggested to leverage
     * its {@link com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable#getCommandString()} method to ensure
     * a consistent representation.
     *
     * @since 4.0.0
     */
    String toCommandStringFromZen(final Z zenType);
    
    /**
     * Obtains the registry name of the given JEI object as a {@link ResourceLocation}.
     *
     * <p>The registry name is the unique name that identifies the object itself or a part of it within a registry. It
     * is not thus not necessary for this conversion to represent a bijective relation: multiple objects may be assigned
     * the same registry name if the converter deems it necessary to do so.</p>
     *
     * <p>Note that if no registry for the given object exists, the converter should attempt to extract a name from the
     * given object anyway: returning {@code null} is (currently) not a supported situation. No restrictions are placed
     * on "fictitious" names that might be created by this method, as long as they follow the specifications of a
     * {@code ResourceLocation}. For this reason, this method might be considered the {@code ResourceLocation}
     * counterpart of {@link #toCommandStringFromJei(Object)}.</p>
     *
     * @param jeiType The JEI object for which the registry name should be obtained.
     * @return The registry name, never {@code null}.
     * @throws NullPointerException If the parameter is {@code null}.
     *
     * @since 4.0.0
     */
    ResourceLocation toRegistryNameFromJei(final J jeiType);
    
    /**
     * Obtains the <em>command string</em> of the given JEI object.
     *
     * <p>The command string is a string representation of the way the given object can be obtained by a scriptwriter.
     * It is usually in the form of a bracket expression, but no restrictions on it are placed. The returned string must
     * be a valid expression in the ZenCode programming language.</p>
     *
     * <p>The returned string should attempt to represent the contents of the given JEI object as much as possible,
     * carrying with it as much information as it can. Implementations are free to drop some data if they deem their
     * conversion into a ZenCode expression to be too cumbersome, unwieldy, or essentially impossible.</p>
     *
     * @param jeiType The JEI object for which the command string should be obtained.
     * @return The command string.
     * @throws NullPointerException If the parameter is {@code null}.
     *
     * @implSpec By default, this method is implemented as a simple bouncer to {@link #toCommandStringFromZen(Object)}
     * after the object has {@linkplain #toZenFromJei(Object) undergone conversion to a script object}.
     *
     * @since 4.0.0
     */
    default String toCommandStringFromJei(final J jeiType) {
        return this.toCommandStringFromZen(this.toZenFromJei(jeiType));
    }
    
    /**
     * Obtains the registry name of the given script object as a {@link ResourceLocation}.
     *
     * <p>The registry name is the unique name that identifies the object itself or a part of it within a registry. It
     * is not thus not necessary for this conversion to represent a bijective relation: multiple objects may be assigned
     * the same registry name if the converter deems it necessary to do so.</p>
     *
     * <p>Note that if no registry for the given object exists, the converter should attempt to extract a name from the
     * given object anyway: returning {@code null} is (currently) not a supported situation. No restrictions are placed
     * on "fictitious" names that might be created by this method, as long as they follow the specifications of a
     * {@code ResourceLocation}. For this reason, this method might be considered the {@code ResourceLocation}
     * counterpart of {@link #toCommandStringFromZen(Object)}.</p>
     *
     * @param zenType The script object for which the registry name should be obtained.
     * @return The registry name, never {@code null}.
     * @throws NullPointerException If the parameter is {@code null}.
     *
     * @implNote By default, this method is implemented as a simple bouncer to {@link #toRegistryNameFromJei(Object)}
     * after the object has {@linkplain #toJeiFromZen(Object) undergone conversion to a JEI object}.
     *
     * @since 4.0.0
     */
    default ResourceLocation toRegistryNameFromZen(final Z zenType) {
        return this.toRegistryNameFromJei(this.toJeiFromZen(zenType));
    }
}
