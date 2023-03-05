package com.blamejared.jeitweaker.common.plugin.core;

import com.blamejared.jeitweaker.common.api.plugin.JeiIngredientTypeRegistration;
import com.blamejared.jeitweaker.common.api.plugin.JeiTweakerPluginProvider;
import com.google.common.base.Suppliers;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Supplier;

final class LazyPlugin implements JeiTweakerPluginProvider {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();
    private static final MethodType CONSTRUCTOR_TYPE = MethodType.methodType(void.class);
    private static final MethodType INVOKER_TYPE = MethodType.methodType(JeiTweakerPluginProvider.class);
    
    private final Supplier<JeiTweakerPluginProvider> plugin;
    
    private LazyPlugin(final Supplier<JeiTweakerPluginProvider> plugin) {
        this.plugin = Suppliers.memoize(plugin::get);
    }
    
    static JeiTweakerPluginProvider of(final Class<? extends JeiTweakerPluginProvider> clazz) {
        try {
            final MethodHandle constructorHandle = LOOKUP.findConstructor(clazz, CONSTRUCTOR_TYPE);
            final MethodHandle adaptedHandle = constructorHandle.asType(INVOKER_TYPE);
            return new LazyPlugin(() -> {
                try {
                    return (JeiTweakerPluginProvider) adaptedHandle.invokeExact();
                } catch (final Throwable t) {
                    throw new IllegalStateException("Unable to construct plugin instance for " + clazz.getName(), t);
                }
            });
        } catch(final NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException("Invalid plugin class " + clazz.getName() + ": missing empty constructor", e);
        }
    }
    
    @Override
    public void initialize() {
        this.plugin().initialize();
    }
    
    @Override
    public void registerIngredientTypes(final JeiIngredientTypeRegistration registration) {
        this.plugin().registerIngredientTypes(registration);
    }
    
    private JeiTweakerPluginProvider plugin() {
        return this.plugin.get();
    }
    
}
