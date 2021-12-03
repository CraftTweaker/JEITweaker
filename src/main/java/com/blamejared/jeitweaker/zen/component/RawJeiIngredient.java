package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents any ingredient used in JEI.
 *
 * <p>JEI identifies as an ingredient everything that can be used in any type of recipe, both as input and as output.
 * Even elements in information boxes are considered ingredients. The JEI user interface renders by default a list of
 * all available ingredients on the right side of any menu.</p>
 *
 * <p>Most things you interact with in scripts normally can already freely convert to a JEI ingredient without any
 * additional help. Refer to {@link IFluidStackExpansions#asJeiIngredient(IFluidStack)} and
 * {@link IItemStackExpansions#asJeiIngredient(IItemStack)} for more information.</p>
 *
 * <p>Moreover, other types can automatically convert to an array of JEI ingredients, for even less boilerplate and ease
 * of usage. Refer to {@link IIngredientExpansions#asJeiIngredientArray(IIngredient)} and
 * {@link ItemTagExpansions#asJeiIngredientArray(MCTag)} for more information.</p>
 *
 * <p><strong>For mod developers:</strong> do not use this class internally, use the generic version.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEI/API/Component/JeiIngredient")
@ZenCodeType.Name("mods.jei.component.JeiIngredient")
@ZenRegister
// TODO("Replace all usages of this with JeiIngredient once generic inference is better")
public interface RawJeiIngredient extends CommandStringDisplayable {
    
    /**
     * Casts this raw ingredient to the generic version.
     *
     * @param <T> The exposed type of the ingredient.
     * @param <U> The internal type of the ingredient.
     * @return {@code this}, but generified.
     *
     * @since 1.1.0
     */
    @SuppressWarnings("unchecked")
    default <T, U> JeiIngredient<T, U> cast() {
        
        return (JeiIngredient<T, U>) this;
    }
}
