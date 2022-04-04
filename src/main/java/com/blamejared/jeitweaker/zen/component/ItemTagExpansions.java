package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.expand.ExpandItemTag;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mezz.jei.api.constants.ModIds;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Expands {@link MCTag} when typed with {@link Item} with JEI-specific conversions.
 */
@Document("mods/JEITweaker/API/Component/ItemTagExpansions")
@ZenCodeType.Expansion("crafttweaker.api.tag.type.KnownTag<crafttweaker.api.item.ItemDefinition>")
@ZenRegister(modDeps = ModIds.JEI_ID)
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
    public static RawJeiIngredient[] asJeiIngredientArray(final KnownTag<Item> tag) {
        
        return IIngredientExpansions.asJeiIngredientArray(ExpandItemTag.asIIngredient(tag));
    }
}
