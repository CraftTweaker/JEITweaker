package com.blamejared.jeitweaker.zen.recipe;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.actions.ActionAddRecipeToCategory;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * Expands a {@link JeiCategory} allowing for registration of {@link JeiRecipe}s.
 *
 * @since 1.1.0
 */
@Document("mods/JEI/API/Recipe/JeiCategoryRecipeExpansion")
@ZenCodeType.Expansion("mods.jei.category.JeiCategory")
@ZenRegister
public final class JeiCategoryRecipeExpansion {
    
    /**
     * Adds a new recipe to the specified category with the given inputs and outputs.
     *
     * @param category The category to add the recipe to.
     * @param outputs The outputs of the recipe. Refer to {@link JeiRecipe} for more details on the format.
     * @param inputs The inputs of the recipe. Refer to {@link JeiRecipe} for more details on the format.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static void addRecipe(final JeiCategory category, final RawJeiIngredient[][] outputs, final RawJeiIngredient[][] inputs) {
        
        addRecipe(category, outputs, inputs, graphics -> {});
    }
    
    /**
     * Adds a new recipe to the specified category with the given inputs and outputs and the specified graphics.
     *
     * @param category The category to add the recipe to.
     * @param outputs The outputs of the recipe. Refer to {@link JeiRecipe} for more details on the format.
     * @param inputs The inputs of the recipe. Refer to {@link JeiRecipe} for more details on the format.
     * @param graphics A consumer for a {@link RecipeGraphics} allowing for more integration with the category. Each
     *                 category defines a different contract for the graphics, so refer to the category documentation
     *                 for more information.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static void addRecipe(
            final JeiCategory category,
            final RawJeiIngredient[][] outputs,
            final RawJeiIngredient[][] inputs,
            final Consumer<RecipeGraphics> graphics
    ) {
        
        final JeiRecipe recipe = new JeiRecipe(category, inputs, outputs, graphics);
        CraftTweakerAPI.apply(new ActionAddRecipeToCategory(category, recipe));
    }
}
