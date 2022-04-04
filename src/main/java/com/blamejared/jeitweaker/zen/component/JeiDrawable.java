package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.library.ninepatch.NinePatchDrawable;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents anything that can be drawn in a JEI recipe or category.
 *
 * <p>A drawable can be anything from an ingredient, to a blank image, to something with animations. Refer to all the
 * available factory methods for more information.</p>
 *
 * <p>All {@link RawJeiIngredient}s can automatically convert into a {@link JeiDrawable}, avoiding some boilerplate.
 * Refer to {@link JeiIngredientExpansions#asJeiDrawable(RawJeiIngredient)} and its specialized versions
 * {@link IItemStackExpansions#asJeiDrawable(IItemStack)} and {@link IFluidStackExpansions#asJeiDrawable(IFluidStack)}
 * for more information.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Component/JeiDrawable")
@ZenCodeType.Name("mods.jei.component.JeiDrawable")
@ZenRegister(modDeps = ModIds.JEI_ID)
public final class JeiDrawable {
    
    private volatile Function<IGuiHelper, IDrawable> delegate;
    private volatile IDrawable content;
    
    private JeiDrawable(final Function<IGuiHelper, IDrawable> delegate) {
        
        this.delegate = delegate;
    }
    
    /**
     * Creates a blank drawable of the given width and height.
     *
     * <p>A blank drawable draws nothing.</p>
     *
     * @param width The width of the drawable.
     * @param height The height of the drawable.
     * @return A blank {@link JeiDrawable}.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static JeiDrawable blank(final int width, final int height) {
        
        return new JeiDrawable((helper) -> helper.createBlankDrawable(width, height));
    }
    
    /**
     * Creates a drawable that draws the given {@link RawJeiIngredient}.
     *
     * @param ingredient The ingredient to draw.
     * @return An ingredient {@link JeiDrawable}.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static JeiDrawable of(final RawJeiIngredient ingredient) {
        
        return new JeiDrawable(helper -> helper.createDrawableIngredient(ingredient.cast().getType().toJeiType(ingredient.cast().getWrapped())));
    }
    
    /**
     * Creates a drawable that draws the given texture, as specified in the parameters.
     *
     * @param texture A {@link ResourceLocation} identifying the path of the texture to draw.
     * @param u The u coordinate of the texture (i.e. the top-left x coordinate of the rectangle to draw).
     * @param v The v coordinate of the texture (i.e. the top-left y coordinate of the rectangle to draw).
     * @param width The width of the texture to draw.
     * @param height The height of the texture to draw.
     * @return A texture {@link JeiDrawable}.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static JeiDrawable of(final ResourceLocation texture, final int u, final int v, final int width, final int height) {
        
        return new JeiDrawable(helper -> helper.createDrawable(texture, u, v, width, height));
    }
    
    /**
     * Creates an animated version of the given drawable that animates over the course of a set of ticks.
     *
     * <p>The original drawable is left untouched, meaning that the returned instance is a fully separate drawable.</p>
     *
     * @param delegate The drawable that should be animated.
     * @param ticks The amount of ticks over which the animation unfolds. 20 ticks is 1 second.
     * @param animation The type of animation that should be rendered.
     * @return An animated {@link JeiDrawable}.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static JeiDrawable ofAnimated(final JeiDrawable delegate, final int ticks, final JeiDrawableAnimation animation) {
        
        return new JeiDrawable(helper -> helper.createAnimatedDrawable((IDrawableStatic) delegate.getDrawable(helper), ticks, animation.direction(), animation.inverted()));
    }
    
    /**
     * Creates an animated version of a texture drawable.
     *
     * <p>Using this method is effectively equivalent to
     * {@code ofAnimated(of(texture, u, v, width, height), ticks, animation)}. It is thus merely provided as a
     * convenience to avoid having to create two drawables.</p>
     *
     * @param texture A {@link ResourceLocation} identifying the path of the texture to draw.
     * @param u The u coordinate of the texture (i.e. the top-left x coordinate of the rectangle to draw).
     * @param v The v coordinate of the texture (i.e. the top-left y coordinate of the rectangle to draw).
     * @param width The width of the texture to draw.
     * @param height The height of the texture to draw.
     * @param ticks The amount of ticks over which the animation unfolds. 20 ticks is 1 second.
     * @param animation The type of animation that should be rendered.
     * @return An animated texture {@link JeiDrawable}.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static JeiDrawable ofAnimated(final ResourceLocation texture, final int u, final int v, final int width, final int height, final int ticks, final JeiDrawableAnimation animation) {
        
        return ofAnimated(of(texture, u, v, width, height), ticks, animation);
    }
    
    /**
     * Creates a new texture drawable which is based on a nine-patch texture.
     *
     * @param texture A {@link ResourceLocation} identifying the path of the texture to draw.
     * @param u The u coordinate of the texture (i.e. the top-left x coordinate of the rectangle to draw).
     * @param v The v coordinate of the texture (i.e. the top-left y coordinate of the rectangle to draw).
     * @param width The width of the texture to draw.
     * @param height The height of the texture to draw.
     * @param expectedWidth The width of the drawable.
     * @param expectedHeight The height of the drawable.
     * @return A nine-patch texture {@link JeiDrawable}.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static JeiDrawable ofNinePatch(final ResourceLocation texture, final int u, final int v, final int width, final int height, final int expectedWidth, final int expectedHeight) {
        
        return new JeiDrawable(ignore -> NinePatchDrawable.of(texture, u, v, width, height, expectedWidth, expectedHeight));
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
