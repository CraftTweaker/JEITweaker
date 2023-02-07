package com.blamejared.jeitweaker.common.plugin.core;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.blamejared.jeitweaker.common.api.plugin.JeiIngredientTypeRegistration;
import com.blamejared.jeitweaker.common.registry.JeiIngredientTypeRegistry;
import mezz.jei.api.ingredients.IIngredientType;

final class JeiIngredientTypeRegistrar implements JeiIngredientTypeRegistration {
    private final JeiIngredientTypeRegistry registry;
    
    private JeiIngredientTypeRegistrar(final JeiIngredientTypeRegistry registry) {
        this.registry = registry;
    }
    
    static JeiIngredientTypeRegistrar of(final JeiIngredientTypeRegistry registry) {
        return new JeiIngredientTypeRegistrar(registry);
    }
    
    @Override
    public <J, Z> void registerIngredientType(final JeiIngredientType<J, Z> type, final JeiIngredientConverter<J, Z> converter, final IIngredientType<J> jeiType) {
        this.registry.register(type, converter, jeiType);
    }
    
}
