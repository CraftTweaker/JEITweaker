package com.blamejared.jeitweaker.bridge;

import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.jeitweaker.zen.recipe.RecipeGraphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CustomTooltipRecipeGraphics implements RecipeGraphics {
    
    public static final class TipData {
        
        private final int x;
        private final int y;
        private final int w;
        private final int h;
        private final List<MCTextComponent> tips;
        
        TipData(final int x, final int y, final int w, final int h, final MCTextComponent... tips) {
            
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.tips = Arrays.asList(tips);
        }
    
        public int x() {
        
            return this.x;
        }
    
        public int y() {
        
            return this.y;
        }
    
        public int width() {
        
            return this.w;
        }
    
        public int height() {
        
            return this.h;
        }
    
        public List<MCTextComponent> tooltip() {
        
            return this.tips;
        }
    }
    
    private final List<TipData> tipData;
    
    public CustomTooltipRecipeGraphics() {
        
        this.tipData = new ArrayList<>();
    }
    
    @Override
    public void addTooltip(final int x, final int y, final int activeAreaWidth, final int activeAreaHeight, final MCTextComponent... lines) {
    
        this.tipData.add(new TipData(x, y, activeAreaWidth, activeAreaHeight, lines));
    }
    
    public List<TipData> tipData() {
        
        return this.tipData;
    }
    
}
