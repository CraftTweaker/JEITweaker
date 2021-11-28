package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.expansions.ExpandItemTag;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Expands {@link MCTag} when typed with {@link Item} with JEI-specific conversions.
 */
//@Document("mods/JEI/Component/ItemTagExpansions")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.item.MCItemDefinition>")
@ZenRegister
public final class ItemTagExpansions {
    
    /**
     * Converts a {@link MCTag} to an array of {@link RawJeiIngredient} for usage in recipe registration.
     *
     * @param tag The tag to convert
     * @return An array of JEI ingredients containing all items currently in the tag.
     *
     * @since 1.1.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static RawJeiIngredient[] asJeiIngredientArray(final MCTag<Item> tag) {
        
        return IIngredientExpansions.asJeiIngredientArray(ExpandItemTag.asIIngredient(tag));
    }
}
