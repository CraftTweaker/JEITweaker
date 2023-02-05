package com.blamejared.jeitweaker.common.ingredient;

import com.blamejared.jeitweaker.common.api.ingredient.JeiIngredientCreator;
import com.google.common.base.Suppliers;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class SimpleJeiIngredientCreator implements JeiIngredientCreator {
    private static final class FromZen implements JeiIngredientCreator.FromZen {
        FromZen() {}
        
        @Override
        public <J, Z> Creator<J, Z> of(final Z zenContent) {
            return type -> ZenOnlyJeiIngredient.of(type, zenContent);
        }
    
        @Override
        public <J, Z> Creator<J, Z> of(final Z zenContent, final UnaryOperator<Z> copier) {
            return type -> ZenOnlyJeiIngredient.of(type, zenContent, copier);
        }
    }
    
    private static final class FromJei implements JeiIngredientCreator.FromJei {
        FromJei() {}
        
        @Override
        public <J, Z> Creator<J, Z> of(final J jeiContent) {
            return type -> JeiOnlyJeiIngredient.of(type, jeiContent);
        }
    
        @Override
        public <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> copier) {
            return type -> JeiOnlyJeiIngredient.of(type, jeiContent, copier);
        }
    }
    
    private static final class FromBoth implements JeiIngredientCreator.FromBoth {
        FromBoth() {}
        
        @Override
        public <J, Z> Creator<J, Z> of(final J jeiContent, final Z zenContent) {
            return type -> BothJeiIngredient.of(type, jeiContent, zenContent);
        }
    
        @Override
        public <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> jeiCopier, final Z zenContent) {
            return type -> BothJeiIngredient.of(type, jeiContent, jeiCopier, zenContent);
        }
    
        @Override
        public <J, Z> Creator<J, Z> of(final J jeiContent, final UnaryOperator<J> jeiCopier, final Z zenContent, final UnaryOperator<Z> zenCopier) {
            return type -> BothJeiIngredient.of(type, jeiContent, jeiCopier, zenContent, zenCopier);
        }
    }
    
    private static final Supplier<JeiIngredientCreator> INSTANCE = Suppliers.memoize(SimpleJeiIngredientCreator::new);
    
    private final FromZen fromZen;
    private final FromJei fromJei;
    private final FromBoth fromBoth;
    
    private SimpleJeiIngredientCreator() {
        this.fromZen = new FromZen();
        this.fromJei = new FromJei();
        this.fromBoth = new FromBoth();
    }
    
    public static JeiIngredientCreator get() {
        return INSTANCE.get();
    }
    
    @Override
    public JeiIngredientCreator.FromZen fromZen() {
        return this.fromZen;
    }
    
    @Override
    public JeiIngredientCreator.FromJei fromJei() {
        return this.fromJei;
    }
    
    @Override
    public JeiIngredientCreator.FromBoth fromBoth() {
        return this.fromBoth;
    }
    
}
