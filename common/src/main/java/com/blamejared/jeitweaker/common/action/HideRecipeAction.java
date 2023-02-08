package com.blamejared.jeitweaker.common.action;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.jeitweaker.common.JeiTweakerInitializer;
import com.blamejared.jeitweaker.common.api.action.JeiTweakerAction;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandTypes;
import com.blamejared.jeitweaker.common.util.UnintuitiveApiHelper;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public final class HideRecipeAction extends JeiTweakerAction {
    private final ResourceLocation categoryId;
    private final ResourceLocation recipeId;
    
    private HideRecipeAction(final ResourceLocation categoryId, final ResourceLocation recipeId) {
        this.categoryId = categoryId;
        this.recipeId = recipeId;
    }
    
    public static HideRecipeAction of(final ResourceLocation categoryId, final ResourceLocation recipeId) {
        Objects.requireNonNull(categoryId, "categoryId");
        Objects.requireNonNull(recipeId, "recipeId");
        return new HideRecipeAction(categoryId, recipeId);
    }
    
    @Override
    public void apply() {
        this.enqueueCommand(JeiCommand.of(JeiCommandTypes.GENERAL, this::hide));
    }
    
    @Override
    public String describe() {
        return "Hiding recipe '%s' from JEI category with id '%s'".formatted(this.recipeId, this.categoryId);
    }
    
    private void hide(final IJeiRuntime runtime) {
        final IRecipeManager manager = runtime.getRecipeManager();
        manager.createRecipeCategoryLookup()
                .includeHidden() // Hiding something that is already hidden should no-op, not fail
                .get()
                .filter(it -> this.categoryId.equals(UnintuitiveApiHelper.getRecipeCategoryId(it)))
                .findAny() // There will be only one in all cases
                .ifPresentOrElse(it -> this.hide(manager, it), this::warnCategory);
    }
    
    private <T> void hide(final IRecipeManager manager, final IRecipeCategory<T> category) {
        CraftTweakerAPI.getAccessibleElementsProvider()
                .recipeManager()
                .byKey(this.recipeId)
                .ifPresentOrElse(it -> this.hide(manager, category, it), this::warnRecipe);
    }
    
    private <T, U> void hide(final IRecipeManager manager, final IRecipeCategory<T> category, final U recipe) {
        try {
            // Unfortunately, this is generic hell, so we do not really have a way to verify if the recipe is contained
            // within the category. Moreover, we also do not verify that T and U are related: technically we could, but
            // in such case I'd argue it's a script error that should be fixed, so we can assume the two are related. If
            // this is not the case, JEI will throw an (undocumented) IllegalArgumentException, so we can catch and log.
            // It is worth noting that this is fine because the checks are performed regardless of whether we need them
            // or not, so we do not gain any benefit in performing the check and verifying it ourselves.
            UnintuitiveApiHelper.hideRecipeWithinCategory(category, GenericUtil.uncheck(recipe), manager);
        } catch (final IllegalArgumentException e) {
            JeiTweakerInitializer.get().jeiTweakerLogger().error(
                    () -> "Unable to hide target recipe '" + this.recipeId + "' within category '" + this.categoryId + "' due to an error; maybe the recipe is not removable?",
                    e
            );
        }
    }
    
    private void warnCategory() {
        JeiTweakerInitializer.get().jeiTweakerLogger().warn("No JEI category with ID '{}' was found for hiding: command skipped", this.categoryId);
    }
    
    private void warnRecipe() {
        JeiTweakerInitializer.get().jeiTweakerLogger().warn(
                "No recipe with ID '{}' was found for hiding: command skipped; note that this does not imply the recipe does not exist within JEI",
                this.recipeId
        );
    }
    
}
