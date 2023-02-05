package com.blamejared.jeitweaker.common.api.ingredient.builtin;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import net.minecraft.world.item.ItemStack;

public final class BuiltinJeiIngredientTypes {
    private static final JeiIngredientType<ItemStack, IItemStack> ITEM_STACK = JeiIngredientType.of(
            JeiTweakerConstants.rl("item_stack"),
            ItemStack.class,
            IItemStack.class,
            new ItemStackConverters()
    );
    
    private BuiltinJeiIngredientTypes() {}
    
    public static JeiIngredientType<ItemStack, IItemStack> itemStack() {
        return ITEM_STACK;
    }
    
    public static JeiIngredientType<?, ?> fluidStack() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
