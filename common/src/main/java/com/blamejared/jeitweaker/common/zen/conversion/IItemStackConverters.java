package com.blamejared.jeitweaker.common.zen.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import com.blamejared.jeitweaker.common.api.ingredient.BuiltinJeiIngredientTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Handles automatic conversion between {@link IItemStack}s and {@link ZenJeiIngredient}s.
 *
 * <p>Scriptwriters should never require using this class explicitly.</p>
 *
 * @since 4.0.0
 */
@Document("mods/JeiTweaker/API/IItemStackConverters")
@TypedExpansion(IItemStack.class)
@ZenRegister
public final class IItemStackConverters {
    private IItemStackConverters() {}
    
    /**
     * Converts the given {@link IItemStack} into the corresponding {@link ZenJeiIngredient}.
     *
     * @param $this The stack to convert.
     * @return The resulting ingredient.
     *
     * @docParam $this <item:minecraft:diamond>
     *
     * @since 4.0.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static ZenJeiIngredient asJeiIngredient(final IItemStack $this) {
        return JeiIngredients.toZenIngredient(JeiIngredient.ofZen(BuiltinJeiIngredientTypes.itemStack(), $this));
    }
}
