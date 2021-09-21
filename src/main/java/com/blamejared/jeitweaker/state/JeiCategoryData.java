package com.blamejared.jeitweaker.state;

import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public final class JeiCategoryData {

    private final ResourceLocation id;
    private final boolean isHidden;

    JeiCategoryData(final ResourceLocation id, final boolean isHidden) {

        this.id = id;
        this.isHidden = isHidden;
    }

    public ResourceLocation getId() {

        return this.id;
    }

    public boolean isHidden() {

        return this.isHidden;
    }

    @Override
    public boolean equals(final Object o) {

        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        final JeiCategoryData that = (JeiCategoryData) o;
        return this.isHidden == that.isHidden && this.id.equals(that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.isHidden);
    }

    @Override
    public String toString() {

        return this.id + (this.isHidden? " (currently hidden)" : "");
    }

}
