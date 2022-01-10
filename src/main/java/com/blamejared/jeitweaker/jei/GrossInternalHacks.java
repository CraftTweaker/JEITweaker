package com.blamejared.jeitweaker.jei;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.HandleHelper;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.Util;

import java.lang.invoke.MethodHandle;

final class GrossInternalHacks {
    private static final MethodHandle GET_INGREDIENT_MANAGER = Util.make(() -> {
        try {
            final Class<?> internal = Class.forName("mezz.jei.Internal");
            final Class<?> ingredientManager = Class.forName("mezz.jei.ingredients.IngredientManager");
            return HandleHelper.linkMethod(internal, "getIngredientManager", ingredientManager);
        } catch (final Exception e) {
            CraftTweakerAPI.LOGGER.error("Unable to link against JEI internals: some stuff won't work. Yes, it's gross", e);
            return null;
        }
    });
    
    @SuppressWarnings("ConstantConditions")
    static IIngredientManager getIngredientManager() {
        if (GET_INGREDIENT_MANAGER == null) return null;
        return HandleHelper.invoke(() -> (IIngredientManager) GET_INGREDIENT_MANAGER.invoke());
    }
}
