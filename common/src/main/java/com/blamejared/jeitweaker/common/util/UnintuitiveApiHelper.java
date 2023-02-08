package com.blamejared.jeitweaker.common.util;

import com.google.common.collect.Sets;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class UnintuitiveApiHelper {
    private UnintuitiveApiHelper() {}
    
    public static Set<IRecipeCategory<?>> getHiddenRecipeCategories(final IRecipeManager manager) {
        // For some reason, the JEI API only has functions to get all recipe categories or all visible recipe
        // categories, but no way to query only the hidden ones, which would be helpful. Therefore, we have to
        // essentially perform the difference ourselves, which is kinda annoying.
        final Set<IRecipeCategory<?>> all = manager.createRecipeCategoryLookup().includeHidden().get().collect(Collectors.toSet());
        final Set<IRecipeCategory<?>> visible = manager.createRecipeCategoryLookup().get().collect(Collectors.toSet());
        return Sets.difference(all, visible);
    }
    
    public static <T> ResourceLocation getRecipeCategoryId(final IRecipeCategory<T> category) {
        // In the JEI API, every recipe category is uniquely identified by the recipe type, meaning that for every
        // recipe type there can only be at most one recipe category. This is very unintuitive, as this means we have to
        // essentially hide or show entire recipe types instead of just the category. There is a 1:1 mapping, but it is
        // very not intuitive in terms of vocabulary used.
        return category.getRecipeType().getUid();
    }
    
    public static <T> void hideCategory(final IRecipeCategory<T> category, final IRecipeManager manager) {
        // For the same reason as #getRecipeCategoryId, when you hide, you hide an entire recipe type, not the category.
        // Same confusion ensues.
        manager.hideRecipeCategory(category.getRecipeType());
    }
    
    public static <T> void hideRecipeWithinCategory(final IRecipeCategory<T> category, final T recipe,final IRecipeManager manager) {
        // For the same reason as #getRecipeCategoryId, when you want to hide a recipe from a category, you actually
        // need the recipe type, so the same confusion ensues.
        manager.hideRecipes(category.getRecipeType(), List.of(recipe));
    }
}
