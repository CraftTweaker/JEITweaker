package com.blamejared.jeitweaker.zen;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.actions.ActionAddInfo;
import com.blamejared.jeitweaker.actions.ActionAddIngredient;
import com.blamejared.jeitweaker.actions.ActionAddRecipeCategory;
import com.blamejared.jeitweaker.actions.ActionHideCategory;
import com.blamejared.jeitweaker.actions.ActionHideIngredient;
import com.blamejared.jeitweaker.actions.ActionHideMod;
import com.blamejared.jeitweaker.actions.ActionHideRecipe;
import com.blamejared.jeitweaker.actions.ActionHideRegex;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import mezz.jei.api.constants.ModIds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Predicate;

/**
 * Manages all interactions between JeiTweaker plugins and JEI itself.
 *
 * @since 1.0.0
 */
@Document("mods/JEITweaker/API/JEI")
@ZenCodeType.Name("mods.jei.JEI")
@ZenRegister(modDeps = ModIds.JEI_ID)
public final class JeiManager {
    
    /**
     * Adds the given category to the list of categories shown by JEI.
     *
     * @param category The category that should be added to JEI.
     *
     * @see JeiCategory
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static void addCategory(final JeiCategory category) {
        
        CraftTweakerAPI.apply(new ActionAddRecipeCategory(category));
    }
    
    /**
     * Adds a description for the given {@link RawJeiIngredient}.
     *
     * <p>The description is defined as the text that is shown in the info box that appears as one of the recipe
     * categories for the queried ingredient.</p>
     *
     * @param ingredient  The ingredient to which the description should be added.
     * @param description A series of {@link Component}s identifying the description.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static void addDescription(final RawJeiIngredient ingredient, final Component... description) {
        
        CraftTweakerAPI.apply(new ActionAddInfo<>(ingredient.cast(), description));
    }
    
    /**
     * Adds the given {@link RawJeiIngredient} as a custom ingredient to be shown in JEI.
     *
     * @param ingredient The ingredient that should be shown in JEI.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static void addIngredient(final RawJeiIngredient ingredient) {
        
        CraftTweakerAPI.apply(new ActionAddIngredient<>(ingredient.cast()));
    }
    
    /**
     * Hides the category identified by the given category ID from displaying within JEI.
     *
     * <p>A full list of categories is available through the {@code /ct dump jeiCategories} command.</p>
     *
     * @param category The ID of the category to hide.
     *
     * @since 1.0.0
     */
    @ZenCodeType.Method
    public static void hideCategory(final String category) {
        
        CraftTweakerAPI.apply(new ActionHideCategory(new ResourceLocation(category)));
    }
    
    /**
     * Hides the given {@link RawJeiIngredient}, so that it does not show up in JEI.
     *
     * @param ingredient The ingredient that should be hidden from JEI.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static void hideIngredient(final RawJeiIngredient ingredient) {
        
        CraftTweakerAPI.apply(new ActionHideIngredient<>(ingredient.cast()));
    }
    
    /**
     * Hides all {@link RawJeiIngredient}s of a given mod, making sure that they do not show up in JEI.
     *
     * <p>Optionally, some ingredients can be excluded based on their registry name from the hiding by making use of the
     * {@link Predicate<String>}.</p>
     *
     * <p>Note, the exclusion filter works on the full registry name, unlike recipe filters which work on the path without the modid.</p>
     *
     * @param modId   The mod ID of the ingredients that should be hidden.
     * @param exclude An optional exclusion filter.
     *
     * @since 1.0.0
     */
    @ZenCodeType.Method
    public static void hideMod(final String modId, @ZenCodeType.Optional("(name as string) => {return false;}") final Predicate<String> exclude) {
        
        CraftTweakerAPI.apply(new ActionHideMod(modId, exclude));
    }
    
    /**
     * Hides the recipe with the given name from appearing within the JEI category with the given ID.
     *
     * @param categoryId The ID of the category where the recipe to hide is located.
     * @param recipeName The name of the recipe that should be hidden from JEI.
     *
     * @since 1.0.0
     */
    @ZenCodeType.Method
    public static void hideRecipe(final String categoryId, final String recipeName) {
        
        CraftTweakerAPI.apply(new ActionHideRecipe(new ResourceLocation(categoryId), new ResourceLocation(recipeName)));
    }
    
    /**
     * Hides all {@link RawJeiIngredient}s whose name matches the given regular expression.
     *
     * @param regex The regular expression that identifies ingredient names that should be hidden from JEI.
     *
     * @since 1.0.0
     */
    @ZenCodeType.Method
    public static void hideRegex(final String regex) {
        
        CraftTweakerAPI.apply(new ActionHideRegex(regex));
    }
    
}
