package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import mezz.jei.api.constants.ModIds;
import net.minecraft.network.chat.Component;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.bridge.SimpleInputOutputCategoryBridge;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * Represents a simple 1 input per 1 output recipe, which is the most common usage of custom categories.
 *
 * <p>Due to its pure simplicity, no configuration is available for this recipe.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Category/SimpleInputOutput")
@ZenCodeType.Name("mods.jei.category.SimpleInputOutput")
@ZenRegister(modDeps = ModIds.JEI_ID)
public final class SimpleInputOutputCategory extends SimpleJeiCategory {
    
    private final JeiDrawable background;
    
    public SimpleInputOutputCategory(final ResourceLocation id, final Component name, final JeiDrawable icon, final RawJeiIngredient... catalysts) {
        
        super(id, name, icon, catalysts);
        this.background = JeiDrawable.of(GUI_ATLAS, 0, 0, 82, 33);
    }
    
    @Override
    public JeiDrawable background() {
        
        return this.background;
    }
    
    @Override
    public BiPredicate<JeiRecipe, Logger> getRecipeValidator() {
        
        final BiPredicate<JeiRecipe, Logger> other = (recipe, logger) -> {
            
            if (recipe.getInputs().length != 1) {
                
                logger.error("Recipe " + recipe + " has more than one input: this is not supported for SimpleInputOutput");
                return false;
            }
    
            if (recipe.getOutputs().length != 1) {
        
                logger.error("Recipe " + recipe + " has more than one output: this is not supported for SimpleInputOutput");
                return false;
            }
            
            return true;
        };
        
        return other.and(super.getRecipeValidator());
    }
    
    @Override
    public Supplier<JeiCategoryPluginBridge> getBridgeCreator() {
        
        return SimpleInputOutputCategoryBridge::new;
    }
    
}
