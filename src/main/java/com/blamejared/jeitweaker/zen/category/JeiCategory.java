package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge;
import com.blamejared.jeitweaker.helper.category.JeiCategoryHelper;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Document("mods/JEI/Category/JeiCategory")
@ZenCodeType.Name("mods.jei.category.JeiCategory")
@ZenRegister
public interface JeiCategory {
    
    @ZenCodeType.Method
    static <T extends JeiCategory> JeiCategory create(
            final Class<T> typeToken,
            final String id,
            final MCTextComponent name,
            final JeiDrawable icon,
            final RawJeiIngredient[] catalysts
    ) {
        
        return create(typeToken, id, name, icon, catalysts, ignore -> {});
    }
    
    @ZenCodeType.Method
    static <T extends JeiCategory> JeiCategory create(
            final Class<T> typeToken,
            final String id,
            final MCTextComponent name,
            final JeiDrawable icon,
            final RawJeiIngredient[] catalysts,
            final Consumer<T> configurator
    ) {
        
        return JeiCategoryHelper.of(typeToken, id, name, icon, catalysts, configurator);
    }
    
    @ZenCodeType.Getter("id")
    ResourceLocation id();
    
    @ZenCodeType.Getter("name")
    MCTextComponent name();
    
    @ZenCodeType.Getter("icon")
    JeiDrawable icon();
    
    @ZenCodeType.Getter("background")
    JeiDrawable background();
    
    @ZenCodeType.Getter("catalysts")
    RawJeiIngredient[] catalysts();
    
    void addRecipe(final JeiRecipe recipe);
    
    List<JeiRecipe> getTargetRecipes();
    
    default BiPredicate<JeiRecipe, ILogger> getRecipeValidator() {
        
        return (recipe, logger) -> true;
    }
    
    Supplier<JeiCategoryPluginBridge> getBridgeCreator();
}
