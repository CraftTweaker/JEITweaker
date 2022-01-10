package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.JEITweaker;
import com.blamejared.jeitweaker.api.IngredientType;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.ZenCodeType;
import org.spongepowered.asm.logging.ILogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * Represents the basic and simplest implementation of a custom {@link JeiCategory}.
 *
 * <p><strong>For the script writers:</strong> you should never ever reference this class: use either a specific type or
 * {@link JeiCategory}.</p>
 *
 * <p><strong>For the mod developers:</strong> it is highly suggested to use this class as a base to implement a custom
 * category, as it gets rid of most of the boilerplate and also provides a constructor that already follows the
 * requirements outlined in {@link JeiCategory}.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Category/SimpleJeiCategory")
@ZenCodeType.Name("mods.jei.category.SimpleJeiCategory")
@ZenRegister
public abstract class SimpleJeiCategory implements JeiCategory {
    
    static final ResourceLocation GUI_ATLAS = new ResourceLocation(JEITweaker.MOD_ID, "textures/gui/jei/atlas.png");
    
    private final ResourceLocation id;
    private final Component name;
    private final JeiDrawable icon;
    private final RawJeiIngredient[] catalysts;
    
    private List<JeiRecipe> recipes;
    
    public SimpleJeiCategory(final ResourceLocation id, final Component name, final JeiDrawable icon, final RawJeiIngredient... catalysts) {
        
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.catalysts = catalysts;
    }
    
    @Override
    public final ResourceLocation id() {
        
        return this.id;
    }
    
    @Override
    public final Component name() {
        
        return this.name;
    }
    
    @Override
    public final JeiDrawable icon() {
        
        return this.icon;
    }
    
    @Override
    public final RawJeiIngredient[] catalysts() {
        
        return this.catalysts;
    }
    
    @Override
    public void addRecipe(final JeiRecipe recipe) {
        
        (this.recipes == null? (this.recipes = new ArrayList<>()) : this.recipes).add(recipe);
    }
    
    @Override
    public List<JeiRecipe> getTargetRecipes() {
        
        return this.recipes == null? Collections.emptyList() : this.recipes;
    }
    
    @Override
    public BiPredicate<JeiRecipe, Logger> getRecipeValidator() {
        
        return (recipe, logger) -> {
            
            this.verifyNoMixedSlotsIn(logger, recipe.getInputs(),  () -> "inputs of recipe " + recipe);
            this.verifyNoMixedSlotsIn(logger, recipe.getOutputs(), () -> "outputs of recipe " + recipe);
            return true;
        };
    }
    
    @Override
    public String toString() {
        
        return String.format("JeiCategory[id='%s',type=%s]", this.id, this.getClass().getSimpleName());
    }
    
    private void verifyNoMixedSlotsIn(final Logger logger, final RawJeiIngredient[][] slots, final Supplier<String> in) {
    
        Arrays.stream(slots).forEach(slot -> {
            
            if (slot.length > 1) { // At least two ingredients for this slot
                
                final IngredientType<?, ?> first = slot[0].cast().getType();
                final long mismatchingTypes = Arrays.stream(slot, 1, slot.length)
                        .filter(it -> it.cast().getType() != first)
                        .count();
                
                if (mismatchingTypes > 0) {
                    
                    logger.warn("Found multiple ingredients in {} of different types: this might not work", in.get());
                }
            }
        });
    }
    
}
