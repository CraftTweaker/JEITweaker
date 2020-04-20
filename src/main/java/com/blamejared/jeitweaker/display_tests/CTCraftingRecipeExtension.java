package com.blamejared.jeitweaker.display_tests;


import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.recipes.*;
import com.blamejared.jeitweaker.*;
import mezz.jei.api.*;
import mezz.jei.api.constants.*;
import mezz.jei.api.gui.*;
import mezz.jei.api.gui.ingredient.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.*;
import mezz.jei.gui.ingredients.*;
import net.minecraft.util.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CTCraftingRecipeExtension implements ICustomCraftingCategoryExtension {
    
    private final CTRecipeShaped recipe;
    private final IIngredient[][] ingredients;
    
    public CTCraftingRecipeExtension(CTRecipeShaped recipe) {
        this.recipe = recipe;
        IIngredient[][] toSet = null;
        try {
            final Field ingredients = CTRecipeShaped.class.getDeclaredField("ingredients");
            ingredients.setAccessible(true);
            toSet = (IIngredient[][]) ingredients.get(recipe);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        
        this.ingredients = toSet;
    }
    
    @Override
    public void setIngredients(IIngredients ingredients) {
        final List<IIngredient> inputs = new ArrayList<>();
        
        for(IIngredient[] row : this.ingredients) {
            Collections.addAll(inputs, row);
        }
        ingredients.setInputs(JEIAddonPlugin.I_INGREDIENT_TYPE, inputs);
        ingredients.setOutput(JEIAddonPlugin.I_INGREDIENT_TYPE, new MCItemStack(recipe.getRecipeOutput()));
        
        
        //Leave them in for now for JEI matching
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }
    
    @Override
    public void drawInfo(int recipeWidth, int recipeHeight, double mouseX, double mouseY) {
    }
    
    @Override
    public List<String> getTooltipStrings(double mouseX, double mouseY) {
        return Collections.emptyList();
    }
    
    @Override
    public boolean handleClick(double mouseX, double mouseY, int mouseButton) {
        return false;
    }
    
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getId();
    }
    
    @Nullable
    @Override
    public Size2i getSize() {
        return new Size2i(recipe.getRecipeWidth(), recipe.getRecipeHeight());
    }
    
    
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
        final IGuiIngredientGroup<IIngredient> ingredientsGroup = recipeLayout.getIngredientsGroup(JEIAddonPlugin.I_INGREDIENT_TYPE);
        
        ingredientsGroup.init(0, false, new CTIngredientRenderer(), 94, 18, GuiIngredientProperties
                .getWidth(1), GuiIngredientProperties.getHeight(1), 1, 1);
        ingredientsGroup.set(0, new MCItemStack(recipe.getRecipeOutput()));
        
        
        for(int i = 1; i < 10; i++) {
            ingredientsGroup.init(i, true, new CTIngredientRenderer() , (i - 1) % 3 * 18, (i - 1) / 3 * 18, GuiIngredientProperties
                    .getWidth(1), GuiIngredientProperties.getHeight(1), 1, 1);
            final IIngredient iIngredient = getIIngredient(i);
            if(iIngredient != null) {
                ingredientsGroup.set(i, iIngredient);
            }
        }
        
        ingredientsGroup.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            final IIngredient iIngredient = getIIngredient(slotIndex);
            if(iIngredient instanceof IngredientExpansion.MCIngredientWithJEIText) {
                final String text = ((IngredientExpansion.MCIngredientWithJEIText) iIngredient).getText();
                tooltip.add(tooltip.size() - 1, text);
            }
        });
        
        
        /*
        final IGuiItemStackGroup ingredientsGroup = recipeLayout.getItemStacks();
        ingredientsGroup.set(0, recipe.getRecipeOutput());
        
        for(int i = 1; i < 10; i++) {
            final IIngredient iIngredient = getIIngredient(i);
            if(iIngredient != null) {
                final ItemStack[] stacks = iIngredient.asVanillaIngredient().getMatchingStacks();
                ingredientsGroup.set(i, Arrays.asList(stacks));
            }
        }
        
        ingredientsGroup.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            final IIngredient iIngredient = getIIngredient(slotIndex);
            if(iIngredient instanceof IngredientExpansion.MCIngredientWithJEIText) {
                final IngredientExpansion.MCIngredientWithJEIText withJEIText = (IngredientExpansion.MCIngredientWithJEIText) iIngredient;
                //-1 to add it before the Mod name
                tooltip.add(tooltip.size() - 1, withJEIText.getText());
            }
        });
        */
    }
    
    @Nullable
    public IIngredient getIIngredient(int slotIndex) {
        //Index 0 is output
        if(slotIndex == 0) {
            return new MCItemStack(recipe.getRecipeOutput());
        }
        
        slotIndex--;
        
        final int row = slotIndex / 3;
        if(this.ingredients.length <= row) {
            return null;
        }
        
        final int column = slotIndex % 3;
        final IIngredient[] ingredient = this.ingredients[row];
        return ingredient.length > column ? ingredient[column] : null;
    }
}
