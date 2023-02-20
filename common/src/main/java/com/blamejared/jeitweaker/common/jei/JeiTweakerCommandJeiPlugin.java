package com.blamejared.jeitweaker.common.jei;

import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.blamejared.jeitweaker.common.util.JeiCategoriesState;
import com.blamejared.jeitweaker.common.util.UnintuitiveApiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JeiPlugin
public final class JeiTweakerCommandJeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = JeiTweakerConstants.rl("command");
    
    @Override
    public void onRuntimeAvailable(final IJeiRuntime jeiRuntime) {
        this.storeCategories(jeiRuntime);
    }
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    private void storeCategories(final IJeiRuntime runtime) {
        JeiCategoriesState.get().registerStatesProvider((visible, hidden) -> this.states(runtime, visible, hidden));
    }
    
    private Set<JeiCategoriesState.JeiCategoryState> states(
            final IJeiRuntime runtime,
            final JeiCategoriesState.JeiCategoryState.Creator visible,
            final JeiCategoriesState.JeiCategoryState.Creator hidden
    ) {
        final IRecipeManager recipeManager = runtime.getRecipeManager();
    
        final Set<IRecipeCategory<?>> visibleCategories = recipeManager.createRecipeCategoryLookup().get().collect(Collectors.toSet());
        final Set<IRecipeCategory<?>> hiddenCategories = UnintuitiveApiHelper.getHiddenRecipeCategories(recipeManager);
        return Stream.concat(
                this.stateStream(visibleCategories, visible),
                this.stateStream(hiddenCategories, hidden)
        ).collect(Collectors.toSet());
    }
    
    private Stream<JeiCategoriesState.JeiCategoryState> stateStream(
            final Set<IRecipeCategory<?>> categories,
            final JeiCategoriesState.JeiCategoryState.Creator constructor
    ) {
        return categories.stream().map(UnintuitiveApiHelper::getRecipeCategoryId).map(constructor);
    }
}
