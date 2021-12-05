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

/**
 * Identifies a custom recipe category shown in JEI when querying a specific ingredient.
 *
 * <p>A JEI category is identified by four major components: an id, a name, an icon, and a series of catalysts.</p>
 *
 * <p>The ID is a unique name that is used by JEI to identify the category, in the form of a {@link ResourceLocation}.
 * With JeiTweaker, you have control only on the {@code path} portion of the location, whereas the ID is fixed to the
 * one that represents the JeiTweaker mod.</p>
 *
 * <p>The name is a {@link MCTextComponent} which identifies the category. It can be considered the human-friendly
 * version of the category ID. More than one category can have the same name, although this is discouraged as a matter
 * of clarity towards the player.</p>
 *
 * <p>The icon is a {@link JeiDrawable} that is used as a graphic counterpart to the name, following the same rules. The
 * icon is rendered on the top section of the JEI user interface. An example icon could be the crafting table for the
 * {@code minecraft:crafting} category, or the furnace for the {@code minecraft:furnace} category.</p>
 *
 * <p>Last but not least, the catalysts are a series of {@link RawJeiIngredient}s that indicate where the recipe can be
 * crafted. They appear on the left side of the JEI user interface, in a recessed rectangle. An example set of catalysts
 * for the {@code minecraft:crafting} category could be all crafting tables, modded or otherwise. The set of catalysts
 * might or might not contain the icon.</p>
 *
 * <p>Moreover, each JeiTweaker category <strong>additionally</strong> specifies a background, which is used to provide
 * a default background for recipes.</p>
 *
 * <p><strong>For mod developers looking to create additional categories:</strong> each category is automatically
 * discovered and registered by JeiTweaker as long as it extends {@code JeiCategory} and is annotated with both
 * {@link ZenRegister} and {@link ZenCodeType.Name}. To be correctly registered, the class must not be {@code abstract}
 * (preferably, it should be {@code final}) and it <strong>must</strong> have a constructor with the following
 * signature: {@code public ClassName(ResourceLocation, MCTextComponent, JeiDrawable, RawJeiIngredient...)}. Any other
 * class is not going to be registered. Due to these requirements, mod developers are strongly encouraged not to
 * implement this interface directly, but rather extend {@link SimpleJeiCategory} instead.</p>
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Category/JeiCategory")
@ZenCodeType.Name("mods.jei.category.JeiCategory")
@ZenRegister
public interface JeiCategory {
    
    /**
     * Creates a new category of the specified type, with the given parameters, configuring it to defaults.
     *
     * @param typeToken The class acting as type token of the category to create.
     * @param id The ID of the category to create.
     * @param name A {@link MCTextComponent} representing the name of the category.
     * @param icon A {@link JeiDrawable} that acts as the icon for the category.
     * @param catalysts An array of {@link RawJeiIngredient} acting as catalysts for the category. It must not be empty.
     * @param <T> The type of the category to create. It must extend {@link JeiCategory}.
     * @return The newly created category.
     *
     * @since 1.1.0
     */
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
    
    /**
     * Creates a new category of the specified type, with the given parameters, and with the given function as a
     * configurator.
     *
     * @param typeToken The class acting as type token of the category to create.
     * @param id The ID of the category to create.
     * @param name A {@link MCTextComponent} representing the name of the category.
     * @param icon A {@link JeiDrawable} that acts as the icon for the category.
     * @param catalysts An array of {@link RawJeiIngredient} acting as catalysts for the category. It must not be empty.
     * @param configurator A {@link Consumer} allowing configuration of the category as it gets built.
     * @param <T> The type of the category to create. It must extend {@link JeiCategory}.
     * @return The newly created category.
     *
     * @since 1.1.0
     */
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
    
    /**
     * Gets the ID of the category.
     *
     * @return The ID of the category.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Getter("id")
    ResourceLocation id();
    
    /**
     * Gets the name of the category.
     *
     * @return The name of the category.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Getter("name")
    MCTextComponent name();
    
    /**
     * Gets the {@link JeiDrawable} that acts as the icon for this category.
     *
     * @return The icon of the category.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Getter("icon")
    JeiDrawable icon();
    
    /**
     * Gets the {@link JeiDrawable} that serves as the background for recipes of this category.
     *
     * @return The background for recipes of this category.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Getter("background")
    JeiDrawable background();
    
    /**
     * Gets the lists of catalysts currently registered to this category.
     *
     * @return The lists of catalysts currently registered to this category.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Getter("catalysts")
    RawJeiIngredient[] catalysts();
    
    /**
     * Adds the given recipe to the ones that are listed by this category.
     *
     * @param recipe The recipe to add.
     *
     * @since 1.1.0
     */
    void addRecipe(final JeiRecipe recipe);
    
    /**
     * Gets the list of recipes that have been registered to this category.
     *
     * @return The list of recipes that have been registered to this category.
     *
     * @since 1.1.0
     */
    List<JeiRecipe> getTargetRecipes();
    
    /**
     * Returns a validator for the recipes of this category.
     *
     * <p>The validator should return {@code true} only if the {@link JeiRecipe} given as the first argument follows all
     * requirements imposed by the current category. The {@link ILogger} obtained as second parameter should be used to
     * log errors or warnings regarding the state of the recipe.</p>
     *
     * @return The recipe validator.
     *
     * @since 1.1.0
     */
    default BiPredicate<JeiRecipe, ILogger> getRecipeValidator() {
        
        return (recipe, logger) -> true;
    }
    
    /**
     * Gets a supplier responsible for creating a {@link JeiCategoryPluginBridge} specific to this category.
     *
     * @return A supplier that creates the bridge.
     *
     * @see JeiCategoryPluginBridge
     * @since 1.1.0
     */
    Supplier<JeiCategoryPluginBridge> getBridgeCreator();
}
