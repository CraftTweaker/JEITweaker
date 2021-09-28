package com.blamejared.jeitweaker.plugin;

import com.blamejared.jeitweaker.zen.category.JeiCategory;
import com.blamejared.jeitweaker.zen.component.HackyJeiIngredientToMakeZenCodeHappy;
import com.blamejared.jeitweaker.zen.recipe.JeiRecipe;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.runtime.IIngredientManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class JeiTweakerRecipe {
    
    @FunctionalInterface
    private interface IngredientSetter {
        
        <U> void set(final IIngredientType<U> ingredientType, final List<List<U>> inputs);
    }
    
    private final JeiRecipe recipe;
    private final IIngredientManager manager;
    private final JeiCoordinateFixer fixer;
    private final Supplier<Map<IIngredientType<?>, List<List<?>>>> ingredients;
    private final Supplier<Map<IIngredientType<?>, List<List<?>>>> results;
    private final Supplier<Set<IIngredientType<?>>> consideredTypes;
    
    JeiTweakerRecipe(final JeiRecipe recipe, final IIngredientManager manager) {
        
        this.recipe = recipe;
        this.manager = manager;
        this.fixer = new JeiCoordinateFixer(manager);
        this.ingredients = Suppliers.memoize(() -> this.computeJeiMaps(this.manager, this.recipe.getInputs()));
        this.results = Suppliers.memoize(() -> this.computeJeiMaps(this.manager, this.recipe.getOutputs()));
        this.consideredTypes = Suppliers.memoize(() -> this.computeJeiTypes(this.ingredients.get(), this.results.get()));
    }
    
    JeiCategory getOwningCategory() {
        
        return this.recipe.getOwningCategory();
    }
    
    void setIngredients(final IIngredients ingredients) {
        
        this.setIngredients(this.ingredients.get(), ingredients::setInputLists);
        this.setIngredients(this.results.get(), ingredients::setOutputLists);
    }
    
    void setRecipe(final IRecipeLayout layout, final BiConsumer<IGuiIngredientGroup<?>, IntUnaryOperator> layoutMaker, final long slotsData) {
        
        this.initializeRecipeGui(layout, layoutMaker);
        this.placeIngredients(layout, (int) slotsData, (int) (slotsData >>> 32));
    }
    
    private void setIngredients(final Map<IIngredientType<?>, List<List<?>>> data, final IngredientSetter setter) {
        
        data.forEach((type, ingredient) -> setter.set(type, this.uncheck(ingredient)));
    }
    
    private void initializeRecipeGui(final IRecipeLayout layout, final BiConsumer<IGuiIngredientGroup<?>, IntUnaryOperator> layoutMaker) {
        
        this.consideredTypes.get().forEach(it -> layoutMaker.accept(layout.getIngredientsGroup(it), this.fixer.findFor(it)));
    }
    
    private void placeIngredients(final IRecipeLayout layout, final int inSlots, final int outSlots) {
        
        this.placeIngredientsIn(layout, this.ingredients.get(), 0, inSlots);
        this.placeIngredientsIn(layout, this.results.get(), inSlots, outSlots);
    }
    
    private void placeIngredientsIn(
            final IRecipeLayout layout,
            final Map<IIngredientType<?>, List<List<?>>> data,
            final int startIndex,
            final int slotAmount
    ) {
        
        data.forEach((type, slots) -> {
    
            final IGuiIngredientGroup<?> group = layout.getIngredientsGroup(type);
            IntStream.range(0, Math.min(slots.size(), slotAmount)).forEach(slot -> group.set(slot + startIndex, this.uncheckIngredientList(slots.get(slot))));
        });
    }
    
    private Map<IIngredientType<?>, List<List<?>>> computeJeiMaps(final IIngredientManager manager, final HackyJeiIngredientToMakeZenCodeHappy[][] array) {
        
        final List<List<Pair<JeiTweakerIngredientType<?, ?>, ?>>> jeiLists = this.computeJeiList(array);
        final Map<IIngredientType<?>, List<List<?>>> returnValue = new LinkedHashMap<>();
    
        IntStream.range(0, jeiLists.size()).forEach(slot -> {
            
            final List<Pair<JeiTweakerIngredientType<?, ?>, ?>> validElementsForSlot = jeiLists.get(slot);
            
            validElementsForSlot.forEach(ingredientData -> {
                
                final IIngredientType<?> type = ingredientData.getFirst().toJeiType(manager);
                final List<List<?>> ingredientTypedSlots = returnValue.computeIfAbsent(type, it -> new ArrayList<>());
                
                // All previous slots are assumed empty, so we fill the list with that information
                if (ingredientTypedSlots.size() <= slot) {
                    
                    IntStream.rangeClosed(ingredientTypedSlots.size(), slot).forEach(ignore -> ingredientTypedSlots.add(new ArrayList<>()));
                }
                
                // Guaranteed to exist because of the above check and subsequent addition
                ingredientTypedSlots.get(slot).add(this.uncheck(ingredientData.getSecond()));
            });
        });
        
        return returnValue;
    }
    
    // outer list: list of slots; inner list: list of valid ingredients for that slot
    private List<List<Pair<JeiTweakerIngredientType<?, ?>, ?>>> computeJeiList(final HackyJeiIngredientToMakeZenCodeHappy[][] array) {
        
        return Arrays.stream(array)
                .map(outer -> Arrays.stream(outer)
                        .map(HackyJeiIngredientToMakeZenCodeHappy::cast)
                        .map(it -> Pair.of(it.getType(), it.getType().toInternal(it.getWrapped())))
                        .collect(Collectors.toList())
                )
                .map(this::uncheckList)
                .collect(Collectors.toList());
    }
    
    private Set<IIngredientType<?>> computeJeiTypes(final Map<IIngredientType<?>, ?> in, final Map<IIngredientType<?>, ?> out) {
    
        return Stream.concat(in.keySet().stream(), out.keySet().stream()).collect(Collectors.toSet());
    }
    
    @SuppressWarnings("unchecked")
    private <T, U> U uncheck(final T t) {
        
        return (U) t;
    }
    
    @SuppressWarnings("unchecked")
    private <T, U> List<Pair<JeiTweakerIngredientType<?, ?>, ?>> uncheckList(final List<Pair<T, U>> list) {
        
        return (List<Pair<JeiTweakerIngredientType<?, ?>, ?>>) (List<?>) list;
    }
    
    @SuppressWarnings("unchecked")
    private <T, U> List<U> uncheckIngredientList(final List<T> list) {
        
        return (List<U>) list;
    }
}
