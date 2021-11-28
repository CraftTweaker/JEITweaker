package com.blamejared.jeitweaker.plugin;

import com.blamejared.jeitweaker.api.BuiltinIngredientTypes;
import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.api.CoordinateFixerRegistration;
import com.blamejared.jeitweaker.api.IngredientEnumerator;
import com.blamejared.jeitweaker.api.IngredientEnumeratorRegistration;
import com.blamejared.jeitweaker.api.IngredientTypeRegistration;
import com.blamejared.jeitweaker.api.JeiTweakerPlugin;
import com.blamejared.jeitweaker.api.JeiTweakerPluginProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@JeiTweakerPlugin
@SuppressWarnings("unused")
public class DefaultJeiTweakerPluginProvider implements JeiTweakerPluginProvider {
    
    @Override
    public void registerIngredientTypes(final IngredientTypeRegistration registration) {
        
        BuiltinIngredientTypes.ITEM.registerTo(registration);
        BuiltinIngredientTypes.FLUID.registerTo(registration);
    }
    
    @Override
    public void registerCoordinateFixers(final CoordinateFixerRegistration registration) {
    
        registration.registerFixer(BuiltinIngredientTypes.ITEM.get(), CoordinateFixer.of(it -> it - 1));
    }
    
    @Override
    public void registerIngredientEnumerators(final IngredientEnumeratorRegistration registration) {
        
        registration.registerEnumerator(
                BuiltinIngredientTypes.ITEM.get(),
                IngredientEnumerator.ofJei(BuiltinIngredientTypes.ITEM.get(), this.convert(ForgeRegistries.ITEMS.getValues(), ItemStack::new))
        );
        registration.registerEnumerator(
                BuiltinIngredientTypes.FLUID.get(),
                IngredientEnumerator.ofJei(BuiltinIngredientTypes.FLUID.get(), this.convert(ForgeRegistries.FLUIDS.getValues(), it -> new FluidStack(it, FluidAttributes.BUCKET_VOLUME)))
        );
    }
    
    private <R, V> Collection<R> convert(final Collection<V> registry, final Function<V, R> converter) {
        
        return registry.stream().map(converter).collect(Collectors.toList());
    }
}
