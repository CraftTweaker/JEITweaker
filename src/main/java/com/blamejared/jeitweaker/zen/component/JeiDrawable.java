package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEITweaker/Component/JeiDrawable")
@FunctionalInterface
@ZenCodeType.Name("mods.jei.component.JeiDrawable")
@ZenRegister
public interface JeiDrawable {
    @ZenCodeType.Method
    static JeiDrawable blank(final int width, final int height) {
        
        return new LazyDrawable((helper) -> helper.createBlankDrawable(width, height));
    }
    
    @ZenCodeType.Method
    static JeiDrawable of(final HackyJeiIngredientToMakeZenCodeHappy ingredient) {
        
        return new LazyDrawable((helper) -> helper.createDrawableIngredient(ingredient.cast().getType().toInternal(ingredient.cast().getWrapped())));
    }
    
    @ZenCodeType.Method
    static JeiDrawable of(final ResourceLocation texture, final int u, final int v, final int width, final int height) {
        
        return new LazyDrawable((helper) -> helper.createDrawable(texture, u, v, width, height));
    }
    
    @ZenCodeType.Method
    static JeiDrawable ofAnimated(final JeiDrawable delegate, final int ticks, final JeiDrawableAnimation animation) {
        
        return new LazyDrawable((helper) -> helper.createAnimatedDrawable((IDrawableStatic) delegate.getDrawable(helper), ticks, animation.direction(), animation.inverted()));
    }
    
    @ZenCodeType.Method
    static JeiDrawable ofAnimated(final ResourceLocation texture, final int u, final int v, final int width, final int height, final int ticks, final JeiDrawableAnimation animation) {
        
        return ofAnimated(of(texture, u, v, width, height), ticks, animation);
    }
    
    IDrawable getDrawable(final IGuiHelper helper);
}
