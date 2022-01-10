package com.blamejared.jeitweaker.helper.category;

import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

@FunctionalInterface
public interface JeiCategoryCreator<T extends JeiCategory> {
    
    static <T extends JeiCategory> Optional<JeiCategoryCreator<T>> of(final Class<T> typeToken) {
        
        return JeiCategoryCreatorGenerator.INSTANCE.generate(typeToken);
    }
    
    T of(final ResourceLocation id, final Component name, final JeiDrawable icon, final RawJeiIngredient[] catalysts);
}
