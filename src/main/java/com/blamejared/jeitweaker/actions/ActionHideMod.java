package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;
import com.mojang.datafixers.util.Pair;
import net.minecraftforge.fml.LogicalSide;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class ActionHideMod implements IUndoableAction {

    private final String modId;
    private final IRecipeManager.RecipeFilter exclude;
    private List<JeiIngredient<?, ?>> collectedIngredients;

    public ActionHideMod(final String modId, final IRecipeManager.RecipeFilter exclude) {

        this.modId = modId;
        this.exclude = exclude;
    }

    @Override
    public void apply() {

        this.collectedIngredients = StateManager.INSTANCE.registrationState().ingredientEnumerators()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue().jeiEnumeration().spliterator()))
                .flatMap(entry -> StreamSupport.stream(entry.getSecond(), false).map(it -> Pair.of(entry.getFirst(), it)))
                .map(entry -> Pair.of(entry.getFirst(), entry.getFirst().toJeiTweakerType(this.uncheck(entry.getSecond()))))
                .filter(entry -> this.modId.equals(entry.getFirst().toIngredientIdentifier(this.uncheck(entry.getSecond())).getNamespace()))
                .filter(entry -> !this.exclude.test(entry.getFirst().toIngredientIdentifier(this.uncheck(entry.getSecond())).toString()))
                .map(entry -> JeiIngredient.of(entry.getFirst(), this.uncheck(entry.getSecond())))
                .peek(StateManager.INSTANCE.actionsState()::hide)
                .collect(Collectors.toList());
    }

    @Override
    public void undo() {

        this.collectedIngredients.forEach(StateManager.INSTANCE.actionsState()::show);
    }

    @Override
    public String describeUndo() {

        return "Undoing JEI hiding all ingredients from Mod: " + this.modId;
    }

    @Override
    public String describe() {

        return "JEI Hiding all ingredients from Mod: " + this.modId;
    }

    @Override
    public boolean shouldApplyOn(final LogicalSide side) {

        return !CraftTweakerAPI.isServer();
    }

    @SuppressWarnings("unchecked")
    private <T, U> U uncheck(final T t) {
        return (U) t;
    }
}
