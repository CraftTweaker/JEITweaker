package com.blamejared.jeitweaker.api;

import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.util.ResourceLocation;

/**
 * Represents an ingredient type inside JeiTweaker.
 *
 * <p>An ingredient type is responsible for abstracting out an ingredient inside the code of JeiTweaker, allowing it
 * work with ingredients generically, without assuming any particular implementation.</p>
 *
 * <p>An ingredient type is thus responsible for determining the type of the ingredient exposed to JeiTweaker and its
 * plugins and the internal type used when communicating with both game code and JEI. An ingredient type must also be
 * able to verify whether two instances of the exposed type are the same and convert itself to the internal JEI
 * ingredient type.</p>
 *
 * <p>It is customary, although not required, that each ingredient type in JEI has a corresponding JeiTweaker ingredient
 * type and vice versa, in a 1-to-1 relation.</p>
 *
 * @param <T> The type exposed to JeiTweaker and its plugins.
 * @param <U> The internal type used when communicating with JEI and game code.
 *
 * @since 1.1.0
 */
public interface IngredientType<T, U> {
    
    /**
     * Gets the id that uniquely identifies this ingredient type.
     *
     * @return The id that uniquely identifies this ingredient type.
     *
     * @since 1.1.0
     */
    ResourceLocation id();
    
    /**
     * Gets the class that identifies the exposed type of this ingredient.
     *
     * @return The class that identifies the exposed type of this ingredient.
     *
     * @since 1.1.0
     */
    Class<T> jeiTweakerType();
    
    /**
     * Gets the class that identifies the internal type of this ingredient.
     *
     * @return The class that identifies the internal type of this ingredient.
     *
     * @since 1.1.0
     */
    Class<U> jeiType();
    
    /**
     * Converts the given instance of an exposed type to the corresponding instance of the internal type.
     *
     * @param t The exposed type instance to convert.
     * @return The instance of the internal type the given exposed type converts to.
     *
     * @since 1.1.0
     */
    U toJeiType(final T t);
    
    /**
     * Converts the given instance of an internal type to the corresponding instance of the exposed type.
     *
     * @param u The internal type instance to convert.
     * @return The instance of the exposed type the given internal type converts to.
     *
     * @since 1.1.0
     */
    T toJeiTweakerType(final U u);
    
    /**
     * Converts this ingredient type to the {@link IIngredientType} used internally by JEI.
     *
     * @param manager The {@link IIngredientManager} where all known ingredient types are stored.
     * @return The {@link IIngredientType} used by JEI that corresponds to this ingredient type.
     *
     * @implSpec The implementation should use the given manager to convert this ingredient type to the JEI equivalent.
     * The implementation of this method should thus use {@link IIngredientManager#getIngredientType(Class)}, in a form
     * similar to the following: {@code manager.getIngredientType(this.jeiType())}.
     *
     * @since 1.1.0
     */
    IIngredientType<U> toJeiIngredientType(final IIngredientManager manager);
    
    /**
     * Verifies whether two instances of the exposed times match.
     *
     * <p>The definition of match in this element varies depending on the ingredient type. It may be a direct equality
     * check or a more complicated definition that verifies additional properties. Commonly, it follows the logic of the
     * ingredient when used to verify whether a certain instance is valid when crafting a recipe.</p>
     *
     * @param a The first instance of the exposed type to verify.
     * @param b The second instance of the exposed type to verify.
     * @return Whether the two ingredients match.
     *
     * @since 1.1.0
     */
    boolean match(final T a, final T b);
}
