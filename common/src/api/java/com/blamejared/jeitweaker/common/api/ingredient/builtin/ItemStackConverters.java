package com.blamejared.jeitweaker.common.api.ingredient.builtin;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

final class ItemStackConverters implements JeiIngredientConverter<ItemStack, IItemStack> {
    @Override
    public JeiIngredientCreator.Creator<ItemStack, IItemStack> toFullIngredientFromJei(final JeiIngredientCreator.FromJei creator, final ItemStack jeiType) {
        return creator.of(jeiType, ItemStack::copy);
    }
    
    @Override
    public JeiIngredientCreator.Creator<ItemStack, IItemStack> toFullIngredientFromZen(final JeiIngredientCreator.FromZen creator, final IItemStack zenType) {
        return creator.of(zenType.asImmutable());
    }
    
    @Override
    public JeiIngredientCreator.Creator<ItemStack, IItemStack> toFullIngredientFromBoth(
            final JeiIngredientCreator.FromBoth creator,
            final ItemStack jeiType,
            final IItemStack zenType
    ) {
        return creator.of(jeiType, ItemStack::copy, zenType.asImmutable());
    }
    
    @Override
    public IIngredientType<ItemStack> toJeiIngredientType() {
        return VanillaTypes.ITEM_STACK;
    }
    
    @Override
    public ItemStack toJeiFromZen(final IItemStack zenType) {
        return zenType.getInternal();
    }
    
    @Override
    public IItemStack toZenFromJei(final ItemStack jeiType) {
        return IItemStack.of(jeiType);
    }
    
    @Override
    public String toCommandStringFromZen(final IItemStack zenType) {
        return zenType.getCommandString();
    }
    
    @Override
    public String toRegistryNameFromJei(final ItemStack jeiType) {
        return Registry.ITEM.getKey(jeiType.getItem()).toString();
    }
    
}
