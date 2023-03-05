package com.blamejared.jeitweaker.common.zen;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.common.action.AddIngredientAction;
import com.blamejared.jeitweaker.common.action.AddIngredientInformationAction;
import com.blamejared.jeitweaker.common.action.HideCategoryAction;
import com.blamejared.jeitweaker.common.action.HideIngredientsAction;
import com.blamejared.jeitweaker.common.action.HideIngredientsByRegexAction;
import com.blamejared.jeitweaker.common.action.HideIngredientAction;
import com.blamejared.jeitweaker.common.action.HideModIngredientsAction;
import com.blamejared.jeitweaker.common.action.HideRecipeAction;
import com.blamejared.jeitweaker.common.api.zen.JeiTweakerZenConstants;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Predicate;

/**
 * Main class of the JEI integration.
 *
 * <p>This class is responsible for handling any and all integration possibilities with JEI.</p>
 *
 * @since 4.0.0
 */
@Document("mods/JeiTweaker/API/Jei")
@ZenCodeType.Name(JeiTweakerZenConstants.ZEN_MODULE_ROOT + ".Jei")
@ZenRegister
public final class JeiTweaker {
    
    /**
     * Adds the specified ingredient to the list of ingredients displayed by JEI.
     *
     * <p>The list of ingredients is available on the JEI ingredients panel, usually displayed on the right side of the
     * screen. Note that ingredients added can still be hidden through appropriate calls to the hiding methods.</p>
     *
     * @param ingredient The ingredient to add.
     *
     * @docParam ingredient <item:minecraft:dirt>
     *
     * @since 4.0.0
     */
    @ZenCodeType.Method
    public static void addIngredient(final ZenJeiIngredient ingredient) {
        CraftTweakerAPI.apply(AddIngredientAction.of(ingredient));
    }
    
    /**
     * Adds the given set of {@link Component}s to the information page of the specified ingredient.
     *
     * <p>The specified set of components will then be appended to the current set of information displayed within JEI.
     * It is worth noting that every invocation of this method will therefore create one or more new pages to display
     * the full set of components.</p>
     *
     * <p>Once added, information cannot be removed.</p>
     *
     * <p>The information page can be viewed by attempting to look for recipes of the specified ingredient, then
     * identifying the information category in the category list.</p>
     *
     * @param ingredient The ingredient for which the information should be added.
     * @param info A variable-length list of {@link Component}s representing the information to add.
     *
     * @docParam ingredient <item:minecraft:apple>
     * @docParam info Component.literal("One apple a day keeps the doctor at bay, yum 😋")
     *
     * @since 4.0.0
     */
    @ZenCodeType.Method
    public static void addIngredientInformation(final ZenJeiIngredient ingredient, final Component... info) {
        CraftTweakerAPI.apply(AddIngredientInformationAction.of(ingredient, info));
    }
    
    /**
     * Hides the specified category from being viewable.
     *
     * <p>The category is hidden through its identifier: a list of all known categories can be obtained by checking the
     * output of {@code /ct dump jeitweaker:jei_categories}. Hiding a category is global, meaning that it will not
     * appear any more in any category listing, be it the global one or the focused one.</p>
     *
     * <p>Once hidden, a category cannot be shown again.</p>
     *
     * @param category The category to hide.
     *
     * @docParam category <resource:minecraft:smithing>
     *
     * @since 4.0.0
     */
    @ZenCodeType.Method
    public static void hideCategory(final ResourceLocation category) {
        CraftTweakerAPI.apply(HideCategoryAction.of(category));
    }
    
    /**
     * Hides the specified ingredient from JEI.
     *
     * <p>The ingredient will be hidden not only from the JEI ingredient panel, but also in the list of recipe catalysts
     * and in recipe listings, essentially making it as if it never appeared.</p>
     *
     * <p>Note that ingredients that were added can also be hidden with this command.</p>
     *
     * <p>A side effect of ingredient hiding is that if the ingredient was the sole catalyst of a recipe category, then
     * the category will also be hidden as well. Care must be taken if this behavior is not desired.</p>
     *
     * @param ingredient The ingredient to hide.
     *
     * @docParam ingredient <item:minecraft:dried_kelp>
     *
     * @since 4.0.0
     */
    @ZenCodeType.Method
    public static void hideIngredient(final ZenJeiIngredient ingredient) {
        CraftTweakerAPI.apply(HideIngredientAction.of(ingredient));
    }
    
