package com.blamejared.jeitweaker.common.zen.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import com.blamejared.jeitweaker.common.api.ingredient.BuiltinJeiIngredientTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import org.openzen.zencode.java.ZenCodeType;

@TypedExpansion(IItemStack.class)
@ZenRegister
public final class IItemStackConverters {
    @ZenCodeType.Caster(implicit = true)
    public static ZenJeiIngredient asJeiIngredient(final IItemStack $this) {
        return JeiIngredients.toZenIngredient(JeiIngredient.ofZen(BuiltinJeiIngredientTypes.itemStack(), $this));
    }
}
