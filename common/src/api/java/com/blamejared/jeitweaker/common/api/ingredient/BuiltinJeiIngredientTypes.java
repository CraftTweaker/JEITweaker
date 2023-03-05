package com.blamejared.jeitweaker.common.api.ingredient;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.google.common.base.Suppliers;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Provides access to {@link JeiIngredientType}s builtin to JeiTweaker.
 *
 * <p>These same ingredient types can also be obtained through
 * {@link JeiIngredientTypes#findById(net.minecraft.resources.ResourceLocation)} with the names documented on the
 * various getters. Nevertheless, it is suggested to use the getters directly as a matter of API stability. It is not
 * necessary for the caller to cache the result of the calls.</p>
 *
 * @since 4.0.0
 */
public final class BuiltinJeiIngredientTypes {
    private static final Supplier<? extends JeiIngredientType<ItemStack, IItemStack>> ITEM_STACK = find("item_stack");
    private static final Supplier<? extends JeiIngredientType<?, ?>> FLUID_STACK = find("fluid_stack");
    
    private BuiltinJeiIngredientTypes() {}
    
    /**
     * Obtains the {@link JeiIngredientType} representing an item stack.
     *
     * <p>The corresponding ingredient type is registered under the {@code "jeitweaker:item_stack"} unique
     * identifier.</p>
     *
     * @return The item stack ingredient type.
     *
     * @since 4.0.0
     */
    public static JeiIngredientType<ItemStack, IItemStack> itemStack() {
        return ITEM_STACK.get();
    }
    
    /**
     * Obtains the {@link JeiIngredientType} representing a fluid stack.
     *
     * <p>The corresponding ingredient type is registered under the {@code "jeitweaker:fluid_stack"} unique
     * identifier.</p>
     *
     * @return The fluid stack ingredient type.
     *
     * @apiNote The returned ingredient type is platform-specific, therefore particular take must be taken when
     * operating with it directly.
     *
     * @since 4.0.0
     */
    // TODO("Verify source-code compatibility of changing the generic of the return type of a method")
    public static JeiIngredientType<?, ?> fluidStack() {
        return FLUID_STACK.get();
    }
    
    private static <J, Z> Supplier<? extends JeiIngredientType<J, Z>> find(final String path) {
        return Suppliers.memoize(() -> JeiIngredientTypes.findById(JeiTweakerConstants.rl(path)));
    }
}
