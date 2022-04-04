package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.bridge.CatalystRequiringRecipeCategoryBridge;
import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import mezz.jei.api.constants.ModIds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.ZenCodeType;
import org.spongepowered.asm.logging.ILogger;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * Identifies a recipe that supports up to 3 inputs and 3 outputs and specifies a recipe-specific catalyst.
 *
 * <p>The recipe-specific catalyst can be either a {@link JeiDrawable} or an actual JEI ingredient. In the latter case,
 * the catalyst is specified as one additional recipe input.</p>
 *
 * <p>By default, this category is initialized with 1 input, 1 output, and no catalyst drawable.</p>
 *
 * <p>A recipe can also specify a custom tooltip that appears when hovering over the arrow by adding a tooltip with the
 * ID {@code "process_detail"}.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Category/CatalystRequiringRecipe")
@ZenCodeType.Name("mods.jei.category.CatalystRequiringRecipe")
@ZenRegister(modDeps = ModIds.JEI_ID)
public final class CatalystRequiringRecipeCategory extends SimpleJeiCategory {
    
    private static final JeiDrawable BACKGROUND = JeiDrawable.of(GUI_ATLAS, 94, 69, 162, 44);
    private static final JeiDrawable SLOT_COVER = JeiDrawable.of(GUI_ATLAS, 154, 238, 18, 18);
    
    private static final int MAX_INPUTS = 3;
    private static final int MAX_OUTPUTS = 3;
    
    private JeiDrawable catalystDrawable;
    private int inputs;
    private int outputs;
    
    public CatalystRequiringRecipeCategory(final ResourceLocation id, final Component name, final JeiDrawable icon, final RawJeiIngredient... catalysts) {
        
        super(id, name, icon, catalysts);
        this.inputs = 1;
        this.outputs = 1;
        this.catalystDrawable = null;
    }
    
    /**
     * Sets the {@link JeiDrawable} that will be used as a catalyst.
     *
     * <p>If the given drawable is {@code null}, then the last of the input parameters will be treated as catalyst.</p>
     *
     * @param catalystDrawable The drawable for the catalyst, or {@code null}.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Setter("catalystDrawable")
    public void setCatalystDrawable(@ZenCodeType.Nullable final JeiDrawable catalystDrawable) {
        
        this.catalystDrawable = catalystDrawable;
    }
    
    /**
     * Sets the amount of inputs that this recipe category allows.
     *
     * @param inputs The amount of inputs: it must be between 1 and 3 inclusive.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Setter("inputs")
    public void setInputs(final int inputs) {
        
        if(inputs <= 0) {
            
            CraftTweakerAPI.LOGGER.error("Unable to use a CatalystRequiringRecipe without any input");
            return;
        }
        
        if(inputs > MAX_INPUTS) {
            
            CraftTweakerAPI.LOGGER.error("Unable to set inputs of a CatalystRequiringRecipe to {}: max is {}", inputs, MAX_INPUTS);
            return;
        }
        
        this.inputs = inputs;
    }
    
    /**
     * Sets the amount of outputs that this recipe category allows.
     *
     * @param outputs The amount of outputs: it must be between 1 and 3 inclusive.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Setter("outputs")
    public void setOutputs(final int outputs) {
        
        if(outputs <= 0) {
            
            CraftTweakerAPI.LOGGER.error("Unable to use a CatalystRequiringRecipe without any output");
            return;
        }
        
        if(outputs > MAX_OUTPUTS) {
            
            CraftTweakerAPI.LOGGER.error("Unable to set outputs of a CatalystRequiringRecipe to {}: max is {}", outputs, MAX_OUTPUTS);
            return;
        }
        
        this.outputs = outputs;
    }
    
    @Override
    public JeiDrawable background() {
        
        return BACKGROUND;
    }
    
    @Override
    public BiPredicate<JeiRecipe, Logger> getRecipeValidator() {
        
        final boolean hasCatalyst = this.catalystDrawable == null;
        
        final BiPredicate<JeiRecipe, Logger> validator = (recipe, logger) -> {
            
            if(recipe.getOutputs().length > this.outputs) {
                
                logger.error("Recipe " + recipe + " has " + recipe.getOutputs().length + " outputs: expected " + this.outputs);
                return false;
            }
            
            if(recipe.getInputs().length > (this.inputs + (hasCatalyst ? 1 : 0))) {
                
                logger.error("Recipe " + recipe + " has " + recipe.getInputs().length + " outputs: expected " + this.inputs);
                return false;
            }
            
            if(hasCatalyst && recipe.getInputs().length == 1) {
                
                logger.error("Recipe " + recipe + " specifies only a catalyst and no inputs");
                return false;
            }
            
            return true;
        };
        
        return validator.and(super.getRecipeValidator());
    }
    
    @Override
    public Supplier<JeiCategoryPluginBridge> getBridgeCreator() {
        
        return () -> new CatalystRequiringRecipeCategoryBridge(SLOT_COVER, this.catalystDrawable, this.inputs, this.outputs, MAX_INPUTS, MAX_OUTPUTS);
    }
    
}
