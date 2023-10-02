package com.blamejared.jeitweaker.common.command;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

final class CommandGeneration {
    private final AtomicInteger generation;
    private final Queue<IntConsumer> reproductionManagers;
    
    private CommandGeneration() {
        this.generation = new AtomicInteger(0);
        this.reproductionManagers = Collections.checkedQueue(new ArrayDeque<>(), IntConsumer.class);
    }
    
    static CommandGeneration of() {
        return new CommandGeneration();
    }
    
    int currentGeneration() {
        return this.generation.get();
    }
    
    void awaitingReproduction(final IntConsumer consumer) {
        this.reproductionManagers.offer(consumer);
    }
    
    void reproduce() {
        final int newGeneration = this.generation.incrementAndGet();
        
        IntConsumer consumer;
        while ((consumer = this.reproductionManagers.poll()) != null) {
            consumer.accept(newGeneration);
        }
        
        assert this.reproductionManagers.isEmpty();
    }
    
    @Override
    public String toString() {
        
        return "Generation " + this.currentGeneration() + ", " + this.reproductionManagers.size() + " awaiting for reproduction";
    }
    
}
