package com.blamejared.jeitweaker.zen;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
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
import com.blamejared.jeitweaker.zen.component.IFluidStackExpansions;
import com.blamejared.jeitweaker.zen.component.IItemStackExpansions;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

/**
 * Manages all interactions between JeiTweaker plugins and JEI itself.
 *
 * @since 1.0.0
 */
@Document("mods/JEITweaker/API/JEI")
@ZenCodeType.Name("mods.jei.JEI")
@ZenRegister
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
     * @param ingredient The ingredient to which the description should be added.
     * @param description A series of {@link MCTextComponent}s identifying the description.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Method
    public static void addDescription(final RawJeiIngredient ingredient, final MCTextComponent... description) {
        
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
     * Adds the given {@link IItemStack} to the list of ingredients that JEI should display.
     *
     * @deprecated Use {@link #addIngredient(RawJeiIngredient)} instead. If you are writing a script, you can use the
     * same syntax as conversion between {@link IItemStack} and {@link RawJeiIngredient} is automatic: see
     * {@link IItemStackExpansions#asJeiIngredient(IItemStack)}.
     *
     * @param stack The stack to be added to JEI.
     *
     * @since 1.0.0
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addItem(final IItemStack stack) {
        
        addIngredient(IItemStackExpansions.asJeiIngredient(stack));
    }
    
    /**
     * Adds an information box for the given {@link IItemStack}.
     *
     * <p>The information box is identified by an information symbol that appears as one of the categories available
     * when querying an ingredient.</p>
     *
     * @deprecated Use {@link #addDescription(RawJeiIngredient, MCTextComponent...)} instead. If you are writing a
     * script, conversion between {@link IItemStack} and {@link RawJeiIngredient} is automatic: see
     * {@link IItemStackExpansions#asJeiIngredient(IItemStack)}. Descriptions need to be converted manually instead:
     * refer to {@link MCTextComponent#createTranslationTextComponent(String)}.
     *
     * @param stack The stack to which the information box should be added to.
     * @param description A series of strings that represent the description. These information boxes are treated as
     *                    {@linkplain MCTextComponent#createTranslationTextComponent(String) translation components},
     *                    meaning that they will be automatically treated as entries available in a language file.
     *
     * @since 1.0.0
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addInfo(final IItemStack stack, final String... description) {
        
        addDescription(IItemStackExpansions.asJeiIngredient(stack), Arrays.stream(description).map(MCTextComponent::createTranslationTextComponent).toArray(MCTextComponent[]::new));
    }
    
    /**
     * Adds an information box for the given {@link IFluidStack}.
     *
     * <p>The information box is identified by an information symbol that appears as one of the categories available
     * when querying an ingredient.</p>
     *
     * @deprecated Use {@link #addDescription(RawJeiIngredient, MCTextComponent...)} instead. If you are writing a
     * script, conversion between {@link IFluidStack} and {@link RawJeiIngredient} is automatic: see
     * {@link IFluidStackExpansions#asJeiIngredient(IFluidStack)}. Descriptions need to be converted manually instead:
     * refer to {@link MCTextComponent#createTranslationTextComponent(String)}.
     *
     * @param stack The stack to which the information box should be added to.
     * @param description A series of strings that represent the description. These information boxes are treated as
     *                    {@linkplain MCTextComponent#createTranslationTextComponent(String) translation components},
     *                    meaning that they will be automatically treated as entries available in a language file.
     *
     * @since 1.0.0
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addInfo(final IFluidStack stack, final String... description) {
        
        addDescription(IFluidStackExpansions.asJeiIngredient(stack), Arrays.stream(description).map(MCTextComponent::createTranslationTextComponent).toArray(MCTextComponent[]::new));
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
     * {@link com.blamejared.crafttweaker.api.managers.IRecipeManager.RecipeFilter}.</p>
     *
     * @param modId The mod ID of the ingredients that should be hidden.
     * @param exclude An optional exclusion filter.
     *
     * @since 1.0.0
     */
    @ZenCodeType.Method
    public static void hideMod(final String modId, @ZenCodeType.Optional("(name as string) => {return false;}") final IRecipeManager.RecipeFilter exclude) {
        
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
    
    /**
     * Hides the given {@link IItemStack} from within JEI.
     *
     * @deprecated Use {@link #hideIngredient(RawJeiIngredient)} instead. If you are writing a script, the conversion
     * between {@link IItemStack} and {@link RawJeiIngredient} is automatic: see
     * {@link IItemStackExpansions#asJeiIngredient(IItemStack)}.
     *
     * @param stack The stack that should be hidden from JEI.
     *
     * @since 1.0.0
     */
    @Deprecated
    @ZenCodeType.Method
    public static void hideItem(final IItemStack stack) {
        
        hideIngredient(IItemStackExpansions.asJeiIngredient(stack));
    }
    
    /**
     * Hides the given {@link IFluidStack} from within JEI.
     *
     * @deprecated Use {@link #hideIngredient(RawJeiIngredient)} instead. If you are writing a script, the conversion
     * between {@link IFluidStack} and {@link RawJeiIngredient} is automatic: see
     * {@link IFluidStackExpansions#asJeiIngredient(IFluidStack)}.
     *
     * @param stack The stack that should be hidden from JEI.
     *
     * @since 1.0.0
     */
    @Deprecated
    @ZenCodeType.Method
    public static void hideFluid(final IFluidStack stack) {
    
        hideIngredient(IFluidStackExpansions.asJeiIngredient(stack));
    }
}
