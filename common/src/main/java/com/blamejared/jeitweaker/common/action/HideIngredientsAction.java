package com.blamejared.jeitweaker.common.action;

import com.blamejared.jeitweaker.common.api.action.JeiTweakerAction;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class HideIngredientsAction extends JeiTweakerAction {
    private final Collection<? extends JeiIngredient<?, ?>> ingredients;
    
    private HideIngredientsAction(final Collection<? extends JeiIngredient<?, ?>> ingredients) {
        this.ingredients = ingredients;
    }
    
    public static HideIngredientsAction of(final ZenJeiIngredient... ingredients) {
        Objects.requireNonNull(ingredients, "ingredients");
        final Collection<? extends JeiIngredient<?, ?>> jeiIngredients = Arrays.stream(ingredients)
                .map(JeiIngredients::toJeiIngredient)
                .toList();
        return new HideIngredientsAction(jeiIngredients);
    }
    
    @Override
    public void apply() {
        this.enqueueCommand(JeiCommand.of(JeiCommandTypes.GENERAL, this::hide));
    }
    
    @Override
    public String describe() {
        return "Hiding %s ingredients from JEI: %s".formatted(
                this.ingredients.size(),
                this.ingredients.stream().map(JeiIngredients::toCommandString).toList()
        );
    }
    
    private void hide(final IJeiRuntime runtime) {
        final IIngredientManager manager = runtime.getIngredientManager();
        this.ingredients.stream()
                .collect(Collectors.groupingBy(JeiIngredients::jeiIngredientTypeOf, Collectors.mapping(JeiIngredient::jeiContent, Collectors.toList())))
                .forEach((type, ingredient) -> this.hide(manager, type, ingredient));
    }
    
    private <J> void hide(final IIngredientManager manager, final IIngredientType<J> type, final List<J> ingredients) {
        if (ingredients.isEmpty()) {
            return; // Stupid Jei, why can't you just accept empty lists and DO NOTHING LIKE YOU SHOULD??
        }
        manager.removeIngredientsAtRuntime(type, ingredients);
    }
    
}
