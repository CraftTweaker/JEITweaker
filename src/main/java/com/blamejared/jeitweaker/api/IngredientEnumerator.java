package com.blamejared.jeitweaker.api;

import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * Represents an enumerator that can list all possibilities for an {@link IngredientType}.
 *
 * <p>An ingredient enumerator is queried when a list of all possible ingredients for a specific ingredient type is
 * required to perform a certain action in JEI. The responsibility of the enumerator is thus to provide to JeiTweaker
 * a list of all ingredients that JEI knows about of a certain type. If such a thing is not possible, then the
 * enumerator should provide at least a sensible set of defaults (e.g. it is unreasonable to list every possible
 * variation of item stacks, thus a sensible default can be the default stack instance for each item registered in the
 * game).</p>
 *
 * <p>Each ingredient type <strong>must</strong> have an associated enumerator registered through an instance of
 * {@link IngredientEnumeratorRegistration}.</p>
 *
 * <p>It is suggested to use the {@link #ofJeiTweaker(IngredientType, Iterable)},
 * {@link #ofJei(IngredientType, Iterable)} and {@link #of(Iterable, Iterable)} factory methods to
 * create an instance of this class, rather than implementing it directly.</p>
 *
 * @param <T> The exposed type of the ingredient type targeted by the enumerator.
 * @param <U> The internal type of the ingredient type targeted by the enumerator.
 *
 * @since 1.1.0
 */
public interface IngredientEnumerator<T, U> {
    
    /**
     * Creates a new enumerator for the given type and using the given {@link Iterable} to provide a list of elements
     * of the exposed type.
     *
     * @apiNote The {@code type} parameter is used merely to allow for conversion between the exposed type and the
     * internal type of the ingredient type.
     *
     * @param type The ingredient type this enumerator is for.
     * @param jeiTweakerIterable An {@link Iterable} that allows for enumerating all possible ingredients or a sensible
     *                           subset of them, producing instances of the exposed type of the ingredient type.
     * @param <T> The exposed type of the ingredient type.
     * @param <U> The internal type of the ingredient type.
     * @return A new enumerator that follows the given characteristics.
     *
     * @since 1.1.0
     */
    static <T, U> IngredientEnumerator<T, U> ofJeiTweaker(final IngredientType<T, U> type, final Iterable<T> jeiTweakerIterable) {
        
        return of(jeiTweakerIterable, () -> StreamSupport.stream(jeiTweakerIterable.spliterator(), false).map(type::toJeiType).iterator());
    }
    
    /**
     * Creates a new enumerator for the given type and using the given {@link Iterable} to provide a list of elements
     * of the internal type.
     *
     * @apiNote The {@code type} parameter is used merely to allow for conversion between the internal type and the
     * exposed type of the ingredient type.
     *
     * @param type The ingredient type this enumerator is for.
     * @param jeiIterable An {@link Iterable} that allows for enumerating all possible ingredients or a sensible subset
     *                    of them, producing instances of the internal type of the ingredient type.
     * @param <T> The exposed type of the ingredient type.
     * @param <U> The internal type of the ingredient type.
     * @return A new enumerator that follows the given characteristics.
     *
     * @since 1.1.0
     */
    static <T, U> IngredientEnumerator<T, U> ofJei(final IngredientType<T, U> type, final Iterable<U> jeiIterable) {
        
        return of(() -> StreamSupport.stream(jeiIterable.spliterator(), false).map(type::toJeiTweakerType).iterator(), jeiIterable);
    }
    
    /**
     * Creates a new enumerator that uses the given two {@link Iterable}s to provide lists of elements of both the
     * exposed and the internal types of its corresponding ingredient type.
     *
     * <p>It is highly suggested to use either {@link #ofJeiTweaker(IngredientType, Iterable)} or
     * {@link #ofJei(IngredientType, Iterable)} instead of this method for simplicity concerns.</p>
     *
     * @param jeiTweakerIterable An {@link Iterable} that allows for enumerating all possible ingredients or a sensible
     *                           subset of them, producing instances of the exposed type of the ingredient type.
     * @param jeiIterable An {@link Iterable} that allows for enumerating all possible ingredients or a sensible subset
     *                    of them, producing instances of the internal type of the ingredient type.
     * @param <T> The exposed type of the ingredient type.
     * @param <U> The internal type of the ingredient type.
     * @return A new enumerator that follows the given characteristics.
     *
     * @since 1.1.0
     */
    static <T, U> IngredientEnumerator<T, U> of(final Iterable<T> jeiTweakerIterable, final Iterable<U> jeiIterable) {
        
        return new IngredientEnumerator<T, U>() {
   
            @Override
            public Iterable<T> jeiTweakerEnumeration() {
        
                return jeiTweakerIterable;
            }
    
            @Override
            public Iterable<U> jeiEnumeration() {
        
                return jeiIterable;
            }
        };
    }
    
    /**
     * Gets an {@link Iterable} that enumerates all possible ingredients or a sensible default of them, producing
     * instances of the exposed type.
     *
     * @return An {@link Iterable} that produces instances of the exposed type.
     *
     * @since 1.1.0
     */
    Iterable<T> jeiTweakerEnumeration();
    
    /**
     * Gets an {@link Iterable} that enumerates all possible ingredients or a sensible default of them, producing
     * instances of the internal type.
     *
     * @return An {@link Iterable} that produces instances of the internal type.
     *
     * @since 1.1.0
     */
    Iterable<U> jeiEnumeration();
    
    /**
     * Performs the given action on all ingredients of the exposed type.
     *
     * <p>The ingredients are obtained from the value of {@link #jeiTweakerEnumeration()}. The action is executed until
     * all ingredients have been processed.</p>
     *
     * @param consumer The action to execute.
     *
     * @since 1.1.0
     */
    default void forEachJeiTweakerIngredient(final Consumer<T> consumer) {
        
        this.jeiTweakerEnumeration().forEach(consumer);
    }
    
    /**
     * Performs the given action on all ingredients of the internal type.
     *
     * <p>The ingredients are obtained from the value of {@link #jeiEnumeration()} ()}. The action is executed until all
     * ingredients have been processed.</p>
     *
     * @param consumer The action to execute.
     *
     * @since 1.1.0
     */
    default void forEachJeiIngredient(final Consumer<U> consumer) {
        
        this.jeiEnumeration().forEach(consumer);
    }
}
