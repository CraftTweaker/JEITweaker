package com.blamejared.jeitweaker.zen.recipe;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Identifies a custom recipe shown in JEI inside a specific {@link JeiCategory}.
 *
 * <p>A JEI recipe is represented in JeiTweaker as having four different components: an owning category, an array of
 * array of ingredients indicating inputs, an array of array of ingredients indicating output, and an optional consumer
 * for {@link RecipeGraphics}.</p>
 *
 * <p>The owning category identifies the {@link JeiCategory} in which the custom recipe wants to be shown in. It
 * effectively establishes a relationship of parent-child between the category and the recipe.</p>
 *
 * <p>The inputs are represented as a two-level array, where the first level identifies the slots in which ingredients
 * are placed inside the category and the second level identifies all the different ingredients that will fit into that
 * slot. As an example, consider the following array:
 * {@code [[<item:minecraft:apple>, <item:minecraft:dried_kelp>], [<item:minecraft:gold_ingot>]]}. This array represents
 * the input for a recipe with two slots. The first slot can either accept an apple or a dried kelp, and JEI will
 * automatically cycle between these two ingredients as required. The second slot, on the other hand, accepts only a
 * single gold ingot.</p>
 *
 * <p>The outputs are represented exactly the same way as inputs. This means that the outputs of a recipe can also cycle
 * if such a behavior is desired.</p>
 *
 * <p>The {@link RecipeGraphics} {@link Consumer} is instead optional and allows the recipe to draw some additional
 * elements onto the screen if desired, or to communicate with its category and provide additional information which is
 * then used to render the recipe on screen. The specific interactions vary depending on the category.</p>
 *
 * @since 1.1.0
 */
// TODO("Expose methods?")
@Document("mods/JEITweaker/API/Recipe/JeiRecipe")
@ZenCodeType.Name("mods.jei.recipe.JeiRecipe")
@ZenRegister
public final class JeiRecipe {
    
    private final JeiCategory owningCategory;
    private final RawJeiIngredient[][] inputs;
    private final RawJeiIngredient[][] outputs;
    private final Consumer<RecipeGraphics> graphics;
    
    JeiRecipe(
            final JeiCategory owningCategory,
            final RawJeiIngredient[][] inputs,
            final RawJeiIngredient[][] outputs,
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
    
    public RawJeiIngredient[][] getInputs() {
        
        return this.inputs;
    }
    
    public RawJeiIngredient[][] getOutputs() {
        
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
