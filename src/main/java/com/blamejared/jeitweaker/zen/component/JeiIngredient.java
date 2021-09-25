package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.jeitweaker.plugin.JeiTweakerIngredientType;

/*
 TODO("Uncomment when generic type inference has gotten better")
@Document("mods/JEITweaker/Component/JeiIngredient")
@ZenCodeType.Name("mods.jei.component.JeiIngredient")
@ZenRegister
 */
public final class JeiIngredient<T, U> implements HackyJeiIngredientToMakeZenCodeHappy {
    private final JeiTweakerIngredientType<T, U> type;
    private final T wrapped;
    
    JeiIngredient(final JeiTweakerIngredientType<T, U> type, final T wrapped) {
        
        this.type = type;
        this.wrapped = wrapped;
    }
    
    public JeiTweakerIngredientType<T, U> getType() {
        
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
