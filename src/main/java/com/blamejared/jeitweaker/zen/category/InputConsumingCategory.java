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

@Document("mods/JEI/Category/InputConsuming")
@ZenCodeType.Name("mods.jei.category.InputConsuming")
@ZenRegister
public final class InputConsumingCategory extends SimpleJeiCategory {
    
    private static final JeiDrawable BACKGROUND = JeiDrawable.of(GUI_ATLAS, 94, 20, 162, 49);
    private static final JeiDrawable DEFAULT_BG = JeiDrawable.of(GUI_ATLAS, 232, 232, 24, 24);
    private static final JeiDrawable DEFAULT_ANIM = JeiDrawable.ofAnimated(GUI_ATLAS, 208, 232, 24, 24, 35, JeiDrawableAnimation.SHOW_BOTTOM_TO_TOP);
    
    private Pair<JeiDrawable, JeiDrawable> output;
    private MCTextComponent baseExtra;
    
    public InputConsumingCategory(final ResourceLocation id, final MCTextComponent name, final JeiDrawable icon, final RawJeiIngredient... catalysts) {
        
        super(id, name, icon, catalysts);
        this.output = Pair.of(DEFAULT_BG, DEFAULT_ANIM);
        this.baseExtra = null;
    }
    
    @ZenCodeType.Method("setOutputDrawables")
    public void setOutput(final JeiDrawable background, @ZenCodeType.Nullable final JeiDrawable animation) {
        
        this.output = Pair.of(background, animation);
    }
    
    @ZenCodeType.Setter("baseResultText")
    public void setBaseExtra(@ZenCodeType.Nullable final MCTextComponent baseExtra) {
        
        this.baseExtra = baseExtra;
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
        
        return () -> new InputConsumingCategoryBridge(this.output, this.baseExtra);
    }
    
}
