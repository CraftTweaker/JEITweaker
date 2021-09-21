package com.blamejared.jeitweaker.state;

import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.jeitweaker.zen.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.JeiIngredient;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public enum JeiStateManager {
    INSTANCE;
    
    private final Set<JeiCategoryData> currentJeiCategories = new LinkedHashSet<>();
    
    private final Multimap<JeiTweakerIngredientType<?, ?>, Object> hiddenIngredients = LinkedHashMultimap.create();
    private final Set<ResourceLocation> hiddenRecipeCategories = new LinkedHashSet<>();
    private final Set<Pair<ResourceLocation, ResourceLocation>> hiddenRecipes = new LinkedHashSet<>();
    
    private final Map<JeiTweakerIngredientType<?, ?>, Map<Object, String[]>> descriptions = new HashMap<>();
    
    private final Multimap<JeiTweakerIngredientType<?, ?>, Object> customIngredients = LinkedHashMultimap.create();
    
    public void replaceJeiCategoriesWith(final Set<ResourceLocation> allCategories, final Set<ResourceLocation> nonHiddenCategories) {
        this.currentJeiCategories.clear();
        allCategories.stream()
                .map(it -> new JeiCategoryData(it, !nonHiddenCategories.contains(it)))
                .forEach(this.currentJeiCategories::add);
    }
    
    public <T extends CommandStringDisplayable, U> void hide(final JeiIngredient<T, U> ingredient) {
        
        this.hiddenIngredients.put(ingredient.getType(), ingredient.getWrapped());
    }
    
    public void hideRecipeCategory(final ResourceLocation category) {
        
        this.hiddenRecipeCategories.add(category);
    }
    
    public void hideRecipe(final ResourceLocation category, final ResourceLocation recipe) {
        
        this.hiddenRecipes.add(Pair.of(category, recipe));
    }
    
    public <T extends CommandStringDisplayable, U> void addDescription(final JeiIngredient<T, U> ingredient, final String... description) {
        
        // TODO("Maybe merging?")
        this.descriptions.computeIfAbsent(ingredient.getType(), it -> new HashMap<>())
                .put(ingredient.getWrapped(), description);
    }
    
    public <T extends CommandStringDisplayable, U> void addCustomIngredient(final JeiIngredient<T, U> ingredient) {
        
        this.customIngredients.put(ingredient.getType(), ingredient.getWrapped());
    }
    
    public <T extends CommandStringDisplayable, U> void show(final JeiIngredient<T, U> ingredient) {
        
        this.hiddenIngredients.remove(ingredient.getType(), ingredient.getWrapped());
    }
    
    public void showRecipeCategory(final ResourceLocation category) {
        
        this.hiddenRecipeCategories.remove(category);
    }
    
    public void showRecipe(final ResourceLocation category, final ResourceLocation recipe) {
        
        this.hiddenRecipes.remove(Pair.of(category, recipe));
    }
    
    public <T extends CommandStringDisplayable, U> void removeDescription(final JeiIngredient<T, U> ingredient) {
        
        this.descriptions.computeIfAbsent(ingredient.getType(), it -> new HashMap<>())
                .remove(ingredient.getWrapped());
    }
    
    public <T extends CommandStringDisplayable, U> void removeCustomIngredient(final JeiIngredient<T, U> ingredient) {
        
        this.customIngredients.remove(ingredient.getType(), ingredient.getWrapped());
    }
    
    public Set<JeiCategoryData> getCurrentJeiCategories() {
        
        return Collections.unmodifiableSet(this.currentJeiCategories);
    }
    
    public <T, U> Collection<T> getHiddenIngredientsForType(final JeiTweakerIngredientType<T, U> type) {
        
        return Collections.unmodifiableCollection(this.uncheckCollection(this.hiddenIngredients.get(type)));
    }
    
    public Set<ResourceLocation> getCategoriesToHide() {
        
        return Collections.unmodifiableSet(this.hiddenRecipeCategories);
    }
    
    public void onHiddenRecipes(final BiConsumer<ResourceLocation, ResourceLocation> consumer) {
        
        this.hiddenRecipes.forEach(it -> consumer.accept(it.getFirst(), it.getSecond()));
    }
    
    @SuppressWarnings("unchecked")
    public <T, U> void onDescriptionsFor(final JeiTweakerIngredientType<T, U> type, final BiConsumer<T, ITextComponent[]> consumer) {
        
        this.descriptions.getOrDefault(type, Collections.emptyMap())
                .forEach((ingredient, description) ->
                        consumer.accept((T) ingredient, Arrays.stream(description).map(TranslationTextComponent::new).toArray(ITextComponent[]::new)));
    }
    
    public <T, U> Collection<T> getCustomIngredientsForType(final JeiTweakerIngredientType<T, U> type) {
        
        return Collections.unmodifiableCollection(this.uncheckCollection(this.customIngredients.get(type)));
    }
    
    @SuppressWarnings("unchecked") // Guaranteed to work due to API, just need to erase generics a little
    private <T> Collection<T> uncheckCollection(final Collection<Object> collection) {
        
        return (Collection<T>) collection;
    }
}
