package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.bridge.CatalystRequiringRecipeCategoryBridge;
import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

@Document("mods/JEI/Category/CatalystRequiringRecipe")
@ZenCodeType.Name("mods.jei.category.CatalystRequiringRecipe")
@ZenRegister
public final class CatalystRequiringRecipeCategory extends SimpleJeiCategory {
    
    private static final JeiDrawable BACKGROUND = JeiDrawable.of(GUI_ATLAS, 94, 69, 162, 44);
    private static final JeiDrawable SLOT_COVER = JeiDrawable.of(GUI_ATLAS, 154, 238, 18, 18);
    
    private static final int MAX_INPUTS = 3;
    private static final int MAX_OUTPUTS = 3;
    
    private JeiDrawable catalystDrawable;
    private int inputs;
    private int outputs;
    
    public CatalystRequiringRecipeCategory(final ResourceLocation id, final MCTextComponent name, final JeiDrawable icon, final RawJeiIngredient... catalysts) {
        
        super(id, name, icon, catalysts);
        this.inputs = 1;
        this.outputs = 1;
        this.catalystDrawable = null;
    }
    
    @ZenCodeType.Setter("catalystDrawable")
    public void setCatalystDrawable(@ZenCodeType.Nullable final JeiDrawable catalystDrawable) {
        
        this.catalystDrawable = catalystDrawable;
    }
    
    @ZenCodeType.Setter("inputs")
    public void setInputs(final int inputs) {
        
        if (inputs <= 0) {
            
            CraftTweakerAPI.logError("Unable to use a CatalystRequiringRecipe without any input");
            return;
        }
        
        if (inputs > MAX_INPUTS) {
            
            CraftTweakerAPI.logError("Unable to set inputs of a CatalystRequiringRecipe to {}: max is {}", inputs, MAX_INPUTS);
            return;
        }
        
        this.inputs = inputs;
    }
    
    @ZenCodeType.Setter("outputs")
    public void setOutputs(final int outputs) {
    
        if (outputs <= 0) {
        
            CraftTweakerAPI.logError("Unable to use a CatalystRequiringRecipe without any output");
            return;
        }
    
        if (outputs > MAX_OUTPUTS) {
        
            CraftTweakerAPI.logError("Unable to set outputs of a CatalystRequiringRecipe to {}: max is {}", outputs, MAX_OUTPUTS);
            return;
        }
    
        this.outputs = outputs;
    }
    
    @Override
    public JeiDrawable background() {
        
        return BACKGROUND;
    }
    
    @Override
    public BiPredicate<JeiRecipe, ILogger> getRecipeValidator() {
    
        final boolean hasCatalyst = this.catalystDrawable == null;
        
        final BiPredicate<JeiRecipe, ILogger> validator = (recipe, logger) -> {
        
            if (recipe.getOutputs().length > this.outputs) {
            
                logger.error("Recipe " + recipe + " has " + recipe.getOutputs().length + " outputs: expected " + this.outputs);
                return false;
            }
            
            if (recipe.getInputs().length > (this.inputs + (hasCatalyst? 1 : 0))) {
    
                logger.error("Recipe " + recipe + " has " + recipe.getInputs().length + " outputs: expected " + this.inputs);
                return false;
            }
            
            if (hasCatalyst && recipe.getInputs().length == 1) {
                
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
