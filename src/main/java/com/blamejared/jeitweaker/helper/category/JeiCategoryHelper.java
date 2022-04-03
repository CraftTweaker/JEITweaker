package com.blamejared.jeitweaker.helper.category;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.util.NameUtil;
import com.blamejared.crafttweaker.impl.registry.CraftTweakerRegistry;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.JeiDrawable;
import com.blamejared.jeitweaker.zen.component.RawJeiIngredient;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

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
            final Component name,
            final JeiDrawable icon,
            final RawJeiIngredient[] catalysts,
            final Consumer<T> configurator
    ) {
        
        final ResourceLocation checkedId = NameUtil.fromFixedName(
                id,
                (fixed, mistakes) -> CraftTweakerAPI.LOGGER.warn(
                        "Invalid category ID '{}' specified due to the following mistakes:\n{}\nThe new rename will be '{}'",
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
        
        CraftTweakerRegistry.get().getZenClassRegistry()
                .getImplementationsOf(CraftTweakerAPI.getRegistry()
                        .findLoader(CraftTweakerConstants.DEFAULT_LOADER_NAME), JeiCategory.class)
                .stream()
                .filter(type -> !Modifier.isAbstract(type.getModifiers()))
                .forEach(type -> JeiCategoryCreator.of(type).ifPresent(creator -> map.put(type, creator)));
    }
    
}
