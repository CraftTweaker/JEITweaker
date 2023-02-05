package com.blamejared.jeitweaker.common.api;

import com.google.common.base.Suppliers;

import java.util.ServiceLoader;
import java.util.function.Supplier;

final class ApiBridgeInstanceHolder {
    private static final Supplier<JeiTweakerApi> API_BRIDGE = Suppliers.memoize(ApiBridgeInstanceHolder::find);
    
    static JeiTweakerApi get() {
        return API_BRIDGE.get();
    }
    
    private static JeiTweakerApi find() {
        final Package apiPackage = JeiTweakerApi.class.getPackage();
        final String rootPackageName = apiPackage.getName().replace(".api", "");
        return ServiceLoader.load(JeiTweakerApi.class)
                .stream()
                .filter(it -> it.type().getPackageName().startsWith(rootPackageName))
                .findFirst()
                .map(ServiceLoader.Provider::get)
                .orElseThrow(() -> new IllegalStateException("Missing JeiTweaker API Bridge service"));
    }
}
