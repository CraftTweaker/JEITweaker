package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.plugin.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.zen.component.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiPredicate;
import java.util.function.IntUnaryOperator;

@Document("mods/JEITweaker/Category/SimpleInputOutput")
@ZenCodeType.Name("mods.jei.category.SimpleInputOutput")
@ZenRegister
public final class SimpleInputOutputCategory extends SimpleJeiCategory {
    private static final class Bridge implements JeiCategoryPluginBridge {
    
        @Override
        public <G> void initializeGui(final IGuiIngredientGroup<G> group, final IntUnaryOperator coordinateFixer) {
        
            group.init(0, true, coordinateFixer.applyAsInt(1), coordinateFixer.applyAsInt(8));
            group.init(1, false, coordinateFixer.applyAsInt(61), coordinateFixer.applyAsInt(8));
        }
    
        @Override
        public int getInputSlotsAmount() {
        
            return 1;
        }
    
        @Override
        public int getOutputSlotsAmount() {
        
            return 1;
        }
    
    }
    
    private final Bridge bridge;
    
    public SimpleInputOutputCategory(final ResourceLocation id, final MCTextComponent name, final JeiDrawable icon, final HackyJeiIngredientToMakeZenCodeHappy... catalysts) {
        
        super(id, name, icon, catalysts);
        this.bridge = new Bridge();
    }
    
    @Override
    public JeiDrawable background() {
        
        return JeiDrawable.of(GUI_ATLAS, 0, 0, 82, 33);
    }
    
    @Override
    public BiPredicate<JeiRecipe, ILogger> getRecipeValidator() {
        
        final BiPredicate<JeiRecipe, ILogger> other = (recipe, logger) -> {
            
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
        
        return super.getRecipeValidator().and(other);
    }
    
    @Override
    public JeiCategoryPluginBridge getBridge() {
        
        return this.bridge;
    }
    
}
