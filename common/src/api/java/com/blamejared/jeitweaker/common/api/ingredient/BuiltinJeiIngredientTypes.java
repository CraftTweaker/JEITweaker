package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.google.common.base.Suppliers;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public final class BuiltinJeiIngredientTypes {
    private static final Supplier<? extends JeiIngredientType<ItemStack, IItemStack>> ITEM_STACK = find("item_stack");
    private static final Supplier<? extends JeiIngredientType<?, ?>> FLUID_STACK = find("fluid_stack");
    
    private BuiltinJeiIngredientTypes() {}
    
    public static JeiIngredientType<ItemStack, IItemStack> itemStack() {
        return ITEM_STACK.get();
    }
    
    public static JeiIngredientType<?, ?> fluidStack() {
        return FLUID_STACK.get();
    }
    
    private static <J, Z> Supplier<? extends JeiIngredientType<J, Z>> find(final String path) {
        return Suppliers.memoize(() -> JeiIngredientTypes.findById(JeiTweakerConstants.rl(path)));
    }
}
