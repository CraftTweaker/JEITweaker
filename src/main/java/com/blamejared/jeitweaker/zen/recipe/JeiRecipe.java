package com.blamejared.jeitweaker.zen.recipe;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.HackyJeiIngredientToMakeZenCodeHappy;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.function.Consumer;

// TODO("Expose methods?")
@Document("mods/JEITweaker/Recipe/JeiRecipe")
@ZenCodeType.Name("mods.jei.recipe.JeiRecipe")
@ZenRegister
public final class JeiRecipe {
    
    private final JeiCategory owningCategory;
    private final HackyJeiIngredientToMakeZenCodeHappy[][] inputs;
    private final HackyJeiIngredientToMakeZenCodeHappy[][] outputs;
    private final Consumer<RecipeGraphics> graphics;
    
    JeiRecipe(
            final JeiCategory owningCategory,
            final HackyJeiIngredientToMakeZenCodeHappy[][] inputs,
            final HackyJeiIngredientToMakeZenCodeHappy[][] outputs,
            final Consumer<RecipeGraphics> graphics
    ) {
        
        this.owningCategory = owningCategory;
        this.inputs = inputs;
        this.outputs = outputs;
        this.graphics = graphics;
    }
    
    public JeiCategory getOwningCategory() {
        
        return this.owningCategory;
    }
    
    public HackyJeiIngredientToMakeZenCodeHappy[][] getInputs() {
        
        return this.inputs;
    }
    
    public HackyJeiIngredientToMakeZenCodeHappy[][] getOutputs() {
        
        return this.outputs;
    }
    
    public void doGraphics(final RecipeGraphics graphics) {
        
        this.graphics.accept(graphics);
    }
    
    @Override
    public String toString() {
        
        return String.format(
                "JeiRecipe[category='%s',outputs=%s,inputs=%s]",
                this.owningCategory,
                Arrays.deepToString(this.outputs),
                Arrays.deepToString(this.inputs)
        );
    }
    
}
