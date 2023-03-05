package com.blamejared.jeitweaker.common.api.zen.ingredient;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.common.api.zen.JeiTweakerZenConstants;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents the ZenCode counterpart to an ingredient that shows up in the JEI ingredient panel.
 *
 * <p>For context, the JEI ingredient panel is the list of ingredients visible by default on the right side of the
 * screen in any container.</p>
 *
 * <p>Most valid types can be automatically converted to instances of this class in ZenCode scripts, so no manual
 * conversion nor particular care is required on the scriptwriter part. Nevertheless, if manual conversions are desired,
 * then refer to the other classes available in this package.</p>
 *
 * <p><strong>Integration writers</strong>, on the other hand should ever operate with instances of this class outside
 * of the ZenCode script interoperability interface, preferring the unwrapped {@code JeiIngredient} class: fast wrapping
 * and unwrapping methods are provided through the {@code JeiIngredients} class.</p>
 *
 * @see com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient
 * @see com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients
 * @since 4.0.0
 */
@Document("mods/JeiTweaker/API/Ingredient/JeiIngredient")
@ZenCodeType.Name(JeiTweakerZenConstants.INGREDIENT_PACKAGE_NAME + ".JeiIngredient")
@ZenRegister
public interface ZenJeiIngredient extends CommandStringDisplayable {}
