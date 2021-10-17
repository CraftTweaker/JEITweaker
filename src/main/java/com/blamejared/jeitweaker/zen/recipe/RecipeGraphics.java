package com.blamejared.jeitweaker.zen.recipe;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEI/Recipe/JeiRecipeGraphics")
@ZenCodeType.Name("mods.jei.recipe.JeiRecipeGraphics")
@ZenRegister
public interface RecipeGraphics {
    
    @ZenCodeType.Method
    void showShapelessMarker();
    
    @ZenCodeType.Method
    void setExtraComponent(final String key, final MCTextComponent component);
    
    @ZenCodeType.Method
    void addTooltip(final String key, final MCTextComponent... lines);
    
    @ZenCodeType.Method
    void addTooltip(final int x, final int y, final int activeAreaWidth, final int activeAreaHeight, final MCTextComponent... lines);
}
