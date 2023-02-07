package com.blamejared.jeitweaker.common.action;

import com.blamejared.jeitweaker.common.api.action.JeiTweakerAction;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IJeiRuntime;

import java.util.List;
import java.util.Objects;

public final class AddIngredientAction<J, Z> extends JeiTweakerAction {
    private final JeiIngredient<J, Z> ingredient;
    
    private AddIngredientAction(final JeiIngredient<J, Z> ingredient) {
        this.ingredient = ingredient;
    }
    
    public static <J, Z> AddIngredientAction<J, Z> of(final ZenJeiIngredient ingredient) {
        Objects.requireNonNull(ingredient, "ingredient");
        final JeiIngredient<J, Z> jeiIngredient = JeiIngredients.toJeiIngredient(ingredient);
        return new AddIngredientAction<>(jeiIngredient);
    }
    
    @Override
    public void apply() {
        this.enqueueCommand(JeiCommand.of(JeiCommandTypes.GENERAL, this::add));
    }
    
    @Override
    public String describe() {
        return "Adding ingredient %s to JEI".formatted(JeiIngredients.toCommandString(this.ingredient));
    }
    
    private void add(final IJeiRuntime runtime) {
        final IIngredientType<J> type = JeiIngredients.jeiIngredientTypeOf(this.ingredient);
        final J jeiIngredient = this.ingredient.jeiContent();
        runtime.getIngredientManager().addIngredientsAtRuntime(type, List.of(jeiIngredient));
    }
}
