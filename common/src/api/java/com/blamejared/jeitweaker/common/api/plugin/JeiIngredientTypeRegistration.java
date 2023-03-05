package com.blamejared.jeitweaker.common.api.plugin;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import mezz.jei.api.ingredients.IIngredientType;

/**
 * Manages the registration of {@link JeiIngredientType}s.
 *
 * <p>Clients of this API obtain an instance of this interface during the plugin initialization, in the
 * {@link JeiTweakerPluginProvider#registerIngredientTypes(JeiIngredientTypeRegistration)} method.</p>
 *
 * @see JeiTweakerPluginProvider
 * @since 4.0.0
 */
public interface JeiIngredientTypeRegistration {
    
    /**
     * Registers the specified {@link JeiIngredientType}, mapping it to the {@link JeiIngredientConverter} and JEI
     * {@link IIngredientType}.
     *
     * @param type The ingredient type to register.
     * @param converter The converter that should be responsible for conversions for the ingredient type.
     * @param jeiType The JEI API counterpart to the specified ingredient type.
     * @param <J> The JEI object type of the ingredient type.
     * @param <Z> The script object type of the ingredient type.
     *
     * @since 4.0.0
     */
    <J, Z> void registerIngredientType(final JeiIngredientType<J, Z> type, final JeiIngredientConverter<J, Z> converter, final IIngredientType<J> jeiType);
}
