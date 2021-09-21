package com.blamejared.jeitweaker.zen;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.state.JeiTweakerIngredientType;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEITweaker/JeiIngredient")
@ZenCodeType.Name("mods.jei.JeiIngredient")
@ZenRegister
public final class JeiIngredient<T extends CommandStringDisplayable, U> implements CommandStringDisplayable {
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
        
        return this.wrapped.getCommandString();
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
        
        return String.format("JeiIngredient{%s}", this.getCommandString());
    }
}
