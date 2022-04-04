package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.api.BuiltinIngredientTypes;
import mezz.jei.api.constants.ModIds;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Expands {@link IItemStack} with JEI-specific conversions.
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Component/IItemStackExpansions")
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
@ZenRegister(modDeps = ModIds.JEI_ID)
public final class IItemStackExpansions {
    
    /**
     * Converts an {@link IItemStack} into its {@link RawJeiIngredient} equivalent.
     *
     * @param stack The stack to convert.
     * @return The equivalent ingredient.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static RawJeiIngredient asJeiIngredient(final IItemStack stack) {

        return JeiIngredient.of(BuiltinIngredientTypes.ITEM.get(), stack);
    }

    /**
     * Converts an {@link IItemStack} to a {@link JeiDrawable} that draws it as a JEI ingredient.
     *
     * @param stack The stack to convert.
     * @return A {@link JeiDrawable} that renders it.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static JeiDrawable asJeiDrawable(final IItemStack stack) {

        return JeiIngredientExpansions.asJeiDrawable(asJeiIngredient(stack));
    }
}
