package com.blamejared.jeitweaker.common.registry;

public record JeiTweakerRegistries(JeiIngredientTypeRegistry jeiIngredientTypeRegistry) {
    public JeiTweakerRegistries() {
        this(new JeiIngredientTypeRegistry());
    }
}
