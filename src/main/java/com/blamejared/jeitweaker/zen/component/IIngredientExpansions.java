package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mezz.jei.api.constants.ModIds;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

/**
 * Expands {@link IIngredient} with JEI-specific conversions.
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Component/IIngredientExpansions")
@ZenCodeType.Expansion("crafttweaker.api.ingredient.IIngredient")
@ZenRegister(modDeps = ModIds.JEI_ID)
public final class IIngredientExpansions {
    
    /**
     * Converts an {@link IIngredient} to an array of {@link RawJeiIngredient} for usage in recipe registration.
     *
     * @param ingredient The ingredient to convert
     * @return An array of JEI ingredients containing all possible outputs of the ingredient.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static RawJeiIngredient[] asJeiIngredientArray(final IIngredient ingredient) {
        
        return Arrays.stream(ingredient.getItems())
                .map(IItemStackExpansions::asJeiIngredient)
                .toArray(RawJeiIngredient[]::new);
    }
}
