package com.blamejared.jeitweaker.common.api;

import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

/**
 * Represents the basic interface through which the JeiTweaker API can query the implementation.
 *
 * <p>An instance of this class can be obtained through {@link #get()}. Nevertheless, clients <strong>should
 * <em>never</em> require</strong> to obtain an instance of this class. On the contrary, access to these methods is
 * already provided in more sensible places across the API: those methods should thus be preferred. The canonical access
 * point for each of these methods will be provided in the documentation.</p>
 *
 * <p>Clients should also note that, while this interface is still considered public API, minor breaking changes might
 * still be allowed: depending on this class is therefore "at your own risk".</p>
 *
 * @since 4.0.0
 */
public interface JeiTweakerApi {
    
    /**
     * Obtains an instance of this interface, for method invocation.
     *
     * <p>Clients should never require invocation of this method.</p>
     *
     * @return The api instance.
     *
     * @since 4.0.0
     */
    static JeiTweakerApi get() {
        return ApiBridgeInstanceHolder.get();
    }
    
    /**
     * Obtains the {@link JeiIngredientCreator} instance used by the implementation.
     *
     * <p>Clients should never require nor request access to this interface by themselves, therefore no alternative is
     * provided. Depending on this method is thus "at your own risk".</p>
     *
     * @return The ingredient creator.
     *
     * @since 4.0.0
     */
    JeiIngredientCreator ingredientCreator();
    
    /**
     * Converts the given {@link JeiIngredient} into a {@link ZenJeiIngredient}.
     *
     * <p>Clients should prefer using
     * {@link com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients#toZenIngredient(JeiIngredient)} instead.</p>
     *
     * @param jeiIngredient The ingredient to convert.
     * @return The conversion result.
     * @param <J> The JEI object type.
     * @param <Z> The script object type.
     * @throws NullPointerException If the argument is {@code null}.
     *
     * @since 4.0.0
     */
    <J, Z> ZenJeiIngredient ingredientZenFromJei(final JeiIngredient<J, Z> jeiIngredient);
    
    /**
     * Converts the given {@link ZenJeiIngredient} into a {@link JeiIngredient}.
     *
     * <p>Clients should prefer using
     * {@link com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients#toJeiIngredient(ZenJeiIngredient)}
     * instead.</p>
     *
     * @param zenJeiIngredient The ingredient to convert.
     * @return The conversion result.
     * @param <J> The JEI object type.
     * @param <Z> The script object type.
     * @throws NullPointerException If the argument is {@code null}.
     * @throws IllegalArgumentException If the argument is invalid.
     *
     * @since 4.0.0
     */
    <J, Z> JeiIngredient<J, Z> ingredientJeiFromZen(final ZenJeiIngredient zenJeiIngredient);
    
    /**
     * Gets the {@link JeiIngredientType} corresponding to the given {@link ResourceLocation} id.
     *
     * <p>Clients should prefer using
     * {@link com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientTypes#findById(ResourceLocation)}
     * instead.</p>
     *
     * @param id The identifier.
     * @return The matching ingredient type.
     * @param <J> The JEI object type.
     * @param <Z> The script object type.
     * @throws NullPointerException If the identifier is {@code null}.
     * @throws IllegalArgumentException If no ingredient type is found.
     *
     * @since 4.0.0
     */
    <J, Z> JeiIngredientType<J, Z> ingredientTypeFromIdentifier(final ResourceLocation id);
    
    /**
     * Gets the {@link JeiIngredientType} corresponding to the given JEI {@link IIngredientType}, if it exists.
     *
     * <p>Clients should prefer using
     * {@link com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientTypes#fromJeiTypeOrNull(IIngredientType)}
     * instead.</p>
     *
     * @param jeiType The JEI ingredient type.
     * @return The corresponding ingredient type, if it exists; {@code null} otherwise.
     * @param <J> The JEI object type.
     * @param <Z> The script object type.
     *
     * @since 4.0.0
     */
    <J, Z> JeiIngredientType<J, Z> ingredientTypeFromJei(final IIngredientType<J> jeiType);
    
    /**
     * Obtains the {@link JeiIngredientConverter} corresponding to the given {@link JeiIngredientType}.
     *
     * <p>Clients should prefer using
     * {@link com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientTypes#converterFor(JeiIngredientType)}
     * instead.</p>
     *
     * @param type The ingredient type.
     * @return The converter.
     * @param <J> The JEI object type.
     * @param <Z> The script object type.
     * @throws NullPointerException If the type is {@code null}.
     * @throws IllegalArgumentException If the type is unknown.
     *
     * @since 4.0.0
     */
    <J, Z> JeiIngredientConverter<J, Z> ingredientConverterFromIngredientType(final JeiIngredientType<J, Z> type);
    
    /**
     * Gets the JEI {@link IIngredientType} corresponding to the given {@link JeiIngredientType}.
     *
     * <p>Clients should prefer using
     * {@link com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientTypes#toJeiType(JeiIngredientType)}
     * instead.</p>
     *
     * @param type The ingredient type.
     * @return The JEI ingredient type.
     * @param <J> The JEI object type.
     * @param <Z> The script object type.
     * @throws NullPointerException If the type is {@code null}.
     * @throws IllegalArgumentException If the type is unknown.
     *
     * @since 4.0.0
     */
    <J, Z> IIngredientType<J> jeiFromIngredientType(final JeiIngredientType<J, Z> type);
    
    /**
     * Gets whether an action should be applied.
     *
     * <p>Clients should never require invoking this method, unless they are creating a custom
     * {@link com.blamejared.crafttweaker.api.action.base.IAction} implementation that does not extend from
     * {@link com.blamejared.jeitweaker.common.api.action.JeiTweakerAction}. Such a situation is therefore discouraged
     * and this method is provided "at your own risk".</p>
     *
     * @return Whether an action should be applied.
     *
     * @since 4.0.0
     */
    boolean shouldApplyAction();
    
    /**
     * Enqueues the given {@link JeiCommand} to be executed.
     *
     * <p>Clients should prefer using the equivalent method in
     * {@link com.blamejared.jeitweaker.common.api.action.JeiTweakerAction} instead. Not extending this class for
     * JeiTweaker actions is discouraged, thus this method is also provided "at your own risk" if the client is simply
     * implementing {@link com.blamejared.crafttweaker.api.action.base.IAction}.</p>
     *
     * @param command The command to execute.
     * @param <T> The type of the argument.
     *
     * @since 4.0.0
     */
    <T> void enqueueCommand(final JeiCommand<T> command);
}
