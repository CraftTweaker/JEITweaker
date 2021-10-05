package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

//@Document("mods/JEI/Component/JeiIngredientExpansions")
@ZenCodeType.Expansion("mods.jei.component.JeiIngredient")
@ZenRegister
public final class JeiIngredientExpansions {
    
    @ZenCodeType.Caster(implicit = true)
    public static JeiDrawable asJeiDrawable(final HackyJeiIngredientToMakeZenCodeHappy ingredient) {
        
        return JeiDrawable.of(ingredient);
    }
}
