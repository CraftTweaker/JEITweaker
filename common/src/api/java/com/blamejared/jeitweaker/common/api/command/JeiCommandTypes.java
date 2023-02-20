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

@SuppressWarnings("UnstableApiUsage")
public final class JeiCommandTypes {
    public static final JeiCommandType<IJeiRuntime> GENERAL = JeiCommandType.of("general", IJeiRuntime.class);
    public static final JeiCommandType<Void> NO_CONTEXT = JeiCommandType.of("no_context", Void.class);
    
    public static final JeiCommandType<IAdvancedRegistration> ADVANCED = JeiCommandType.of("advanced", IAdvancedRegistration.class);
    public static final JeiCommandType<Pair<ISubtypeRegistration, IPlatformFluidHelper<?>>> FLUID_SUBTYPE = JeiCommandType.of("fluid_subtype", new TypeToken<>() {});
    public static final JeiCommandType<IGuiHandlerRegistration> GUI_HANDLER = JeiCommandType.of("gui_handler", IGuiHandlerRegistration.class);
    public static final JeiCommandType<IModIngredientRegistration> INGREDIENT = JeiCommandType.of("ingredient", IModIngredientRegistration.class);
    public static final JeiCommandType<ISubtypeRegistration> ITEM_SUBTYPE = JeiCommandType.of("item_subtype", ISubtypeRegistration.class);
    public static final JeiCommandType<IRecipeRegistration> RECIPE = JeiCommandType.of("recipe", IRecipeRegistration.class);
    public static final JeiCommandType<IRecipeCatalystRegistration> RECIPE_CATALYST = JeiCommandType.of("recipe_catalyst", IRecipeCatalystRegistration.class);
    public static final JeiCommandType<IRecipeCategoryRegistration> RECIPE_CATEGORY = JeiCommandType.of("recipe_category", IRecipeCategoryRegistration.class);
    public static final JeiCommandType<IRecipeTransferRegistration> RECIPE_TRANSFER = JeiCommandType.of("recipe_transfer", IRecipeTransferRegistration.class);
    public static final JeiCommandType<IRuntimeRegistration> RUNTIME = JeiCommandType.of("runtime", IRuntimeRegistration.class);
    public static final JeiCommandType<IVanillaCategoryExtensionRegistration> VANILLA_EXTENSION = JeiCommandType.of("vanilla_extension", IVanillaCategoryExtensionRegistration.class);
    
    private JeiCommandTypes() {}
    
    static void init() {}
}
