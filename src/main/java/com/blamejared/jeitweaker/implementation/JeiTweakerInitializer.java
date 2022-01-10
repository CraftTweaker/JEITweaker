package com.blamejared.jeitweaker.implementation;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.jeitweaker.api.JeiTweakerPlugin;
import com.blamejared.jeitweaker.api.JeiTweakerPluginProvider;
import com.blamejared.jeitweaker.helper.category.JeiCategoryHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JeiTweakerInitializer {
    
    private JeiTweakerInitializer() {}
    
    public static void initialize() {
        
        registerData(findPlugins());
        JeiCategoryHelper.initialize(); // TODO("Find a way to get rid of this")
    }
    
    private static List<JeiTweakerPluginProvider> findPlugins() {
        
        return ModList.get()
                .getAllScanData()
                .stream()
                .flatMap(JeiTweakerInitializer::findValidAnnotations)
                .map(JeiTweakerInitializer::initialize)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    private static Stream<Type> findValidAnnotations(final ModFileScanData data) {
        
        final Type annotationType = Type.getType(JeiTweakerPlugin.class);
        return data.getAnnotations()
                .stream()
                .filter(it -> annotationType.equals(it.annotationType()))
                .map(ModFileScanData.AnnotationData::clazz);
    }
    
    @SuppressWarnings("unchecked")
    private static JeiTweakerPluginProvider initialize(final Type type) {
        
        try {
            final Class<?> clazz = Class.forName(type.getClassName(), false, JeiTweakerInitializer.class.getClassLoader());
            if(!JeiTweakerPluginProvider.class.isAssignableFrom(clazz)) {
                throw new ClassCastException(clazz.getName() + " does not extend JeiTweakerPluginProvider");
            }
            final Constructor<? extends JeiTweakerPluginProvider> constructor = ((Class<? extends JeiTweakerPluginProvider>) clazz).getConstructor();
            return constructor.newInstance();
        } catch(final ReflectiveOperationException | ClassCastException e) {
            CraftTweakerAPI.LOGGER.error("Unable to initialize JeiTweaker Plugin {}", type.getClassName(), e);
            return null;
        }
    }
    
    private static void registerData(final List<JeiTweakerPluginProvider> providers) {
        
        final RegistrationManager manager = new RegistrationManager();
        
        providers.forEach(provider -> {
            provider.registerIngredientTypes(manager);
            provider.registerCoordinateFixers(manager);
            provider.registerIngredientEnumerators(manager);
        });
    }
    
}
