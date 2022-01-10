package com.blamejared.jeitweaker.bridge;

import net.minecraft.network.chat.Component;
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
        private final List<Component> tips;
        
        TipData(final int x, final int y, final int w, final int h, final Component... tips) {
            
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
    
        public List<Component> tooltip() {
        
            return this.tips;
        }
    }
    
    private final List<TipData> tipData;
    
    public CustomTooltipRecipeGraphics() {
        
        this.tipData = new ArrayList<>();
    }
    
    @Override
    public void addTooltip(final int x, final int y, final int activeAreaWidth, final int activeAreaHeight, final Component... lines) {
    
        this.tipData.add(new TipData(x, y, activeAreaWidth, activeAreaHeight, lines));
    }
    
    public List<TipData> tipData() {
        
        return this.tipData;
    }
    
}
