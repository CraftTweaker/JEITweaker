package com.blamejared.jeitweaker.helper.category;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.impl.util.NameUtils;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public final class JeiCategoryHelper {
    
    private static final Map<Class<?>, JeiCategoryCreator<?>> CREATORS = Util.make(new HashMap<>(), JeiCategoryHelper::lookup);
    
    public static void initialize() {}
    
    public static <T extends JeiCategory> T of(
            final Class<T> typeToken,
            final String id,
            final MCTextComponent name,
            final JeiDrawable icon,
            final RawJeiIngredient[] catalysts,
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
        
        return Util.make(of(typeToken).of(checkedId, name, icon, catalysts), configurator);
    }
    
    @SuppressWarnings("unchecked")
    private static <T extends JeiCategory> JeiCategoryCreator<T> of(final Class<T> type) {
        
        return Objects.requireNonNull((JeiCategoryCreator<T>) CREATORS.get(type), () -> "Invalid category type supplied: " + type.getName());
    }
    
    private static void lookup(final Map<Class<?>, JeiCategoryCreator<?>> map) {
    
        CraftTweakerRegistry.getZenClassRegistry()
                .getImplementationsOf(JeiCategory.class)
                .stream()
                .filter(type -> !Modifier.isAbstract(type.getModifiers()))
                .forEach(type -> JeiCategoryCreator.of(type).ifPresent(creator -> map.put(type, creator)));
    }
}
