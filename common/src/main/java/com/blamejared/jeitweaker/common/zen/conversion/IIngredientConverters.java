package com.blamejared.jeitweaker.common.zen.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import com.blamejared.jeitweaker.common.api.ingredient.BuiltinJeiIngredientTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JeiTweaker/API/IIngredientConverters")
@TypedExpansion(IIngredient.class)
@ZenRegister
public final class IIngredientConverters {
    private IIngredientConverters() {}
    
    @ZenCodeType.Caster(implicit = true)
    public static ZenJeiIngredient[] asJeiIngredient(final IIngredient $this) {
        final IItemStack[] items = $this.getItems();
        final int s = items.length;
        
        final ZenJeiIngredient[] array = new ZenJeiIngredient[s];
        for (int i = 0; i < s; ++i) {
            array[i] = IItemStackConverters.asJeiIngredient(items[i]);
        }
        
        return array;
    }
}
