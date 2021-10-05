package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.expansions.ExpandItemTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

//@Document("mods/JEI/Component/ItemTagExpansions")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.item.MCItemDefinition>")
@ZenRegister
public final class ItemTagExpansions {
    
    @ZenCodeType.Caster(implicit = true)
    public static HackyJeiIngredientToMakeZenCodeHappy[] asJeiIngredientArray(final MCTag<Item> tag) {
        
        return IIngredientExpansions.asJeiIngredientArray(ExpandItemTag.asIIngredient(tag));
    }
}
