package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.JEITweaker;
import com.blamejared.jeitweaker.plugin.JeiTweakerIngredientType;
import com.blamejared.jeitweaker.zen.component.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

@Document("mods/JEITweaker/Category/SimpleJeiCategory")
@ZenCodeType.Name("mods.jei.category.SimpleJeiCategory")
@ZenRegister
public abstract class SimpleJeiCategory implements JeiCategory {
    
    static final ResourceLocation GUI_ATLAS = new ResourceLocation(JEITweaker.MOD_ID, "textures/gui/jei/atlas.png");
    
    private final ResourceLocation id;
    private final MCTextComponent name;
    private final JeiDrawable icon;
    private final HackyJeiIngredientToMakeZenCodeHappy[] catalysts;
    
    private List<JeiRecipe> recipes;
    
    public SimpleJeiCategory(final ResourceLocation id, final MCTextComponent name, final JeiDrawable icon, final HackyJeiIngredientToMakeZenCodeHappy... catalysts) {
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
    public final MCTextComponent name() {
        
        return this.name;
    }
    
    @Override
    public final JeiDrawable icon() {
        
        return this.icon;
    }
    
    @Override
    public final HackyJeiIngredientToMakeZenCodeHappy[] catalysts() {
        
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
    public BiPredicate<JeiRecipe, ILogger> getRecipeValidator() {
        
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
    
    private void verifyNoMixedSlotsIn(final ILogger logger, final HackyJeiIngredientToMakeZenCodeHappy[][] slots, final Supplier<String> in) {
    
        Arrays.stream(slots).forEach(slot -> {
            
            if (slot.length > 1) { // At least two ingredients for this slot
                
                final JeiTweakerIngredientType<?, ?> first = slot[0].cast().getType();
                final long mismatchingTypes = Arrays.stream(slot, 1, slot.length)
                        .filter(it -> it.cast().getType() != first)
                        .count();
                
                if (mismatchingTypes > 0) {
                    
                    logger.warning(String.format("Found multiple ingredients in %s of different types: this might not work", in.get()));
                }
            }
        });
    }
    
}
