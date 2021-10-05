package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;
import java.util.function.Supplier;

@Document("mods/JEI/Component/JeiDrawable")
@ZenCodeType.Name("mods.jei.component.JeiDrawable")
@ZenRegister
public final class JeiDrawable {
    
    private volatile Function<IGuiHelper, IDrawable> delegate;
    private volatile IDrawable content;
    
    private JeiDrawable(final Function<IGuiHelper, IDrawable> delegate) {
        
        this.delegate = delegate;
    }
    
    @ZenCodeType.Method
    public static JeiDrawable blank(final int width, final int height) {
        
        return new JeiDrawable((helper) -> helper.createBlankDrawable(width, height));
    }
    
    @ZenCodeType.Method
    public static JeiDrawable of(final RawJeiIngredient ingredient) {
        
        return new JeiDrawable((helper) -> helper.createDrawableIngredient(ingredient.cast().getType().toInternal(ingredient.cast().getWrapped())));
    }
    
    @ZenCodeType.Method
    public static JeiDrawable of(final ResourceLocation texture, final int u, final int v, final int width, final int height) {
        
        return new JeiDrawable((helper) -> helper.createDrawable(texture, u, v, width, height));
    }
    
    @ZenCodeType.Method
    public static JeiDrawable ofAnimated(final JeiDrawable delegate, final int ticks, final JeiDrawableAnimation animation) {
        
        return new JeiDrawable((helper) -> helper.createAnimatedDrawable((IDrawableStatic) delegate.getDrawable(helper), ticks, animation.direction(), animation.inverted()));
    }
    
    @ZenCodeType.Method
    public static JeiDrawable ofAnimated(final ResourceLocation texture, final int u, final int v, final int width, final int height, final int ticks, final JeiDrawableAnimation animation) {
        
        return ofAnimated(of(texture, u, v, width, height), ticks, animation);
    }
    
    // Do not expose to Zen: this is here only for the benefit of JEITweaker
    public static JeiDrawable of(final Supplier<IDrawable> drawableSupplier) {
        
        return new JeiDrawable(ignore -> drawableSupplier.get());
    }
    
    public IDrawable getDrawable(final IGuiHelper helper) {
    
        if (this.content == null) {
        
            synchronized (this) {
            
                if (this.content == null) {
                
                    this.content = this.delegate.apply(helper);
                    this.delegate = null;
                }
            }
        }
    
        return this.content;
    }
}
