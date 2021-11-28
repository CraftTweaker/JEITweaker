package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.bridge.InputConsumingCategoryBridge;
import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.JeiDrawableAnimation;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * Represents a recipe that fully consumes its singular input, producing no tangible output.
 *
 * <p>The output is represented by a {@link JeiDrawable} with optional animation that acts as the representation of the
 * "immaterial" output of the recipe.</p>
 *
 * <p>This category also renders some recipe-dependent text allowing to better specify the result of the recipe (e.g.
 * specifying the exact amount of energy that gets produced or the amount of time required to fully consume a
 * particular ingredient).</p>
 *
 * <p>The category allows specifying the recipe-specific text, by setting the extra component named
 * {@code "result_text"} in a recipe graphics.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEI/Category/InputConsuming")
@ZenCodeType.Name("mods.jei.category.InputConsuming")
@ZenRegister
public final class InputConsumingCategory extends SimpleJeiCategory {
    
    private static final JeiDrawable BACKGROUND = JeiDrawable.of(GUI_ATLAS, 94, 20, 162, 49);
    private static final JeiDrawable DEFAULT_BG = JeiDrawable.of(GUI_ATLAS, 232, 232, 24, 24);
    private static final JeiDrawable DEFAULT_ANIM = JeiDrawable.ofAnimated(GUI_ATLAS, 208, 232, 24, 24, 35, JeiDrawableAnimation.SHOW_BOTTOM_TO_TOP);
    
    private Pair<JeiDrawable, JeiDrawable> output;
    private MCTextComponent baseResultText;
    
    public InputConsumingCategory(final ResourceLocation id, final MCTextComponent name, final JeiDrawable icon, final RawJeiIngredient... catalysts) {
        
        super(id, name, icon, catalysts);
        this.output = Pair.of(DEFAULT_BG, DEFAULT_ANIM);
        this.baseResultText = null;
    }
    
    /**
     * Sets a pair of {@link JeiDrawable}s that act as the output of the recipe.
     *
     * <p>The drawable representing the background is always drawn, whereas the animation is drawn only if present. For
     * this reason, a {@code null} animation drawable effectively disables the animation.</p>
     *
     * @param background The drawable to use as output background.
     * @param animation The drawable to use as output animation, or {@code null} if no output is desired.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method("setOutputDrawables")
    public void setOutputDrawables(final JeiDrawable background, @ZenCodeType.Nullable final JeiDrawable animation) {
        
        this.output = Pair.of(background, animation);
    }
    
    /**
     * Sets the base text that should appear before the recipe-specific text in the recipe category.
     *
     * @param baseExtra The text that should appear in all recipes, or {@code null} to disable.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Setter("baseResultText")
    public void setBaseResultText(@ZenCodeType.Nullable final MCTextComponent baseExtra) {
        
        this.baseResultText = baseExtra;
    }
    
    @Override
    public JeiDrawable background() {
        
        return BACKGROUND;
    }
    
    @Override
    public BiPredicate<JeiRecipe, ILogger> getRecipeValidator() {
        
        final BiPredicate<JeiRecipe, ILogger> validator = (recipe, logger) -> {
            
            if (recipe.getOutputs().length != 0) {
                
                logger.warning("Recipe " + recipe + " has outputs: they will be ignored in InputConsuming");
            }
            
            if (recipe.getInputs().length != 1) {
                
                logger.error("Recipe " + recipe + " has " + recipe.getInputs().length + " inputs: expected one");
                return false;
            }
            
            return true;
        };
        
        return validator.and(super.getRecipeValidator());
    }
    
    @Override
    public Supplier<JeiCategoryPluginBridge> getBridgeCreator() {
        
        return () -> new InputConsumingCategoryBridge(this.output, this.baseResultText);
    }
    
}
