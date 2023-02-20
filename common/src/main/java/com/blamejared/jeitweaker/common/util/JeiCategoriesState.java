package com.blamejared.jeitweaker.common.util;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class JeiCategoriesState {
    public interface JeiCategoryState extends Comparable<JeiCategoryState> {
        enum Visibility {
            VISIBLE("visible"),
            HIDDEN("hidden");
        
            private final String friendlyName;
        
            Visibility(final String friendlyName) {
                this.friendlyName = friendlyName;
            }
        
            @Override
            public String toString() {
                return this.friendlyName;
            }
        }
        
        @FunctionalInterface
        interface Creator extends Function<ResourceLocation, JeiCategoryState> {}
        
        ResourceLocation id();
        Visibility visibility();
        @Override String toString();
    }
    
    @SuppressWarnings("ClassCanBeRecord") // No, I want the static factory method only
    private static final class JeiCategoryStateProvider implements JeiCategoryState {
        private static final Comparator<JeiCategoryState> COMPARATOR = Comparator.comparing(JeiCategoryState::visibility)
                .thenComparing(JeiCategoryState::id, Comparator.comparing(ResourceLocation::getNamespace).thenComparing(ResourceLocation::getPath));
    
        private final ResourceLocation id;
        private final Visibility visibility;
        
        private JeiCategoryStateProvider(final ResourceLocation id, final Visibility visibility) {
            this.id = id;
            this.visibility = visibility;
        }
        
        public static JeiCategoryState ofVisible(final ResourceLocation id) {
            return of(id, Visibility.VISIBLE);
        }
        
        public static JeiCategoryState ofHidden(final ResourceLocation id) {
            return of(id, Visibility.HIDDEN);
        }
        
        private static JeiCategoryState of(final ResourceLocation id, final Visibility visibility) {
            return new JeiCategoryStateProvider(Objects.requireNonNull(id, "id"), visibility);
        }
        
        @Override
        public ResourceLocation id() {
            return this.id;
        }
        
        @Override
        public Visibility visibility() {
            return this.visibility;
        }
        
        @Override
        public int compareTo(@NotNull final JeiCategoriesState.JeiCategoryState o) {
            return COMPARATOR.compare(this, o);
        }
    
        @Override
        public String toString() {
            return "<resource:%s> (%s)".formatted(this.id(), this.visibility()); // Maybe rework to make it look better
        }
    
    }
    
    private static final class ReloadableLazyCategoryStateSortedSet implements SortedSet<JeiCategoryState> {
        private final SortedSet<JeiCategoryState> backend;
        private final SortedSet<JeiCategoryState> exposedView;
        private final AtomicReference<Supplier<Set<JeiCategoryState>>> updatedStateSupplier;
        
        ReloadableLazyCategoryStateSortedSet(final SortedSet<JeiCategoryState> backend) {
            this.backend = backend;
            this.exposedView = Collections.unmodifiableSortedSet(this.backend);
            this.updatedStateSupplier = new AtomicReference<>(null);
        }
        
        void updateState(final Supplier<Set<JeiCategoryState>> state) {
            this.updatedStateSupplier.set(Objects.requireNonNull(state, "updater"));
        }
        
        private void verifyUpdateState() {
            final Supplier<Set<JeiCategoryState>> updatedState = this.updatedStateSupplier.getAndSet(null);
            if (updatedState != null) {
                this.backend.clear();
                this.backend.addAll(updatedState.get());
            }
        }
    
        @Nullable
        @Override
        public Comparator<? super JeiCategoryState> comparator() {
            return this.exposedView.comparator();
        }
    
        @NotNull
        @Override
        public SortedSet<JeiCategoryState> subSet(final JeiCategoryState fromElement, final JeiCategoryState toElement) {
            this.verifyUpdateState();
            return this.exposedView.subSet(fromElement, toElement);
        }
    
        @NotNull
        @Override
        public SortedSet<JeiCategoryState> headSet(final JeiCategoryState toElement) {
            this.verifyUpdateState();
            return this.exposedView.headSet(toElement);
        }
    
        @NotNull
        @Override
        public SortedSet<JeiCategoryState> tailSet(final JeiCategoryState fromElement) {
            this.verifyUpdateState();
            return this.exposedView.tailSet(fromElement);
        }
    
        @Override
        public JeiCategoryState first() {
            this.verifyUpdateState();
            return this.exposedView.first();
        }
    
        @Override
        public JeiCategoryState last() {
            this.verifyUpdateState();
            return this.exposedView.last();
        }
    
        @Override
        public Spliterator<JeiCategoryState> spliterator() {
            this.verifyUpdateState();
            return this.exposedView.spliterator();
        }
    
        @Override
        public int size() {
            this.verifyUpdateState();
            return this.exposedView.size();
        }
    
        @Override
        public boolean isEmpty() {
            this.verifyUpdateState();
            return this.exposedView.isEmpty();
        }
    
        @Override
        public boolean contains(final Object o) {
            this.verifyUpdateState();
            return this.exposedView.contains(o);
        }
    
        @NotNull
        @Override
        public Iterator<JeiCategoryState> iterator() {
            this.verifyUpdateState();
            return this.exposedView.iterator();
        }
    
        @NotNull
        @Override
        public Object @NotNull [] toArray() {
            this.verifyUpdateState();
            return this.exposedView.toArray();
        }
    
        @NotNull
        @Override
        public <T> T @NotNull [] toArray(@NotNull final T @NotNull [] a) {
            this.verifyUpdateState();
            return this.exposedView.toArray(a);
        }
    
        @Override
        public boolean add(final JeiCategoryState jeiCategoryState) {
            throw new UnsupportedOperationException();
        }
    
        @Override
        public boolean remove(final Object o) {
            throw new UnsupportedOperationException();
        }
    
        @Override
        public boolean containsAll(@NotNull final Collection<?> c) {
            this.verifyUpdateState();
            return this.exposedView.containsAll(c);
        }
    
        @Override
        public boolean addAll(@NotNull final Collection<? extends JeiCategoryState> c) {
            throw new UnsupportedOperationException();
        }
    
        @Override
        public boolean retainAll(@NotNull final Collection<?> c) {
            throw new UnsupportedOperationException();
        }
    
        @Override
        public boolean removeAll(@NotNull final Collection<?> c) {
            throw new UnsupportedOperationException();
        }
    
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    
        @Override
        public <T> T[] toArray(final IntFunction<T[]> generator) {
            this.verifyUpdateState();
            return this.exposedView.toArray(generator);
        }
    
        @Override
        public boolean removeIf(final Predicate<? super JeiCategoryState> filter) {
            throw new UnsupportedOperationException();
        }
    
        @Override
        public Stream<JeiCategoryState> stream() {
            this.verifyUpdateState();
            return this.exposedView.stream();
        }
    
        @Override
        public Stream<JeiCategoryState> parallelStream() {
            this.verifyUpdateState();
            return this.exposedView.parallelStream();
        }
    
        @Override
        public void forEach(final Consumer<? super JeiCategoryState> action) {
            this.verifyUpdateState();
            this.exposedView.forEach(action);
        }
    
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof ReloadableLazyCategoryStateSortedSet set)) return false;
            
            this.verifyUpdateState();
            set.verifyUpdateState();
            return this.backend.equals(set.backend);
        }
    
        @Override
        public int hashCode() {
            this.verifyUpdateState();
            return this.backend.hashCode();
        }
    
        @Override
        public String toString() {
            this.verifyUpdateState();
            return this.backend.toString();
        }
    
    }
    
    private static final Supplier<JeiCategoriesState> INSTANCE = Suppliers.memoize(JeiCategoriesState::new);
    
    private final ReloadableLazyCategoryStateSortedSet states;
    
    private JeiCategoriesState() {
        this.states = new ReloadableLazyCategoryStateSortedSet(new TreeSet<>());
    }
    
    public static JeiCategoriesState get() {
        return INSTANCE.get();
    }
    
    public void registerStatesProvider(final BiFunction<JeiCategoryState.Creator, JeiCategoryState.Creator, Set<JeiCategoryState>> states) {
        this.states.updateState(() -> states.apply(JeiCategoryStateProvider::ofVisible, JeiCategoryStateProvider::ofHidden));
    }
    
    public Stream<JeiCategoryState> knownCategoryStates() {
        return this.states.stream();
    }
    
}
