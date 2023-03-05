package com.blamejared.jeitweaker.common.plugin.core;

import com.blamejared.crafttweaker.api.util.ClassUtil;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPlugin;
import com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPluginProvider;
import com.blamejared.jeitweaker.common.platform.PlatformBridge;
import com.blamejared.jeitweaker.common.registry.JeiTweakerRegistries;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public final class PluginManager {
    private static final boolean DEVELOPMENT = PlatformBridge.INSTANCE.isDevEnv();
    
    private final Logger logger;
    private final JeiTweakerRegistries registries;
    private final List<JeiTweakerPluginProvider> plugins;
    
    private PluginManager(final Logger logger, final JeiTweakerRegistries registries) {
        this.logger = logger;
        this.registries = registries;
        this.plugins = new ArrayList<>();
    }
    
    public static PluginManager of(final Logger logger, final JeiTweakerRegistries registries) {
        return new PluginManager(logger, registries);
    }
    
    public void discoverPlugins() {
        assert this.plugins.size() == 0;
        
        ClassUtil.findClassesWithAnnotation(JeiTweakerPlugin.class)
                .map(this::asPluginInstance)
                .map(it -> Pair.of(this.findName(it), LazyPlugin.of(it)))
                .map(it -> new DecoratedPlugin(it.first(), it.second()))
                .peek(it -> this.logger.info("Identified JeiTweaker plugin '" + it + "': loading scheduled"))
                .forEach(this.plugins::add);
    }
    
    public void initializePlugins() {
        this.logger.info("Running JeiTweaker plugin initialization");
        this.allPlugins(null, (plugin, nothing) -> plugin.initialize());
        this.allPlugins(JeiIngredientTypeRegistrar.of(this.registries.jeiIngredientTypeRegistry()), JeiTweakerPluginProvider::registerIngredientTypes);
        this.logger.info("JeiTweaker plugin initialization completed successfully");
    }
    
    private Class<? extends JeiTweakerPluginProvider> asPluginInstance(final Class<?> clazz) {
        if (JeiTweakerPluginProvider.class.isAssignableFrom(clazz)) {
            return GenericUtil.uncheck(clazz);
        }
        throw new IllegalStateException("Invalid plugin class " + clazz.getName() + ": does not extend JeiTweakerPluginProvider");
    }
    
    private ResourceLocation findName(final Class<? extends JeiTweakerPluginProvider> clazz) {
        final String s = clazz.getAnnotation(JeiTweakerPlugin.class).value();
        final ResourceLocation rl = new ResourceLocation(s);
        if ("minecraft".equals(rl.getNamespace())) {
            throw new IllegalStateException("Invalid plugin class " + clazz.getName() + ": missing namespace in ID");
        }
        return rl;
    }
    
    private <T> void allPlugins(final T object, final BiConsumer<JeiTweakerPluginProvider, T> consumer) {
        this.plugins.forEach(it -> {
            try {
                consumer.accept(it, object);
            } catch (final Throwable e) {
                this.logger.error("An error occurred while initializing plugin " + it, e);
                if (DEVELOPMENT) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
