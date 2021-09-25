package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.zen.component.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.function.Consumer;

@Document("mods/JEITweaker/Category/JeiCategory")
@ZenCodeType.Name("mods.jei.category.JeiCategory")
@ZenRegister
public interface JeiCategory {
    
    @ZenCodeType.Method
    static <T extends JeiCategory> JeiCategory create(
            final Class<T> typeToken,
            final String id,
            final String name,
            final JeiDrawable icon,
            final HackyJeiIngredientToMakeZenCodeHappy[] catalysts
    ) {
        
        return create(typeToken, id, name, icon, catalysts, ignore -> {});
    }
    
    @ZenCodeType.Method
    static <T extends JeiCategory> JeiCategory create(
            final Class<T> typeToken,
            final String id,
            final String name,
            final JeiDrawable icon,
            final HackyJeiIngredientToMakeZenCodeHappy[] catalysts,
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
    HackyJeiIngredientToMakeZenCodeHappy[] catalysts();
    
    void addRecipe(final JeiRecipe recipe);
    
    List<JeiRecipe> getTargetRecipes();
}
