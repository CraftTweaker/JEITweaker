package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.jeitweaker.api.BuiltinIngredientTypes;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Expands {@link IFluidStack} with JEI-specific conversions.
 *
 * @since 1.1.0
 */
//@Document("mods/JEI/Component/IFluidStackExpansions")
@ZenCodeType.Expansion("crafttweaker.api.fluid.IFluidStack")
@ZenRegister
public final class IFluidStackExpansions {
    
    /**
     * Converts a {@link IFluidStack} into its {@link RawJeiIngredient} equivalent.
     *
     * @param stack The stack to convert.
     * @return The equivalent ingredient.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static RawJeiIngredient asJeiIngredient(final IFluidStack stack) {

        return new JeiIngredient<>(BuiltinIngredientTypes.FLUID.get(), stack);
    }

    /**
     * Converts a {@link IFluidStack} to a {@link JeiDrawable} that draws it as a JEI ingredient.
     *
     * @param stack The stack to convert.
     * @return A {@link JeiDrawable} that renders it.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static JeiDrawable asJeiDrawable(final IFluidStack stack) {

        return JeiIngredientExpansions.asJeiDrawable(asJeiIngredient(stack));
    }
}
