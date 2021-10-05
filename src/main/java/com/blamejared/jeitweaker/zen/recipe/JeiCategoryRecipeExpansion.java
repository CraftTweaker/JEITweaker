package com.blamejared.jeitweaker.zen.recipe;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.jeitweaker.actions.ActionAddRecipeToCategory;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

//@Document("mods/JEI/Recipe/JeiCategoryRecipeExpansion")
@ZenCodeType.Expansion("mods.jei.category.JeiCategory")
@ZenRegister
public final class JeiCategoryRecipeExpansion {
    
    @ZenCodeType.Method
    public static void addRecipe(final JeiCategory category, final RawJeiIngredient[][] outputs, final RawJeiIngredient[][] inputs) {
        
        addRecipe(category, outputs, inputs, graphics -> {});
    }
    
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
