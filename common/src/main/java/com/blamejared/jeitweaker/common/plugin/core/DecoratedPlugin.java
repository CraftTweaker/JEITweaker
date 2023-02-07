package com.blamejared.jeitweaker.common.plugin.core;

import com.blamejared.jeitweaker.common.api.plugin.JeiIngredientTypeRegistration;
import com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPluginProvider;
import net.minecraft.resources.ResourceLocation;

record DecoratedPlugin(ResourceLocation id, JeiTweakerPluginProvider plugin) implements JeiTweakerPluginProvider {
    @Override
    public void registerIngredientTypes(final JeiIngredientTypeRegistration registration) {
        this.plugin().registerIngredientTypes(registration);
    }
    
    @Override
    public String toString() {
        return this.id().toString();
    }
    
}
