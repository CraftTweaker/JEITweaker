package com.blamejared.jeitweaker.zen.recipe;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Allows a {@link JeiRecipe} to request additional graphic capabilities to its parent
 * {@link com.blamejared.jeitweaker.zen.category.JeiCategory}.
 *
 * <p>Support for the various methods available depends on the category to which the recipe is being added to. Refer to
 * the category documentation for more information. Namely, categories <strong>need not</strong> support nor acknowledge
 * any of the requests specified by a recipe if they do not desire to do so.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Recipe/JeiRecipeGraphics")
@ZenCodeType.Name("mods.jei.recipe.JeiRecipeGraphics")
@ZenRegister
public interface RecipeGraphics {
    
    /**
     * Shows a shapeless marker for the current recipe, if possible.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    default void showShapelessMarker() {}
    
    /**
     * Sets the value of the extra component identified by the given key to the component specified.
     *
     * @param key The key that identifies the extra component.
     * @param component The component to set.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    default void setExtraComponent(final String key, final MCTextComponent component) {}
    
    /**
     * Sets the tooltip identified by the given key to the given set of components.
     *
     * @param key The key that identifies the tooltip.
     * @param lines The components that make up the tooltip.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    default void addTooltip(final String key, final MCTextComponent... lines) {}
    
    /**
     * Asks the category to render a tooltip with the given components as lines when the cursor is in the active area
     * indicated by the given coordinates and with the given width and height.
     *
     * @param x The x coordinate of the top-left corner of the active area rectangle.
     * @param y The y coordinate of the top-left corner of the active area rectangle.
     * @param activeAreaWidth The width of the active area rectangle.
     * @param activeAreaHeight The height of the active area rectangle.
     * @param lines The components that make up the tooltip.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    default void addTooltip(final int x, final int y, final int activeAreaWidth, final int activeAreaHeight, final MCTextComponent... lines) {}
}
