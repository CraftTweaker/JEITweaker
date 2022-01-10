package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Expands {@link RawJeiIngredient} with additional JEI-specific conversions.
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Component/JeiIngredientExpansions")
@ZenCodeType.Expansion("mods.jei.component.JeiIngredient")
@ZenRegister
public final class JeiIngredientExpansions {
    
    /**
     * Converts a {@link RawJeiIngredient} to a {@link JeiDrawable} that draws it.
     *
     * @param ingredient The ingredient to convert.
     * @return A {@link JeiDrawable} that draws it.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static JeiDrawable asJeiDrawable(final RawJeiIngredient ingredient) {
        
        return JeiDrawable.of(ingredient);
    }
}
