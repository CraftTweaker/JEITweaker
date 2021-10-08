package com.blamejared.jeitweaker.component.ninepatch;

final class NinePatchRegion {
    
    private final NinePatchBehavior behavior;
    private final int beginning;
    private final int size;
    
    private NinePatchRegion(final NinePatchBehavior behavior, final int beginning, final int size) {
        
        this.behavior = behavior;
        this.beginning = beginning;
        this.size = size;
    }
    
    static NinePatchRegion of(final NinePatchBehavior behavior, final int beginning, final int size) throws InvalidNinePatchDataException {
        
        if (beginning < 0) {
            
            throw new InvalidNinePatchDataException("Unable to build nine-patch region for invalid beginning coordinate " + beginning);
        }
        
        if (size < 0) {
            
            throw new InvalidNinePatchDataException("Unable to build nine-patch region for invalid size " + size);
        }
        
        return new NinePatchRegion(behavior, beginning, size);
    }
    
    
    public NinePatchBehavior behavior() {
        
        return this.behavior;
    }
    
    public int beginning() {
        
        return this.beginning;
    }
    
    public int size() {
        
        return this.size;
    }
}
