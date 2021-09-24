package com.blamejared.jeitweaker.zen;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEITweaker/JeiIngredient")
@ZenCodeType.Name("mods.jei.JeiIngredient")
@ZenRegister
// TODO("Replace all usages of this with JeiIngredient once generic inference is better")
public interface HackyJeiIngredientToMakeZenCodeHappy extends CommandStringDisplayable {
    
    @SuppressWarnings("unchecked")
    default <T extends CommandStringDisplayable, U>  JeiIngredient<T, U> cast() {
        
        return (JeiIngredient<T, U>) (Object) this; // Mixin-cast
    }
}
