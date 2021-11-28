package com.blamejared.jeitweaker.api;

import java.util.function.IntUnaryOperator;

/**
 * Represents a fixer to adjust slot coordinates for an ingredient type.
 *
 * <p>JEI renders certain ingredients as slightly offset from the actual slot position, such as items. A coordinate
 * fixer can be used to counteract this offset by specifying the actual position of the slot as slightly off from the
 * real set of coordinates.</p>
 *
 * <p>JeiTweaker plugins should register a coordinate fixer for a specific ingredient type through the given
 * {@link CoordinateFixerRegistration}.</p>
 *
 * <p>All custom category bridges should query the given fixer every time they need to add a slot to the category. Refer
 * to the bridge-specific documentation for more information.</p>
 *
 * <p>It is suggested to use the {@link #of(IntUnaryOperator)} and {@link #of(IntUnaryOperator, IntUnaryOperator)}
 * factory methods to create an instance of this interface instead of implementing it directly.</p>
 *
 * @since 1.1.0
 */
public interface CoordinateFixer {
    
    /**
     * Creates a new fixer that uses the given {@code operator} to fix both X and Y coordinates.
     *
     * @param operator The operator to use for fixing both coordinates.
     * @return A fixer that carries out the specified operation.
     *
     * @apiNote Calling this method corresponds to calling {@link #of(IntUnaryOperator, IntUnaryOperator)} with the
     * same unary operator for both arguments.
     *
     * @since 1.1.0
     */
    static CoordinateFixer of(final IntUnaryOperator operator) {
        
        return of(operator, operator);
    }
    
    /**
     * Creates a new fixer that uses the given operators to fix both X and Y coordinates.
     *
     * @param x The operator to use to fix the X coordinate.
     * @param y The operator to use to fix the Y coordinate.
     * @return A fixer that carries out the specified operation.
     *
     * @since 1.1.0
     */
    static CoordinateFixer of(final IntUnaryOperator x, final IntUnaryOperator y) {
        
        return new CoordinateFixer() {
            
            @Override
            public int fixX(final int original) {
        
                return x.applyAsInt(original);
            }
    
            @Override
            public int fixY(final int original) {
    
                return y.applyAsInt(original);
            }
        };
    }
    
    /**
     * Fixes the X coordinate by slightly altering it so that it fits the given slot coordinate.
     *
     * @param original The original slot X coordinate
     * @return The fixed slot X coordinate, accounting for the necessary offsets.
     *
     * @since 1.1.0
     */
    int fixX(final int original);
    
    /**
     * Fixes the Y coordinate by slightly altering it so that it fits the given slot coordinate.
     *
     * @param original The original slot Y coordinate
     * @return The fixed slot Y coordinate, accounting for the necessary offsets.
     *
     * @since 1.1.0
     */
    int fixY(final int original);
    
}
