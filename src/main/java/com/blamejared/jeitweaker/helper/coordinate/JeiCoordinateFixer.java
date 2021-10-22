package com.blamejared.jeitweaker.helper.coordinate;

import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface JeiCoordinateFixer extends IntUnaryOperator {
    
    static JeiCoordinateFixer of(final IntUnaryOperator operator) {
        
        return operator::applyAsInt;
    }
    
    static JeiCoordinateFixer of(final IntUnaryOperator x, final IntUnaryOperator y) {
        
        return new JeiCoordinateFixer() {
            
            @Override
            public int fix(final int original) {
        
                throw new UnsupportedOperationException();
            }
    
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
    
    int fix(final int original);
    
    default int fixX(final int original) {
        
        return this.fix(original);
    }
    
    default int fixY(final int original) {
        
        return this.fix(original);
    }

    @Override
    default int applyAsInt(final int operand) {
        
        return this.fix(operand);
    }
    
}
