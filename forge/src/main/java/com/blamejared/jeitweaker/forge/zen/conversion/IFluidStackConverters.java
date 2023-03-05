package com.blamejared.jeitweaker.forge.zen.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import com.blamejared.jeitweaker.common.api.ingredient.BuiltinJeiIngredientTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Handles automatic conversions between {@link IFluidStack}s and {@link ZenJeiIngredient}s.
 *
 * <p>Scriptwriters should never require using this class explicitly.</p>
 *
 * @since 4.0.0
 */
@Document("mods/JeiTweaker/API/IFluidStackConverters")
@TypedExpansion(IFluidStack.class)
@ZenRegister
public final class IFluidStackConverters {
    private IFluidStackConverters() {}
    
    /**
     * Converts the given {@link IFluidStack} into the corresponding {@link ZenJeiIngredient}.
     *
     * @param $this The stack to convert.
     * @return The corresponding ingredient.
     *
     * @docParam $this <fluid:minecraft:lava>
     *
     * @since 4.0.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static ZenJeiIngredient asJeiIngredient(final IFluidStack $this) {
        return JeiIngredients.toZenIngredient(JeiIngredient.ofZen(GenericUtil.uncheck(BuiltinJeiIngredientTypes.fluidStack()), $this));
    }
}
