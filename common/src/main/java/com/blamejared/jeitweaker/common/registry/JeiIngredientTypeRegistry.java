package com.blamejared.jeitweaker.common.registry;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientConverter;
import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientType;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;

public final class JeiIngredientTypeRegistry {
    private final BiMap<ResourceLocation, JeiIngredientType<?, ?>> ingredientTypes;
    private final BiMap<IIngredientType<?>, JeiIngredientType<?, ?>> jeiIngredientTypesMapping;
    private final BiMap<JeiIngredientType<?, ?>, IIngredientType<?>> jeiIngredientTypesMappingInverseView;
    private final Map<JeiIngredientType<?, ?>, JeiIngredientConverter<?, ?>> converters;
    
    JeiIngredientTypeRegistry() {
        this.ingredientTypes = HashBiMap.create();
        this.jeiIngredientTypesMapping = HashBiMap.create();
        this.jeiIngredientTypesMappingInverseView = this.jeiIngredientTypesMapping.inverse();
        this.converters = new Object2ObjectOpenHashMap<>();
    }
    
    public <J, Z> void register(final JeiIngredientType<J, Z> type, final JeiIngredientConverter<J, Z> converter, final IIngredientType<J> jeiType) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(converter, "converter");
        Objects.requireNonNull(jeiType, "jeiType");
    
        //noinspection UnstableApiUsage
        if (type.jeiType().isSubtypeOf(jeiType.getIngredientClass())) {
            throw new IllegalArgumentException("Type " + type + " does not match the JEI type " + jeiType + ", nor it is a subtype");
        }
        
        if (this.ingredientTypes.containsValue(type)) {
            throw new IllegalArgumentException("Type " + type + " has already been registered");
        }
        
        final ResourceLocation id = type.id();
        if (this.ingredientTypes.containsKey(id)) {
            throw new IllegalArgumentException("A type with the same id " + id + " has already been registered");
        }
        
        this.ingredientTypes.put(id, type);
        this.jeiIngredientTypesMapping.put(jeiType, type);
        this.converters.put(type, converter);
    }
    
    public <J, Z> JeiIngredientType<J, Z> findById(final ResourceLocation id) {
        return this.queryMap(this.ingredientTypes, id);
    }
    
    public <J, Z> JeiIngredientType<J, Z> findByJeiTypeOrNull(final IIngredientType<J> jeiType) {
        return GenericUtil.uncheck(this.jeiIngredientTypesMapping.get(jeiType));
    }
    
    public <J, Z> JeiIngredientConverter<J, Z> converterFor(final JeiIngredientType<J, Z> type) {
        return this.queryMap(this.converters, type);
    }
    
    public <J, Z> IIngredientType<J> jeiTypeOf(final JeiIngredientType<J, Z> type) {
        return this.queryMap(this.jeiIngredientTypesMappingInverseView, type);
    }
    
    private <K, V, R extends V, T extends K> R queryMap(final Map<K, V> map, final T key) {
        return GenericUtil.uncheck(map.computeIfAbsent(Objects.requireNonNull(key, "key"), it -> {
            throw new IllegalArgumentException("No known Jei ingredient mapped by " + it);
        }));
    }
}
