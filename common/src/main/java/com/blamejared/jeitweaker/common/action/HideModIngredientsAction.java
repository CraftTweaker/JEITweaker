package com.blamejared.jeitweaker.common.action;

import com.blamejared.jeitweaker.common.api.action.JeiTweakerAction;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import com.blamejared.jeitweaker.common.api.command.JeiCommandTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredient;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientTypes;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredients;
import it.unimi.dsi.fastutil.Pair;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class HideModIngredientsAction extends JeiTweakerAction {
    private final String mod;
    private final Predicate<String> inclusionFilter;
    
    private HideModIngredientsAction(final String mod, final Predicate<String> inclusionFilter) {
        this.mod = mod;
        this.inclusionFilter = inclusionFilter;
    }
    
    public static HideModIngredientsAction of(final String mod, final Predicate<String> exclusionFilter) {
        return new HideModIngredientsAction(
                Objects.requireNonNull(mod, "mod"),
                Objects.requireNonNull(exclusionFilter, "exclusionFilter").negate()
        );
    }
    
    @Override
    public void apply() {
        this.enqueueCommand(JeiCommand.of(JeiCommandTypes.GENERAL, this::hide));
    }
    
    @Override
    public String describe() {
        return "Hiding all ingredients from mod %s from JEI".formatted(this.mod);
    }
    
    private void hide(final IJeiRuntime runtime) {
        final IIngredientManager manager = runtime.getIngredientManager();
        final Predicate<ResourceLocation> mergedFilter = it -> this.mod.equals(it.getNamespace()) && this.inclusionFilter.test(it.toString());
        
        manager.getRegisteredIngredientTypes().stream()
                .flatMap(type -> this.ingredientsOfType(manager, type))
                .map(ingredient -> Pair.of(JeiIngredients.toRegistryName(ingredient), ingredient))
                .filter(it -> mergedFilter.test(it.first()))
                .map(Pair::value)
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
