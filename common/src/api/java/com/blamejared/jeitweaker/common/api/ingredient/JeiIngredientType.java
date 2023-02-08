package com.blamejared.jeitweaker.common.api.ingredient;

import com.google.common.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public final class JeiIngredientType<J, Z> {
    private final ResourceLocation id;
    private final TypeToken<J> jeiType;
    private final TypeToken<Z> zenType;
    
    private JeiIngredientType(final ResourceLocation id, final TypeToken<J> jeiType, final TypeToken<Z> zenType) {
        this.id = id;
        this.jeiType = jeiType;
        this.zenType = zenType;
    }
    
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final Class<J> jeiType, final Class<Z> zenType) {
        return of(id, TypeToken.of(jeiType), TypeToken.of(zenType));
    }
    
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final Class<J> jeiType, final TypeToken<Z> zenType) {
        return of(id, TypeToken.of(jeiType), zenType);
    }
    
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final TypeToken<J> jeiType, final Class<Z> zenType) {
        return of(id, jeiType, TypeToken.of(zenType));
    }
    
    public static <J, Z> JeiIngredientType<J, Z> of(final ResourceLocation id, final TypeToken<J> jeiType, final TypeToken<Z> zenType) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(jeiType, "jeiType");
        Objects.requireNonNull(zenType, "zenType");
        return new JeiIngredientType<>(id, jeiType, zenType);
    }
    
    public ResourceLocation id() {
        return this.id;
    }
    
    public TypeToken<J> jeiType() {
        return this.jeiType;
    }
    
    public TypeToken<Z> zenType() {
        return this.zenType;
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
        return "JeiIngredientType[%s]{%s->%s}".formatted(this.id(), this.jeiType(), this.zenType());
    }
    
}
