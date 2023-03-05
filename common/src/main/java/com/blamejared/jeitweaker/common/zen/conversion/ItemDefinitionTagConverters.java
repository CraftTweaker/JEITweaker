package com.blamejared.jeitweaker.common.zen.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.expand.ExpandItemTag;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.jeitweaker.common.api.zen.ingredient.ZenJeiIngredient;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Handles automatic conversion between {@link KnownTag}s holding {@link Item}s and {@link ZenJeiIngredient}s.
 *
 * <p>Scriptwriters should never require using this class explicitly.</p>
 *
 * @since 4.0.0
 */
@Document("mods/JeiTweaker/API/ItemDefinitionTagConverters")
@ZenCodeType.Expansion("crafttweaker.api.tag.type.KnownTag<crafttweaker.api.item.ItemDefinition>")
@ZenRegister
public final class ItemDefinitionTagConverters {
    private ItemDefinitionTagConverters() {}
    
    /**
     * Converts the given {@link KnownTag} into an array of {@link ZenJeiIngredient}s.
     *
     * @param $this The tag to convert.
     * @return The resulting array of ingredients.
     *
     * @docParam $this <tag:items:minecraft:wooden_planks>
     *
     * @since 4.0.0
     */
    @ZenCodeType.Caster(implicit = true)
    public static ZenJeiIngredient[] asJeiIngredient(final KnownTag<Item> $this) {
        return IIngredientConverters.asJeiIngredient(ExpandItemTag.asIIngredient($this));
    }
}
