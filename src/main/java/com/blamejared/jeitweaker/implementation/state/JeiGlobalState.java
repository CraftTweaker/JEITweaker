package com.blamejared.jeitweaker.implementation.state;

import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class JeiGlobalState {
    
    private final Set<JeiCategoryData> currentJeiCategories;
    
    JeiGlobalState() {
        this.currentJeiCategories = new LinkedHashSet<>();
    }
    
    public void replaceJeiCategoriesWith(final Set<ResourceLocation> allCategories, final Set<ResourceLocation> nonHiddenCategories) {
        
        this.currentJeiCategories.clear();
        allCategories.stream()
                .map(it -> new JeiCategoryData(it, !nonHiddenCategories.contains(it)))
                .forEach(this.currentJeiCategories::add);
    }
    
    public Set<JeiCategoryData> getCurrentJeiCategories() {
        
        return Collections.unmodifiableSet(this.currentJeiCategories);
    }
}
