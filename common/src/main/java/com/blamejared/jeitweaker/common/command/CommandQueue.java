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

public final class CommandQueue<T> implements Queue<JeiCommand<T>> {
    private final Queue<JeiCommand<T>> delegate;
    
    private CommandQueue(final Queue<JeiCommand<T>> delegate) {
        this.delegate = delegate;
    }
    
    static <T> CommandQueue<T> of() {
        return new CommandQueue<>(Collections.checkedQueue(new ArrayDeque<>(), GenericUtil.uncheck(JeiCommand.class)));
    }
    
    public void runCommands(final T argument) {
        JeiCommand<T> head;
        while ((head = this.poll()) != null) {
            SafeJeiCommandManager.safeOf(head).execute(argument);
        }
        assert this.isEmpty();
    }
    
    @Override
    public boolean add(final JeiCommand<T> tJeiCommand) {
        return this.delegate.add(tJeiCommand);
    }
    
    @Override
    public boolean offer(final JeiCommand<T> tJeiCommand) {
        return this.delegate.offer(tJeiCommand);
    }
    
    @Override
    public JeiCommand<T> remove() {
        return this.delegate.remove();
    }
    
    @Override
    public JeiCommand<T> poll() {
        return this.delegate.poll();
    }
    
    @Override
    public JeiCommand<T> element() {
        return this.delegate.element();
    }
    
    @Override
    public JeiCommand<T> peek() {
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
    public Iterator<JeiCommand<T>> iterator() {
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
    public boolean addAll(@NotNull final Collection<? extends JeiCommand<T>> c) {
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
    public boolean removeIf(final Predicate<? super JeiCommand<T>> filter) {
        return this.delegate.removeIf(filter);
    }
    
    @Override
    public Spliterator<JeiCommand<T>> spliterator() {
        return this.delegate.spliterator();
    }
    
    @Override
    public Stream<JeiCommand<T>> stream() {
        return this.delegate.stream();
    }
    
    @Override
    public Stream<JeiCommand<T>> parallelStream() {
        return this.delegate.parallelStream();
    }
    
    @Override
    public void forEach(final Consumer<? super JeiCommand<T>> action) {
        this.delegate.forEach(action);
    }
    
}
