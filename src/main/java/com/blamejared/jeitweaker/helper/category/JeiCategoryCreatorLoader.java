package com.blamejared.jeitweaker.helper.category;

import com.google.common.base.Suppliers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

class JeiCategoryCreatorLoader extends ClassLoader {
    
    private final Map<String, Supplier<Class<?>>> classes;
    
    JeiCategoryCreatorLoader(final ClassLoader parent) {
        super(parent);
        this.classes = new HashMap<>();
    }

    @Override
    public Class<?> loadClass(final String name) throws ClassNotFoundException {
        
        if (this.classes.containsKey(name)) return this.classes.get(name).get();
        return super.loadClass(name);
    }
    
    void addClass(final String name, final byte[] data) {
        
        this.classes.put(name, Suppliers.memoize(() -> this.defineClass(name, data, 0, data.length)));
    }
}
