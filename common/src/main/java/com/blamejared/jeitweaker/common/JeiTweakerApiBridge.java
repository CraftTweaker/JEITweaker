package com.blamejared.jeitweaker.common;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.jeitweaker.common.api.JeiTweakerApi;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import com.blamejared.jeitweaker.common.ingredient.SimpleJeiIngredientCreator;
import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import com.blamejared.jeitweaker.common.zen.ingredient.JeiIngredientBundlingZenJeiIngredient;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

public final class JeiTweakerApiBridge implements JeiTweakerApi {
    
    @Override
    public JeiIngredientCreator ingredientCreator() {
        return SimpleJeiIngredientCreator.get();
    }
    
    @Override
    public <J, Z> ZenJeiIngredient ingredientZenFromJei(final JeiIngredient<J, Z> jeiIngredient) {
        return JeiIngredientBundlingZenJeiIngredient.of(jeiIngredient);
    }
    
    @Override
    public <J, Z> JeiIngredient<J, Z> ingredientJeiFromZen(final ZenJeiIngredient zenJeiIngredient) {
        if (zenJeiIngredient instanceof final JeiIngredientBundlingZenJeiIngredient<?, ?> wrapped) {
            return GenericUtil.uncheck(wrapped.unwrap());
        }
        throw new IllegalArgumentException("Illegal ZenJeiIngredient %s: not obtained through JeiIngredients".formatted(zenJeiIngredient));
    }
    
    @Override
    public <J, Z> JeiIngredientType<J, Z> ingredientTypeFromIdentifier(final ResourceLocation id) {
        return JeiTweakerInitializer.get().registries().jeiIngredientTypeRegistry().findById(id);
    }
    
    @Override
    public <J, Z> JeiIngredientType<J, Z> ingredientTypeFromJei(final IIngredientType<J> jeiType) {
        return JeiTweakerInitializer.get().registries().jeiIngredientTypeRegistry().findByJeiTypeOrNull(jeiType);
    }
    
    @Override
    public <J, Z> JeiIngredientConverter<J, Z> ingredientConverterFromIngredientType(final JeiIngredientType<J, Z> type) {
        return JeiTweakerInitializer.get().registries().jeiIngredientTypeRegistry().converterFor(type);
    }
    
    @Override
    public <J, Z> IIngredientType<J> jeiFromIngredientType(final JeiIngredientType<J, Z> type) {
        return JeiTweakerInitializer.get().registries().jeiIngredientTypeRegistry().jeiTypeOf(type);
    }
    
    @Override
    public boolean shouldApplyAction() {
        return PlatformBridge.INSTANCE.isClient();
    }
    
    @Override
    public <T> void enqueueCommand(final JeiCommand<T> command) {
        JeiTweakerInitializer.get().commandManager().enqueueCommand(command);
    }
    
}
