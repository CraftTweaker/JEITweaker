package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.bridge.OutputListCategoryBridge;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import com.google.common.base.Suppliers;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * Represents a recipe that has no inputs, but produces a certain set of outputs.
 *
 * <p>The amount of rows of output can be manually set. Each row has space for up to 9 outputs.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Category/OutputList")
@ZenCodeType.Name("mods.jei.category.OutputList")
@ZenRegister
public final class OutputListCategory extends SimpleJeiCategory {
    
    private final Supplier<JeiDrawable> backgroundSupplier;
    private int rows;
    
    public OutputListCategory(final ResourceLocation id, final MCTextComponent name, final JeiDrawable icon, final RawJeiIngredient... catalysts) {
        
        super(id, name, icon, catalysts);
        this.rows = 1;
        this.backgroundSupplier = Suppliers.memoize(() -> JeiDrawable.ofNinePatch(GUI_ATLAS, 92, 0, 164, 20, 162, 18 * this.rows));
    }
    
    /**
     * Sets the amount of rows that should be present for each recipe.
     *
     * @param rows The amount of rows that should be available.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Setter("rows")
    public void setRows(final int rows) {
        
        this.rows = Math.max(1, rows);
    }
    
    @Override
    public JeiDrawable background() {
        
        return this.backgroundSupplier.get();
    }
    
    @Override
    public BiPredicate<JeiRecipe, ILogger> getRecipeValidator() {
        
        final BiPredicate<JeiRecipe, ILogger> validator = (recipe, logger) -> {
            
            if (recipe.getInputs().length != 0) {
                
                logger.warning("Recipe " + recipe + " has inputs: they will be ignored in OutputList");
            }
            
            if (recipe.getOutputs().length > this.rows * 9) {
                
                logger.error(String.format(
                        "Recipe %s has %d outputs, but only %d are supported with this configuration of OutputList",
                        recipe,
                        recipe.getOutputs().length,
                        this.rows * 9
                ));
                
                return false;
            }
            
            return true;
        };
        
        return validator.and(super.getRecipeValidator());
    }
    
    @Override
    public Supplier<JeiCategoryPluginBridge> getBridgeCreator() {
        
        return () -> new OutputListCategoryBridge(() -> this.rows);
    }
    
}
