package com.blamejared.jeitweaker.common.api.ingredient;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Hosts a series of providers of helper functions to obtain {@link JeiIngredientCreator.Creator} instances.
 *
 * <p>Clients of this API should never need to obtain an instance of this interface.</p>
 *
 * @since 4.0.0
 */
public interface JeiIngredientCreator {
    
    /**
     * Handles the creation of a {@link JeiIngredient} with the given {@link JeiIngredientType}.
     *
     * <p>This interface essentially allows for late binding of the ingredient type, while still enforcing proper type
     * constraints for both the script and JEI objects that might be stored in the ingredient.</p>
     *
     * <p>This interface is a {@linkplain FunctionalInterface functional interface} representing a {@link Function},
     * with its functional method being {@link #apply(Object)}. In this context, it acts merely as a type alias.</p>
     *
     * @param <J> The type of the JEI object of the creator.
     * @param <Z> The type of the script object of the creator.
     *
     * @since 4.0.0
     */
    @FunctionalInterface
    interface Creator<J, Z> extends Function<JeiIngredientType<J, Z>, JeiIngredient<J, Z>> {}
    
    /**
     * Manages the creation of {@link Creator} instances for ingredients containing solely a script object.
     *
     * <p>The various {@code Creator}s can be obtained through either of the methods exposed by this interface. Namely,
     * {@link #of(Object)} should be used whenever the script object is immutable, whereas
     * {@link #of(Object, UnaryOperator)} whenever the script object is mutable. Refer to the specific documentation for
     * additional details.</p>
     *
     * @since 4.0.0
     */
    interface FromZen {
    
        /**
         * Returns a {@link Creator} instance of a {@link JeiIngredient} containing the given immutable script object.
         *
         * <p>If the script object is mutable, then {@link #of(Object, UnaryOperator)} <strong>must</strong> be used
         * instead.</p>
         *
         * @param zenContent The script object that the created ingredient should contain.
         * @return A {@link Creator} for an ingredient.
         * @param <J> The type of the JEI object of the creator.
         * @param <Z> The type of the script object of the creator.
         * @throws NullPointerException If any of the arguments is {@code null}.
         *
         * @since 4.0.0
         */
        <J, Z> Creator<J, Z> of(final Z zenContent);
    
        /**
         * Returns a {@link Creator} instance of a {@link JeiIngredient} containing the given mutable script object.
         *
         * <p>If the script object is immutable, then {@link #of(Object)} <strong>must</strong> be used instead.</p>
         *
         * @param zenContent The script object that the created ingredient should contain.
         * @param copier A {@link UnaryOperator} that can create a copy of the given script object, i.e. a new object
         *               containing the exact same information as the original.
         * @return A {@link Creator} for an ingredient.
         * @param <J> The type of the JEI object of the creator.
         * @param <Z> The type of the script object of the creator.
         * @throws NullPointerException If any of the arguments is {@code null}.
         *
         * @since 4.0.0
         */
        <J, Z> Creator<J, Z> of(final Z zenContent, final UnaryOperator<Z> copier);
    }
    
    /**
     * Manages the creation of {@link Creator} instances for ingredients containing solely a JEI object.
     *
     * <p>The various {@code Creator}s can be obtained through either of the methods exposed by this interface. Namely,
     * {@link #of(Object)} should be used whenever the JEI object is immutable, whereas
     * {@link #of(Object, UnaryOperator)} whenever the JEI object is mutable. Refer to the specific documentation for
     * additional details.</p>
     *
     * @since 4.0.0
     */
    interface FromJei {
        
        /**
         * Returns a {@link Creator} instance of a {@link JeiIngredient} containing the given immutable JEI object.
         *
         * <p>If the JEI object is mutable, then {@link #of(Object, UnaryOperator)} <strong>must</strong> be used
         * instead.</p>
         *
         * @param jeiContent The JEI object that the created ingredient should contain.
         * @return A {@link Creator} for an ingredient.
         * @param <J> The type of the JEI object of the creator.
         * @param <Z> The type of the script object of the creator.
         * @throws NullPointerException If any of the arguments is {@code null}.
         *
         * @since 4.0.0
         */
        <J, Z> Creator<J, Z> of(final J jeiContent);
    
