package com.blamejared.jeitweaker.helper.coordinate;

import java.util.function.IntUnaryOperator;

public interface JeiCoordinateFixer {
    
    static JeiCoordinateFixer of(final IntUnaryOperator operator) {
        
        return of(operator, operator);
    }
    
    static JeiCoordinateFixer of(final IntUnaryOperator x, final IntUnaryOperator y) {
        
        return new JeiCoordinateFixer() {
            
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
    
    int fixX(final int original);
    
    int fixY(final int original);
    
}
