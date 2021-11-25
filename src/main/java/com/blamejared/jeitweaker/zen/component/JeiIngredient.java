package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.jeitweaker.api.IngredientType;

/**
 * Represents a JEI ingredient, which is a pair of a registered {@link IngredientType} and the actual ingredient.
 *
 * <p>The existence of this class allows JeiTweaker to work generically with ingredients, without having to special
 * case every single ingredient type in JEI.</p>
 *
 * <p>Mod developers should always use this class when interfacing with JeiTweaker ingredients.</p>
 *
 * @apiNote Due to type inference issues with ZenCode, any method that wants to expose this class to scriptwriters must
 * instead use {@link RawJeiIngredient} and then use {@link RawJeiIngredient#cast()} to obtain the actual JEI ingredient
 * instance.
 *
 * @param <T> The exposed type of the wrapped ingredient.
 * @param <U> The internal type of the wrapped ingredient.
 */
/*
 TODO("Uncomment when generic type inference has gotten better")
@Document("mods/JEI/Component/JeiIngredient")
@ZenCodeType.Name("mods.jei.component.JeiIngredient")
@ZenRegister
 */
public final class JeiIngredient<T, U> implements RawJeiIngredient {
    private final IngredientType<T, U> type;
    private final T wrapped;
    
    private JeiIngredient(final IngredientType<T, U> type, final T wrapped) {
        this.type = type;
        this.wrapped = wrapped;
    }
    
    public static <T, U> JeiIngredient<T, U> of(final IngredientType<T, U> type, final T wrapped) {
        return new JeiIngredient<>(type, wrapped);
    }
    
    public IngredientType<T, U> getType() {
        
        return this.type;
    }
    
    public T getWrapped() {
        
        return this.wrapped;
    }
    
    @Override
    public String getCommandString() {
        
        if (this.wrapped instanceof CommandStringDisplayable) {
            
            return ((CommandStringDisplayable) this.wrapped).getCommandString();
        }
        
        return "TODO: MISSING COMMAND STRING FOR " + this.wrapped;
    }
    
    @Override
    public boolean equals(final Object o) {
        
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return this.wrapped.equals(((JeiIngredient<?, ?>) o).wrapped);
    }
    
    @Override
    public int hashCode() {
        
        return this.wrapped.hashCode();
    }
    
    @Override
    public String toString() {
        
        return this.getCommandString();
    }
}
