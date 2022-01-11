package com.blamejared.jeitweaker.actions;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.jeitweaker.implementation.state.StateManager;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;
import com.mojang.datafixers.util.Pair;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class ActionHideRegex implements IUndoableAction {
    
    private final Pattern regex;
    private List<JeiIngredient<?, ?>> collectedIngredients;
    
    public ActionHideRegex(final String regex) {
        
        this.regex = Pattern.compile(regex);
    }
    
    @Override
    public void apply() {
        
        this.collectedIngredients = StateManager.INSTANCE.registrationState().ingredientEnumerators()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue().jeiEnumeration().spliterator()))
                .flatMap(entry -> StreamSupport.stream(entry.getSecond(), false)
                        .map(it -> Pair.of(entry.getFirst(), it)))
                .map(entry -> Pair.of(entry.getFirst(), entry.getFirst()
                        .toJeiTweakerType(this.uncheck(entry.getSecond()))))
                .filter(entry -> this.regex.matcher(entry.getFirst()
                        .toIngredientIdentifier(this.uncheck(entry.getSecond()))
                        .toString()).matches())
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
        
        return "Undoing JEI hiding all ingredients from Regex: " + this.regex;
    }
    
    @Override
    public String describe() {
        
        return "JEI Hiding all ingredients from Regex: " + this.regex;
    }
    
    @Override
    public boolean shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource source) {
        
        return Services.DISTRIBUTION.getDistributionType().isClient();
    }
    
    @SuppressWarnings("unchecked")
    private <T, U> U uncheck(final T t) {
        
        return (U) t;
    }
    
}
