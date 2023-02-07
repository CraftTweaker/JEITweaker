package com.blamejared.jeitweaker.common.plugin.builtin;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.plugin.JeiIngredientTypeRegistration;
import com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPlugin;
import com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPluginProvider;
import mezz.jei.api.constants.VanillaTypes;
import net.minecraft.world.item.ItemStack;

@JeiTweakerPlugin(JeiTweakerConstants.MOD_ID + ":common")
public final class CommonJeiTweakerPlugin implements JeiTweakerPluginProvider {
    
    @Override
    public void registerIngredientTypes(final JeiIngredientTypeRegistration registration) {
        registration.registerIngredientType(
                JeiIngredientType.of(JeiTweakerConstants.rl("item_stack"), ItemStack.class, IItemStack.class),
                new ItemStackConverters(),
                VanillaTypes.ITEM_STACK
        );
    }
    
}
