package com.blamejared.jeitweaker.common.api;

import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

public interface JeiTweakerApi {
    static JeiTweakerApi get() {
        return ApiBridgeInstanceHolder.get();
    }
    
    JeiIngredientCreator ingredientCreator();
    
    <J, Z> ZenJeiIngredient ingredientZenFromJei(final JeiIngredient<J, Z> jeiIngredient);
    <J, Z> JeiIngredient<J, Z> ingredientJeiFromZen(final ZenJeiIngredient zenJeiIngredient);
    
    <J, Z> JeiIngredientType<J, Z> ingredientTypeFromIdentifier(final ResourceLocation id);
    <J, Z> JeiIngredientType<J, Z> ingredientTypeFromJei(final IIngredientType<J> jeiType);
    <J, Z> JeiIngredientConverter<J, Z> ingredientConverterFromIngredientType(final JeiIngredientType<J, Z> type);
    <J, Z> IIngredientType<J> jeiFromIngredientType(final JeiIngredientType<J, Z> type);
    
    <T> void enqueueCommand(final JeiCommand<T> command);
}