        /**
         * Returns a {@link Creator} instance of a {@link JeiIngredient} containing the given mutable JEI object.
         *
         * <p>If the JEI object is immutable, then {@link #of(Object)} <strong>must</strong> be used instead.</p>
         *
         * @param jeiContent The JEI object that the created ingredient should contain.
         * @param copier A {@link UnaryOperator} that can create a copy of the given JEI object, i.e. a new object
         *               containing the exact same information as the original.
         * @return A {@link Creator} for an ingredient.
         * @param <J> The type of the JEI object of the creator.
         * @param <Z> The type of the script object of the creator.
         * @throws NullPointerException If any of the arguments is {@code null}.
         *
         * @since 4.0.0
         */
        <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> copier);
    }
    
    /**
     * Manages the creation of {@link Creator} instances for ingredients containing both a script object and a JEI
     * object.
     *
     * <p>The various {@code Creator}s can be obtained through one of the methods exposed by this interface. Namely,
     * {@link #of(Object, Object)} should be used if both objects are immutable,
     * {@link #of(Object, UnaryOperator, Object)} should be used if the JEI object is mutable and the script object is
     * immutable, and {@link #of(Object, UnaryOperator, Object, UnaryOperator)} should be used whenever the script
     * object is mutable, regardless of the mutability status of the JEI object. Refer to the specific documentation for
     * additional details.</p>
     *
     * @since 4.0.0
     */
    interface FromBoth {
        
        /**
         * Returns a {@link Creator} instance of a {@link JeiIngredient} containing the given immutable JEI and script
         * objects.
         *
         * <p>If the script object is mutable, then {@link #of(Object, UnaryOperator, Object, UnaryOperator)}
         * <strong>must</strong> be used instead. On the other hand, if the script object is immutable but the JEI
         * object is mutable, {@link #of(Object, UnaryOperator, Object)} <strong>must</strong> be used.</p>
         *
         * @param jeiContent The JEI object that the created ingredient should contain.
         * @param zenContent The script object that the created ingredient should contain.
         * @return A {@link Creator} for an ingredient.
         * @param <J> The type of the JEI object of the creator.
         * @param <Z> The type of the script object of the creator.
         * @throws NullPointerException If any of the arguments is {@code null}.
         *
         * @since 4.0.0
         */
        <J, Z> Creator<J, Z> of(final J jeiContent, final Z zenContent);
    
        /**
         * Returns a {@link Creator} instance of a {@link JeiIngredient} containing the given mutable JEI object and
         * the given immutable script object.
         *
         * <p>If the script object is mutable, then {@link #of(Object, UnaryOperator, Object, UnaryOperator)}
         * <strong>must</strong> be used instead. On the other hand, if both objects are immutable, then
         * {@link #of(Object, Object)} <strong>must</strong> be used.</p>
         *
         * @param jeiContent The JEI object that the created ingredient should contain.
         * @param jeiCopier A {@link UnaryOperator} that can create a copy of the given JEI object, i.e. a new object
         *                  containing the exact same information as the original.
         * @param zenContent The script object that the created ingredient should contain.
         * @return A {@link Creator} for an ingredient.
         * @param <J> The type of the JEI object of the creator.
         * @param <Z> The type of the script object of the creator.
         * @throws NullPointerException If any of the arguments is {@code null}.
         *
         * @since 4.0.0
         */
        <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> jeiCopier, final Z zenContent);
    
        /**
         * Returns a {@link Creator} instance of a {@link JeiIngredient} containing the given JEI object and the given
         * mutable script object.
         *
         * <p>If the script object is immutable and the JEI object is mutable, then
         * {@link #of(Object, UnaryOperator, Object)} <strong>must</strong> be used instead. On the other hand, if
         * both objects are immutable, then {@link #of(Object, Object)} <strong>must</strong> be used.</p>
         *
         * <p>If the script object is mutable, but the JEI object is immutable, then this is the correct method to
         * use.</p>
         *
         * @param jeiContent The JEI object that the created ingredient should contain.
         * @param jeiCopier A {@link UnaryOperator} that can create a copy of the given JEI object, i.e. a new object
         *                  containing the exact same information as the original, if the JEI object is mutable;
         *                  {@code null} if the JEI object is immutable.
         * @param zenContent The script object that the created ingredient should contain.
         * @param zenCopier A {@link UnaryOperator} that can create a copy of the given script object, i.e. a new object
         *                  containing the exact same information as the original.
         * @return A {@link Creator} for an ingredient.
         * @param <J> The type of the JEI object of the creator.
         * @param <Z> The type of the script object of the creator.
         * @throws NullPointerException If any of the arguments, except for {@code jeiCopier}, is {@code null}.
         *
         * @since 4.0.0
         */
        <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> jeiCopier, final Z zenContent, final UnaryOperator<Z> zenCopier);
    }
    
    /**
     * Provides a series of creators that can create ingredients wrapping solely script objects.
     *
     * @return A provider of creators.
     *
     * @since 4.0.0
     */
    FromZen fromZen();
    
    /**
     * Provides a series of creators that can create ingredients wrapping solely JEI objects.
     *
     * @return A provider of creators.
     *
     * @since 4.0.0
     */
    FromJei fromJei();
    
    /**
     * Provides a series of creators that can create ingredients wrapping both script and JEI objects.
     *
     * @return A provider of creators.
     *
     * @since 4.0.0
     */
    FromBoth fromBoth();
}
