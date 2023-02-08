package com.blamejared.jeitweaker.common.jei;

import com.blamejared.jeitweaker.common.JeiTweakerInitializer;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.blamejared.jeitweaker.common.api.command.JeiCommandTypes;
import com.blamejared.jeitweaker.common.command.CommandManager;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
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
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public final class JeiTweakerScriptJeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = JeiTweakerConstants.rl("script");
    
    private final CommandManager commandManager;
    
    public JeiTweakerScriptJeiPlugin() {
        this.commandManager = JeiTweakerInitializer.get().commandManager();
    }
    
    @Override
    public void registerItemSubtypes(final ISubtypeRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.ITEM_SUBTYPE, registration);
    }
    
    @Override
    public <T> void registerFluidSubtypes(final ISubtypeRegistration registration, final IPlatformFluidHelper<T> platformFluidHelper) {
        this.commandManager.executeCommands(JeiCommandTypes.FLUID_SUBTYPE, ObjectObjectImmutablePair.of(registration, platformFluidHelper));
    }
    
    @Override
    public void registerIngredients(final IModIngredientRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.INGREDIENT, registration);
    }
    
    @Override
    public void registerCategories(final IRecipeCategoryRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.RECIPE_CATEGORY, registration);
    }
    
    @Override
    public void registerVanillaCategoryExtensions(final IVanillaCategoryExtensionRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.VANILLA_EXTENSION, registration);
    }
    
    @Override
    public void registerRecipes(final IRecipeRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.RECIPE, registration);
    }
    
    @Override
    public void registerRecipeTransferHandlers(final IRecipeTransferRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.RECIPE_TRANSFER, registration);
    }
    
    @Override
    public void registerRecipeCatalysts(final IRecipeCatalystRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.RECIPE_CATALYST, registration);
    }
    
    @Override
    public void registerGuiHandlers(final IGuiHandlerRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.GUI_HANDLER, registration);
    }
    
    @Override
    public void registerAdvanced(final IAdvancedRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.ADVANCED, registration);
    }
    
    @Override
    public void registerRuntime(final IRuntimeRegistration registration) {
        this.commandManager.executeCommands(JeiCommandTypes.RUNTIME, registration);
    }
    
    @Override
    public void onRuntimeAvailable(final IJeiRuntime jeiRuntime) {
        this.commandManager.executeCommands(JeiCommandTypes.GENERAL, jeiRuntime);
    }
    
    @Override
    public void onRuntimeUnavailable() {
        this.commandManager.executeCommands(JeiCommandTypes.NO_CONTEXT, null);
    }
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
}
