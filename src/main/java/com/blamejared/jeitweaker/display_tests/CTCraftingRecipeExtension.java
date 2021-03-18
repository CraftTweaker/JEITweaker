package com.blamejared.jeitweaker.display_tests;


import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.recipes.*;
import com.mojang.blaze3d.platform.*;
import mezz.jei.api.*;
import mezz.jei.api.constants.*;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CTCraftingRecipeExtension implements ICustomCraftingCategoryExtension {
    
    private final CTRecipeShaped recipe;
    private final IIngredient[][] ingredients;
    private final IIngredient[] ingredients1d;
    
    public CTCraftingRecipeExtension(CTRecipeShaped recipe) {
        this.recipe = recipe;
        CTRecipeManagerPlugin.ALL_SHAPED.add(recipe);
        this.ingredients = recipe.getIIngredients();
        this.ingredients1d = new IIngredient[9];
        for(int row = 0; row < 3; row++) {
            if(ingredients.length <= row) {
                continue;
            }
            final IIngredient[] ingredientRow = ingredients[row];
            for(int i = 0; i < 3; i++) {
                if(ingredientRow.length > i) {
                    ingredients1d[row * 3 + i] = ingredientRow[i];
                }
            }
        }
    }
    
    @Override
    public void setIngredients(IIngredients ingredients) {
        //Leave them in for now for JEI matching
        final int reduce = Arrays.stream(ingredients1d)
                .filter(Objects::nonNull)
                .mapToInt(i -> i.getItems().length)
                .filter(i -> i != 0)
                .reduce(1, (left, right) -> left * right);
        
        final List<List<ItemStack>> inputs = new ArrayList<>(9);
        for(int i = 0; i < 9; i++) {
            inputs.add(new ArrayList<>());
        }
        
        final List<ItemStack> outputs = new ArrayList<>(reduce);
        
        calculateCheckedCombinations().forEach(grid -> {
            final IItemStack output = recipe.getOutput(grid);
            if(output.isEmpty()) {
                return;
            }
            
            outputs.add(output.getInternal());
            
            for(int i = 0; i < 9; i++) {
                final ItemStack internal = grid[i / 3][i % 3].getInternal();
                if(!internal.isEmpty()) {
                    inputs.get(i).add(internal);
                }
            }
        });
        
        
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
        
        //ingredients.setInputIngredients(recipe.getIngredients());
        //ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }
    
    public Stream<IItemStack[][]> calculateCheckedCombinations() {
        final NonNullList<Ingredient> ingredients = recipe.getIngredients();
        if(ingredients.isEmpty()) {
            return Stream.empty();
        }
        
        int possibleCombinations = ingredients.stream()
                .mapToInt(value -> value.getMatchingStacks().length)
                .filter(i -> i != 0)
                .reduce(1, (left, right) -> left * right);
        return IntStream.range(0, possibleCombinations).mapToObj(this::rowNo);
    }
    
    public IItemStack[][] rowNo(int i) {
        final IItemStack[][] output = new IItemStack[3][3];
        
        int index = -1;
        for(IIngredient ingredient : ingredients1d) {
            index++;
            final IItemStack[] matchingStacks = ingredient != null ? ingredient.getItems() : new IItemStack[0];
            final IItemStack matchingStack = matchingStacks.length == 0 ? MCItemStack.EMPTY.get() : matchingStacks[i % matchingStacks.length];
            output[index / 3][index % 3] = matchingStack;
            if(matchingStacks.length > 0) {
                i = i / matchingStacks.length;
            }
        }
        
        
        return output;
    }
    
    @Override
    public void drawInfo(int recipeWidth, int recipeHeight, double mouseX, double mouseY) {
        final ItemStack stack = new ItemStack(Items.PUMPKIN);
        GlStateManager.enableDepthTest();
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft minecraft = Minecraft.getInstance();
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        itemRenderer.renderItemAndEffectIntoGUI(null, stack, (int) mouseX - 16, (int) mouseY - 16);
        GlStateManager.disableBlend();
        RenderHelper.disableStandardItemLighting();
        
        
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
        recipeLayout.getItemStacks().set(ingredients);
    }
}
