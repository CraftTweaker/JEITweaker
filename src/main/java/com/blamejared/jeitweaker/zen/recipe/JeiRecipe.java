package com.blamejared.jeitweaker.zen.recipe;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEITweaker/Recipe/JeiRecipe")
@ZenCodeType.Name("mods.jei.recipe.JeiRecipe")
@ZenRegister
public interface JeiRecipe {
    
    JeiCategory owningCategory();
    
}
