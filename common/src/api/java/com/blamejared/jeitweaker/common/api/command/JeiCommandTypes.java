package com.blamejared.jeitweaker.common.api.command;

import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.Pair;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.IRuntimeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;

/**
 * Holds all available {@link JeiCommandType}s.
 *
 * <p>This class essentially acts as the enumeration part of the <strong>catalog type</strong>. Refer to the type
 * documentation for more information.</p>
 *
 * <p>It is illegal for a {@code JeiCommandType} to exist but not be represented by a constant in this class: in other
 * words, this list is by contract necessarily complete.</p>
 *
 * <p>Any guarantee made by the specific type's contract will be respected, but no further guarantees should be assumed.
 * In other words, the ordering of command invocation might differ from the order of JEI plugin reloading; at the same
 * time, any {@link mezz.jei.api.IModPlugin} method that might be referenced should be treated as merely indicative. No
 * further guarantees are made.</p>
 *
 * @since 4.0.0
 */
@SuppressWarnings("UnstableApiUsage")
public final class JeiCommandTypes {
    /**
     * Represents a command executed in a general context.
     *
     * <p>A general context is identified as being a state where a {@link IJeiRuntime} instance is available. No
     * guarantees are made on the phase where commands of this type may be invoked.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IJeiRuntime> GENERAL = JeiCommandType.of("general", IJeiRuntime.class);
    /**
     * Represents a command executed with a lack of any JEI context.
     *
     * <p>In other words, a command of this type is invoked whenever no JEI object is available for consumption. No
     * guarantees are made on the phase where commands of this type may be invoked.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<Void> NO_CONTEXT = JeiCommandType.of("no_context", Void.class);
    
    /**
     * Represents a command executed during the advanced registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IAdvancedRegistration} object and are guaranteed to be invoked as if
     * by {@link mezz.jei.api.IModPlugin#registerAdvanced(IAdvancedRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IAdvancedRegistration> ADVANCED = JeiCommandType.of("advanced", IAdvancedRegistration.class);
    /**
     * Represents a command executed during the fluid subtype registration phase of a plugin reload.
     *
     * <p>Commands of this type receive a {@link Pair} containing an {@link ISubtypeRegistration} instance as the
     * {@linkplain Pair#first() first object} and a {@link IPlatformFluidHelper} as the
     * {@linkplain Pair#second() second object}. Moreover, they are guaranteed to be invoked as if by
     * {@link mezz.jei.api.IModPlugin#registerFluidSubtypes(ISubtypeRegistration, IPlatformFluidHelper)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<Pair<ISubtypeRegistration, IPlatformFluidHelper<?>>> FLUID_SUBTYPE = JeiCommandType.of("fluid_subtype", new TypeToken<>() {});
    /**
     * Represents a command executed during the GUI handler registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IGuiHandlerRegistration} object and are guaranteed to be invoked as if
     * by {@link mezz.jei.api.IModPlugin#registerGuiHandlers(IGuiHandlerRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IGuiHandlerRegistration> GUI_HANDLER = JeiCommandType.of("gui_handler", IGuiHandlerRegistration.class);
    /**
     * Represents a command executed during the ingredient registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IModIngredientRegistration} object and are guaranteed to be invoked as
     * if by {@link mezz.jei.api.IModPlugin#registerIngredients(IModIngredientRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IModIngredientRegistration> INGREDIENT = JeiCommandType.of("ingredient", IModIngredientRegistration.class);
    /**
     * Represents a command executed during the item subtype registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link ISubtypeRegistration} instance and are guaranteed to be invoked as if
     * by {@link mezz.jei.api.IModPlugin#registerItemSubtypes(ISubtypeRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<ISubtypeRegistration> ITEM_SUBTYPE = JeiCommandType.of("item_subtype", ISubtypeRegistration.class);
    /**
     * Represents a command executed during the recipe registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IRecipeRegistration} instance and are guaranteed to be invoked as if
     * by {@link mezz.jei.api.IModPlugin#registerRecipes(IRecipeRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IRecipeRegistration> RECIPE = JeiCommandType.of("recipe", IRecipeRegistration.class);
    /**
     * Represents a command executed during the recipe catalyst registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IRecipeCatalystRegistration} instance and are guaranteed to be invoked
     * as if by {@link mezz.jei.api.IModPlugin#registerRecipeCatalysts(IRecipeCatalystRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IRecipeCatalystRegistration> RECIPE_CATALYST = JeiCommandType.of("recipe_catalyst", IRecipeCatalystRegistration.class);
    /**
     * Represents a command executed during the recipe category registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IRecipeCategoryRegistration} instance and are guaranteed to be invoked
     * as if by {@link mezz.jei.api.IModPlugin#registerCategories(IRecipeCategoryRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IRecipeCategoryRegistration> RECIPE_CATEGORY = JeiCommandType.of("recipe_category", IRecipeCategoryRegistration.class);
    /**
     * Represents a command executed during the recipe transfer registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IRecipeTransferRegistration} instance and are guaranteed to be invoked
     * as if by {@link mezz.jei.api.IModPlugin#registerRecipeTransferHandlers(IRecipeTransferRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IRecipeTransferRegistration> RECIPE_TRANSFER = JeiCommandType.of("recipe_transfer", IRecipeTransferRegistration.class);
    /**
     * Represents a command executed during the runtime registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IRuntimeRegistration} instance and are guaranteed to be invoked as if
     * by {@link mezz.jei.api.IModPlugin#registerRuntime(IRuntimeRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IRuntimeRegistration> RUNTIME = JeiCommandType.of("runtime", IRuntimeRegistration.class);
    /**
     * Represents a command executed during the vanilla category extension registration phase of a plugin reload.
     *
     * <p>Commands of this type receive an {@link IVanillaCategoryExtensionRegistration} instance and are guaranteed to
     * be invoked as if by
     * {@link mezz.jei.api.IModPlugin#registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration)}.</p>
     *
     * @since 4.0.0
     */
    public static final JeiCommandType<IVanillaCategoryExtensionRegistration> VANILLA_EXTENSION = JeiCommandType.of("vanilla_extension", IVanillaCategoryExtensionRegistration.class);
    
    private JeiCommandTypes() {}
    
    static void init() {}
}
