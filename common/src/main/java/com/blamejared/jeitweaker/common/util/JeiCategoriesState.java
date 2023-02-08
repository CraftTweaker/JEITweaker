package com.blamejared.jeitweaker.common.util;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class JeiCategoriesState {
    public record JeiCategoryState(ResourceLocation id, Visibility visibility) implements Comparable<JeiCategoryState> {
        private enum Visibility {
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
        
        private static final Comparator<JeiCategoryState> COMPARATOR = Comparator.comparing(JeiCategoryState::visibility)
                .thenComparing(JeiCategoryState::id, Comparator.comparing(ResourceLocation::getNamespace).thenComparing(ResourceLocation::getPath));
        
        public static JeiCategoryState ofVisible(final ResourceLocation id) {
            return of(id, Visibility.VISIBLE);
        }
        
        public static JeiCategoryState ofHidden(final ResourceLocation id) {
            return of(id, Visibility.HIDDEN);
        }
        
        private static JeiCategoryState of(final ResourceLocation id, final Visibility visibility) {
            return new JeiCategoryState(Objects.requireNonNull(id, "id"), visibility);
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
    
    private static final Supplier<JeiCategoriesState> INSTANCE = Suppliers.memoize(JeiCategoriesState::new);
    
    private final SortedSet<JeiCategoryState> states;
    
    private JeiCategoriesState() {
        this.states = new TreeSet<>();
    }
    
    public static JeiCategoriesState get() {
        return INSTANCE.get();
    }
    
    public void registerStates(final Set<JeiCategoryState> states) {
        this.states.clear();
        this.states.addAll(states);
    }
    
    public Stream<JeiCategoryState> knownCategoryStates() {
        return this.states.stream();
    }
    
}
