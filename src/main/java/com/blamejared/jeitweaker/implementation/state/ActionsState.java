package com.blamejared.jeitweaker.implementation.state;

import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.api.IngredientType;
import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.JeiIngredient;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public final class ActionsState {
    
    private final Multimap<IngredientType<?, ?>, Object> hiddenIngredients;
    private final Set<ResourceLocation> hiddenRecipeCategories;
    private final Set<Pair<ResourceLocation, ResourceLocation>> hiddenRecipes;
    
    private final Map<IngredientType<?, ?>, Map<Object, MCTextComponent[]>> descriptions;
    
    private final Multimap<IngredientType<?, ?>, Object> customIngredients;
    private final List<JeiCategory> customCategories;
    
    ActionsState() {
        
        this.hiddenIngredients = LinkedHashMultimap.create();
        this.hiddenRecipeCategories = new LinkedHashSet<>();
        this.hiddenRecipes = new LinkedHashSet<>();
        
        this.descriptions = new HashMap<>();
        
        this.customIngredients = LinkedHashMultimap.create();
        this.customCategories = new ArrayList<>();
    }
    
    
    public <T, U> void hide(final JeiIngredient<T, U> ingredient) {
        
        this.hiddenIngredients.put(ingredient.getType(), ingredient.getWrapped());
    }
    
    public void hideRecipeCategory(final ResourceLocation category) {
        
        this.hiddenRecipeCategories.add(category);
    }
    
    public void hideRecipe(final ResourceLocation category, final ResourceLocation recipe) {
        
        this.hiddenRecipes.add(Pair.of(category, recipe));
    }
    
    public <T, U> void addDescription(final JeiIngredient<T, U> ingredient, final MCTextComponent... description) {
        
        // TODO("Maybe merging?")
        this.descriptions.computeIfAbsent(ingredient.getType(), it -> new HashMap<>())
                .put(ingredient.getWrapped(), description);
    }
    
    public <T, U> void addCustomIngredient(final JeiIngredient<T, U> ingredient) {
        
        this.customIngredients.put(ingredient.getType(), ingredient.getWrapped());
    }
    
    public void addCustomCategory(final JeiCategory category) {
        
        this.customCategories.add(category);
    }
    
    public <T, U> void show(final JeiIngredient<T, U> ingredient) {
        
        this.hiddenIngredients.remove(ingredient.getType(), ingredient.getWrapped());
    }
    
    public void showRecipeCategory(final ResourceLocation category) {
        
        this.hiddenRecipeCategories.remove(category);
    }
    
    public void showRecipe(final ResourceLocation category, final ResourceLocation recipe) {
        
        this.hiddenRecipes.remove(Pair.of(category, recipe));
    }
    
    public <T, U> void removeDescription(final JeiIngredient<T, U> ingredient) {
        
        this.descriptions.computeIfAbsent(ingredient.getType(), it -> new HashMap<>())
                .remove(ingredient.getWrapped());
    }
    
    public <T, U> void removeCustomIngredient(final JeiIngredient<T, U> ingredient) {
        
        this.customIngredients.remove(ingredient.getType(), ingredient.getWrapped());
    }
    
    public void removeCustomCategory(final JeiCategory category) {
        
        this.customCategories.remove(category);
    }
    
    public <T, U> Collection<T> getHiddenIngredientsForType(final IngredientType<T, U> type) {
        
        return Collections.unmodifiableCollection(this.uncheckCollection(this.hiddenIngredients.get(type)));
    }
    
    public Set<ResourceLocation> getCategoriesToHide() {
        
        return Collections.unmodifiableSet(this.hiddenRecipeCategories);
    }
    
    public Stream<JeiCategory> getCustomCategories() {
        
        return this.customCategories.stream();
    }
    
    public void onHiddenRecipes(final BiConsumer<ResourceLocation, ResourceLocation> consumer) {
        
        this.hiddenRecipes.forEach(it -> consumer.accept(it.getFirst(), it.getSecond()));
    }
    
    @SuppressWarnings("unchecked")
    public <T, U> void onDescriptionsFor(final IngredientType<T, U> type, final BiConsumer<T, ITextComponent[]> consumer) {
        
        this.descriptions.getOrDefault(type, Collections.emptyMap())
                .forEach((ingredient, description) ->
                        consumer.accept((T) ingredient, Arrays.stream(description).map(MCTextComponent::getInternal).toArray(ITextComponent[]::new)));
    }
    
    public <T, U> Collection<T> getCustomIngredientsForType(final IngredientType<T, U> type) {
        
        return Collections.unmodifiableCollection(this.uncheckCollection(this.customIngredients.get(type)));
    }
    
    @SuppressWarnings("unchecked") // Guaranteed to work due to API, just need to erase generics a little
    private <T> Collection<T> uncheckCollection(final Collection<Object> collection) {
        
        return (Collection<T>) collection;
    }
}
