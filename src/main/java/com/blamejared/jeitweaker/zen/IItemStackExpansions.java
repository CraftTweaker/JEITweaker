package com.blamejared.jeitweaker.zen;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.state.JeiTweakerIngredientType;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEITweaker/IItemStackExpansions")
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
@ZenRegister
public final class IItemStackExpansions {
    @ZenCodeType.Caster(implicit = true)
    public static JeiIngredient<IItemStack, ItemStack> asJeiIngredient(final IItemStack stack) {
        return new JeiIngredient<>(JeiTweakerIngredientType.ITEM, stack);
    }
}
