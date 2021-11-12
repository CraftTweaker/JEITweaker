package com.blamejared.jeitweaker.api;

import java.util.function.IntUnaryOperator;

public interface CoordinateFixer {
    
    static CoordinateFixer of(final IntUnaryOperator operator) {
        
        return of(operator, operator);
    }
    
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
    
    int fixX(final int original);
    
    int fixY(final int original);
    
}
