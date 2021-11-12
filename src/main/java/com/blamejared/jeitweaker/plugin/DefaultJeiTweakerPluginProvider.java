package com.blamejared.jeitweaker.plugin;

import com.blamejared.jeitweaker.api.BuiltinIngredientTypes;
import com.blamejared.jeitweaker.api.CoordinateFixer;
import com.blamejared.jeitweaker.api.CoordinateFixerRegistration;
import com.blamejared.jeitweaker.api.IngredientTypeRegistration;
import com.blamejared.jeitweaker.api.JeiTweakerPlugin;
import com.blamejared.jeitweaker.api.JeiTweakerPluginProvider;

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
    
}