    /**
     * Hides the specified variable-arguments list of ingredients from JEI.
     *
     * <p>This acts not only as a convenience method accepting a list instead of a single element like
     * {@code hideIngredient}, but also as a way to use more complicated data structures such as tags or proper arrays
     * for removal.</p>
     *
     * <p>The ingredients will be hidden not only from the JEI ingredient panel, but also in the list of recipe
     * catalysts and in recipe listings, essentially making them as if they never appeared.</p>
     *
     * <p>Note that ingredients that were added can also be hidden with this command.</p>
     *
     * <p>A side effect of ingredient hiding is that if the ingredient was the sole catalyst of a recipe category, then
     * the category will also be hidden as well. Care must be taken if this behavior is not desired.</p>
     *
     * @param ingredients The ingredients to hide.
     *
     * @docParam ingredients <item:minecraft:redstone_dust> | <item:minecraft:iron_ingot>
     *
     * @since 4.0.0
     */
    @ZenCodeType.Method
    public static void hideIngredients(final ZenJeiIngredient... ingredients) {
        CraftTweakerAPI.apply(HideIngredientsAction.of(ingredients));
    }
    
    /**
     * Hides all ingredients from the specified mod, regardless of type.
     *
     * <p>The ingredients are matched according to their registry name. Namely, if the registry name of the ingredient
     * has the mod as the namespace, then it will be hidden, unless the path was excluded through the given exclusion
     * filter. In other words, if the ingredient name is {@code "mod:path"}, then the exclusion filter will get given
     * {@code "path"} as an argument. If the return value is {@code true}, then the item will <strong>not</strong> be
     * removed, otherwise it will.</p>
     *
     * <p>All ingredients will be hidden regardless of type and from everywhere, meaning not only from the JEI
     * ingredient panel, but also in the list of recipe catalysts and in recipe listings, essentially making them as if
     * they never appeared.</p>
     *
     * <p>Note that ingredients that were added can also be hidden with this command.</p>
     *
     * <p>A side effect of ingredient hiding is that if the ingredient was the sole catalyst of a recipe category, then
     * the category will also be hidden as well. Care must be taken if this behavior is not desired.</p>
     *
     * @param modId The id of the mod that should be hidden.
     * @param exclusionFilter An optional exclusion filter to avoid hiding certain elements.
     *
     * @docParam modId "minecraft"
     * @docParam exclusionFilter path => false
     *
     * @docParam modId "minecraft"
     * @docParam exclusionFilter path => path == "golden_hoe"
     *
     * @since 4.0.0
     */
    @ZenCodeType.Method
    public static void hideModIngredients(final String modId, @ZenCodeType.Optional("(path as string) => { return false; }") final Predicate<String> exclusionFilter) {
        CraftTweakerAPI.apply(HideModIngredientsAction.of(modId, exclusionFilter));
    }
    
    /**
     * Hides all ingredients whose <strong>entire</strong> registry name matches the given regex, regardless of type.
     *
     * <p>The ingredients are therefore checked according to their registry name directly, which means that if the
     * ingredient is named {@code "mod:path"}, then {@code "mod:path"} will be validated against the given regular
     * expression.</p>
     *
     * <p>The regular expression must be a valid regular expression. Websites such as
     * <a href="https://regexr.com">RegExr</a> can be used to verify the validity and the adequacy of the given regular
     * expression.</p>
     *
     * <p>All ingredients will be hidden regardless of type and from everywhere, meaning not only from the JEI
     * ingredient panel, but also in the list of recipe catalysts and in recipe listings, essentially making them as if
     * they never appeared.</p>
     *
     * <p>Note that ingredients that were added can also be hidden with this command.</p>
     *
     * <p>A side effect of ingredient hiding is that if the ingredient was the sole catalyst of a recipe category, then
     * the category will also be hidden as well. Care must be taken if this behavior is not desired.</p>
     *
     * @param pattern The regular expression pattern to be used for matching.
     *
     * @docParam pattern "[a-z]*:[a-z]*[0-9]"
     *
     * @since 4.0.0
     */
    @ZenCodeType.Method
    public static void hideIngredientsByRegex(final String pattern) {
        CraftTweakerAPI.apply(HideIngredientsByRegexAction.of(pattern));
    }
    
    /**
     * Hides the given recipe from the specified recipe category.
     *
     * <p>The category is identified through its identifier: a list of all known categories can be obtained by checking
     * the output of {@code /ct dump jeitweaker:jei_categories}. The recipe is identified through its recipe name
     * instead, which can be obtained either through {@code /ct recipes} or by hovering over the output of the recipe
     * in the JEI recipe view with advanced tooltips enabled.</p>
     *
     * <p>It is important to note that the recipe is not removed from the machine, meaning it is still able to be
     * crafted: hiding is merely a visual change in the recipe list.</p>
     *
     * <p>Attempting to hide a recipe from a category that does not contain it results in nothing being done.</p>
     *
     * <p>Once hidden, a recipe cannot be shown again.</p>
     *
     * @param category The category which contains the recipe to hide.
     * @param recipe The name of the recipe to hide.
     *
     * @docParam category <resource:minecraft:stonecutting>
     * @docParam recipe <resource:minecraft:andesite_stairs_from_andesite_stonecutting>
     *
     * @since 4.0.0
     */
    @ZenCodeType.Method
    public static void hideRecipe(final ResourceLocation category, final ResourceLocation recipe) {
        CraftTweakerAPI.apply(HideRecipeAction.of(category, recipe));
    }
}
