package com.blamejared.jeitweaker.api;

import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.util.ResourceLocation;

public interface IngredientType<T, U> {
    
    ResourceLocation id();
    
    Class<T> jeiTweakerType();
    
    Class<U> jeiType();
    
    U toJeiType(final T t);
    
    T toJeiTweakerType(final U u);
    
    IIngredientType<U> toJeiIngredientType(final IIngredientManager manager);
    
    boolean match(final T a, final T b);
}
