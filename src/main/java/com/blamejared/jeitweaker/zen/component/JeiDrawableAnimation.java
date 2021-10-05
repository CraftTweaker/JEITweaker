package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEI/Component/JeiDrawableAnimation")
@ZenCodeType.Name("mods.jei.component.JeiDrawableAnimation")
@ZenRegister
public enum JeiDrawableAnimation {
    @ZenCodeType.Field SHOW_TOP_TO_BOTTOM(IDrawableAnimated.StartDirection.TOP, false),
    @ZenCodeType.Field SHOW_BOTTOM_TO_TOP(IDrawableAnimated.StartDirection.BOTTOM, false),
    @ZenCodeType.Field SHOW_LEFT_TO_RIGHT(IDrawableAnimated.StartDirection.LEFT, false),
    @ZenCodeType.Field SHOW_RIGHT_TO_LEFT(IDrawableAnimated.StartDirection.RIGHT, false),
    @ZenCodeType.Field HIDE_TOP_TO_BOTTOM(IDrawableAnimated.StartDirection.TOP, true),
    @ZenCodeType.Field HIDE_BOTTOM_TO_TOP(IDrawableAnimated.StartDirection.BOTTOM, true),
    @ZenCodeType.Field HIDE_LEFT_TO_RIGHT(IDrawableAnimated.StartDirection.LEFT, true),
    @ZenCodeType.Field HIDE_RIGHT_TO_LEFT(IDrawableAnimated.StartDirection.RIGHT, true);
    
    private final IDrawableAnimated.StartDirection direction;
    private final boolean inverted;
    
    JeiDrawableAnimation(final IDrawableAnimated.StartDirection direction, final boolean inverted) {
        
        this.direction = direction;
        this.inverted = inverted;
    }
    
    IDrawableAnimated.StartDirection direction() {
        
        return this.direction;
    }
    
    boolean inverted() {
        
        return this.inverted;
    }
}
