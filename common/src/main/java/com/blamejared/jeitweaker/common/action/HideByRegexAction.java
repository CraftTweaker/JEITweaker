package com.blamejared.jeitweaker.common.action;

import com.blamejared.jeitweaker.common.api.action.JeiTweakerAction;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class HideByRegexAction extends JeiTweakerAction {
    private final Pattern regex;
    
    private HideByRegexAction(final Pattern regex) {
        this.regex = regex;
    }
    
    public static HideByRegexAction of(final String pattern) {
        return new HideByRegexAction(Pattern.compile(Objects.requireNonNull(pattern, "pattern")));
    }
    
    @Override
    public void apply() {
        this.enqueueCommand(JeiCommand.of(JeiCommandTypes.GENERAL, this::hide));
    }
    
    @Override
    public String describe() {
        return "Hiding all ingredients matching regex '%s' from JEI".formatted(this.regex.pattern());
    }
    
    private void hide(final IJeiRuntime runtime) {
        final IIngredientManager manager = runtime.getIngredientManager();
        final Predicate<String> matcher = this.regex.asMatchPredicate();
        
        manager.getRegisteredIngredientTypes().stream()
                .flatMap(type -> this.ingredientsOfType(manager, type))
                .filter(ingredient -> matcher.test(JeiIngredients.toRegistryName(ingredient).toString()))
                .collect(Collectors.groupingBy(JeiIngredients::jeiIngredientTypeOf, Collectors.mapping(JeiIngredient::jeiContent, Collectors.toList())))
                .forEach((type, ingredient) -> this.doHide(manager, type, ingredient));
    }
    
    private <J, Z> Stream<JeiIngredient<J, Z>> ingredientsOfType(final IIngredientManager manager, final IIngredientType<J> ingredientType) {
        return Stream.ofNullable(JeiIngredientTypes.<J, Z>fromJeiTypeOrNull(ingredientType))
                .flatMap(type -> manager.getAllIngredients(ingredientType).stream().map(it -> JeiIngredient.ofJei(type, it)));
    }
    
    private <J> void doHide(final IIngredientManager manager, final IIngredientType<J> type, final List<J> ingredients) {
        if (ingredients.isEmpty()) {
            return; // Stupid Jei, why can't you just accept empty lists and DO NOTHING LIKE YOU SHOULD??
        }
        manager.removeIngredientsAtRuntime(type, ingredients);
    }
}
