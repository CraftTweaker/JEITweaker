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

@Document("mods/JeiTweaker/API/IFluidStackConverters")
@TypedExpansion(IFluidStack.class)
@ZenRegister
public final class IFluidStackConverters {
    private IFluidStackConverters() {}
    
    @ZenCodeType.Caster(implicit = true)
    public static ZenJeiIngredient asJeiIngredient(final IFluidStack $this) {
        return JeiIngredients.toZenIngredient(JeiIngredient.ofZen(GenericUtil.uncheck(BuiltinJeiIngredientTypes.fluidStack()), $this));
    }
}
