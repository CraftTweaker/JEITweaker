package com.blamejared.jeitweaker.common.api.ingredient;

import com.google.common.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public final class JeiIngredientType<J, Z> {
    private final ResourceLocation id;
    private final TypeToken<J> jeiType;
    private final TypeToken<Z> zenType;
    private final JeiIngredientConverter<J, Z> converter;
    
    private JeiIngredientType(final ResourceLocation id, final TypeToken<J> jeiType, final TypeToken<Z> zenType, final JeiIngredientConverter<J, Z> converter) {
        this.id = id;
        this.jeiType = jeiType;
        this.zenType = zenType;
        this.converter = converter;
    }
    
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final Class<J> jeiType, final Class<Z> zenType, final JeiIngredientConverter<J, Z> converter) {
        return of(id, TypeToken.of(jeiType), TypeToken.of(zenType), converter);
    }
    
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final Class<J> jeiType, final TypeToken<Z> zenType, final JeiIngredientConverter<J, Z> converter) {
        return of(id, TypeToken.of(jeiType), zenType, converter);
    }
    
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final TypeToken<J> jeiType, final Class<Z> zenType, final JeiIngredientConverter<J, Z> converter) {
        return of(id, jeiType, TypeToken.of(zenType), converter);
    }
    
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final TypeToken<J> jeiType, final TypeToken<Z> zenType, final JeiIngredientConverter<J, Z> converter) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(jeiType, "jeiType");
        Objects.requireNonNull(zenType, "zenType");
        Objects.requireNonNull(converter, "converter");
        return new JeiIngredientType<>(id, jeiType, zenType, converter);
    }
    
    public ResourceLocation id() {
        return this.id;
    }
    
    public Type jeiType() {
        return this.jeiType.getType();
    }
    
    public Type zenType() {
        return this.zenType.getType();
    }
    
    public JeiIngredientConverter<J, Z> converter() {
        return this.converter;
    }
    
    @Override
    public boolean equals(final Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        final JeiIngredientType<?, ?> that = (JeiIngredientType<?, ?>) o;
        return this.id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
    
    @Override
    public String toString() {
        return "JeiIngredientType[%s]{%s->%s}".formatted(this.id(), this.jeiType().getTypeName(), this.zenType().getTypeName());
    }
    
}
