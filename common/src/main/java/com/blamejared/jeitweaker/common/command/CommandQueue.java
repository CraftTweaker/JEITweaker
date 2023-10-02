package com.blamejared.jeitweaker.common.command;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.jeitweaker.common.api.command.JeiCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class CommandQueue<T> implements Queue<GenerativeCommand<T>> {
    private final Queue<GenerativeCommand<T>> delegate;
    private final Queue<GenerativeCommand<T>> buffer;
    
    private CommandQueue(final Queue<GenerativeCommand<T>> delegate, final Queue<GenerativeCommand<T>> buffer) {
        this.delegate = delegate;
        this.buffer = buffer;
    }
    
    static <T> CommandQueue<T> of() {
        return new CommandQueue<>(
                Collections.checkedQueue(new ArrayDeque<>(), GenericUtil.uncheck(GenerativeCommand.class)),
                Collections.checkedQueue(new ArrayDeque<>(), GenericUtil.uncheck(GenerativeCommand.class))
        );
    }
    
    public void runCommands(final int generation, final T argument) {
        GenerativeCommand<T> head;
        while ((head = this.poll()) != null) {
            if (head.generation() == generation) {
                SafeJeiCommandManager.safeOf(head.command()).execute(argument);
                this.buffer.add(head);
            }
        }
        
        assert this.isEmpty();
        
        while ((head = this.buffer.poll()) != null) {
            this.add(head);
        }
        
        assert this.buffer.isEmpty();
    }
    
    @Override
    public boolean add(final GenerativeCommand<T> tJeiCommand) {
        return this.delegate.add(tJeiCommand);
    }
    
    @Override
    public boolean offer(final GenerativeCommand<T> tJeiCommand) {
        return this.delegate.offer(tJeiCommand);
    }
    
    @Override
    public GenerativeCommand<T> remove() {
        return this.delegate.remove();
    }
    
    @Override
    public GenerativeCommand<T> poll() {
        return this.delegate.poll();
    }
    
    @Override
    public GenerativeCommand<T> element() {
        return this.delegate.element();
    }
    
    @Override
    public GenerativeCommand<T> peek() {
        return this.delegate.peek();
    }
    
    @Override
    public int size() {
        return this.delegate.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.delegate.contains(o);
    }
    
    @NotNull
    @Override
    public Iterator<GenerativeCommand<T>> iterator() {
        return this.delegate.iterator();
    }
    
    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        return this.delegate.toArray();
    }
    
    @NotNull
    @Override
    public <U> U @NotNull [] toArray(@NotNull final U @NotNull [] a) {
        return this.delegate.toArray(a);
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.delegate.remove(o);
    }
    
    @Override
    public boolean containsAll(@NotNull final Collection<?> c) {
        return this.delegate.containsAll(c);
    }
    
    @Override
    public boolean addAll(@NotNull final Collection<? extends GenerativeCommand<T>> c) {
        return this.delegate.addAll(c);
    }
    
    @Override
    public boolean removeAll(@NotNull final Collection<?> c) {
        return this.delegate.removeAll(c);
    }
    
    @Override
    public boolean retainAll(@NotNull final Collection<?> c) {
        return this.delegate.retainAll(c);
    }
    
    @Override
    public void clear() {
        this.delegate.clear();
    }
    
    @Override
    public <U> U[] toArray(final IntFunction<U[]> generator) {
        return this.delegate.toArray(generator);
    }
    
    @Override
    public boolean removeIf(final Predicate<? super GenerativeCommand<T>> filter) {
        return this.delegate.removeIf(filter);
    }
    
    @Override
    public Spliterator<GenerativeCommand<T>> spliterator() {
        return this.delegate.spliterator();
    }
    
    @Override
    public Stream<GenerativeCommand<T>> stream() {
        return this.delegate.stream();
    }
    
    @Override
    public Stream<GenerativeCommand<T>> parallelStream() {
        return this.delegate.parallelStream();
    }
    
    @Override
    public void forEach(final Consumer<? super GenerativeCommand<T>> action) {
        this.delegate.forEach(action);
    }
    
}
