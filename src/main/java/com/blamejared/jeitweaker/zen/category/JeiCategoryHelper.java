package com.blamejared.jeitweaker.zen.category;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.util.NameUtils;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.zen.component.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

final class JeiCategoryHelper {
    
    @FunctionalInterface
    interface JeiCategoryCreator<T extends JeiCategory> {
        
        T of(final ResourceLocation id, final MCTextComponent name, final JeiDrawable icon, final HackyJeiIngredientToMakeZenCodeHappy[] catalysts);
    }
    
    private static final Map<Class<?>, JeiCategoryCreator<?>> CREATORS = Util.make(new HashMap<>(), it -> {
        it.put(VerySimpleJustToCheck.class, VerySimpleJustToCheck::new);
    });
    
    static <T extends JeiCategory> T of(
            final Class<T> typeToken,
            final String id,
            final String name,
            final JeiDrawable icon,
            final HackyJeiIngredientToMakeZenCodeHappy[] catalysts,
            final Consumer<T> configurator
    ) {
        
        final ResourceLocation checkedId = NameUtils.fromFixedName(
                id,
                (fixed, mistakes) -> CraftTweakerAPI.logWarning(
                        "Invalid category ID '%s' specified due to the following mistakes:\n%s\nThe new rename will be '%s'",
                        id,
                        String.join("\n", mistakes),
                        fixed
                )
        );
        final MCTextComponent translatableName = MCTextComponent.createTranslationTextComponent(name);
        final JeiCategoryCreator<T> creator = creatorOf(typeToken);
        
        return Util.make(creator.of(checkedId, translatableName, icon, catalysts), configurator);
    }
    
    @SuppressWarnings("unchecked")
    private static <T extends JeiCategory> JeiCategoryCreator<T> creatorOf(final Class<T> type) {
        
        return Objects.requireNonNull((JeiCategoryCreator<T>) CREATORS.get(type), () -> "Invalid category type supplied: " + type.getName());
    }
}