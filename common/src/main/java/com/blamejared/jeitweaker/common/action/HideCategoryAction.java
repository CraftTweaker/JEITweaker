package com.blamejared.jeitweaker.common.action;

import com.blamejared.jeitweaker.common.JeiTweakerInitializer;
import com.blamejared.jeitweaker.common.api.action.JeiTweakerAction;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandTypes;
import com.blamejared.jeitweaker.common.util.UnintuitiveApiHelper;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public final class HideCategoryAction extends JeiTweakerAction {
    private final ResourceLocation categoryId;
    
    private HideCategoryAction(final ResourceLocation categoryId) {
        this.categoryId = categoryId;
    }
    
    public static HideCategoryAction of(final ResourceLocation categoryName) {
        Objects.requireNonNull(categoryName, "categoryName");
        return new HideCategoryAction(categoryName);
    }
    
    @Override
    public void apply() {
        this.enqueueCommand(JeiCommand.of(JeiCommandTypes.GENERAL, this::hide));
    }
    
    @Override
    public String describe() {
        return "Hiding JEI category with id '%s'".formatted(this.categoryId);
    }
    
    private void hide(final IJeiRuntime runtime) {
        final IRecipeManager manager = runtime.getRecipeManager();
        manager.createRecipeCategoryLookup()
                .includeHidden() // Hiding something that is already hidden should no-op, not fail
                .get()
                .filter(it -> this.categoryId.equals(UnintuitiveApiHelper.getRecipeCategoryId(it)))
                .findAny() // There will be only one in all cases
                .ifPresentOrElse(it -> UnintuitiveApiHelper.hideCategory(it, manager), this::warn);
    }
    
    private void warn() {
        JeiTweakerInitializer.get().jeiTweakerLogger().warn("No JEI category with ID '{}' was found for hiding: command skipped", this.categoryId);
    }
    
}
