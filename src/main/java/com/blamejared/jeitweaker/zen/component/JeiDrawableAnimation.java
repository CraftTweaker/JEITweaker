package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents the type of animation that an animated {@link JeiDrawable} should apply.
 *
 * @since 1.1.0
 */
@Document("mods/JEITweaker/API/Component/JeiDrawableAnimation")
@ZenCodeType.Name("mods.jei.component.JeiDrawableAnimation")
@ZenRegister
public enum JeiDrawableAnimation {
    SHOW_TOP_TO_BOTTOM(IDrawableAnimated.StartDirection.TOP, false),
    SHOW_BOTTOM_TO_TOP(IDrawableAnimated.StartDirection.BOTTOM, false),
    SHOW_LEFT_TO_RIGHT(IDrawableAnimated.StartDirection.LEFT, false),
    SHOW_RIGHT_TO_LEFT(IDrawableAnimated.StartDirection.RIGHT, false),
    HIDE_TOP_TO_BOTTOM(IDrawableAnimated.StartDirection.TOP, true),
    HIDE_BOTTOM_TO_TOP(IDrawableAnimated.StartDirection.BOTTOM, true),
    HIDE_LEFT_TO_RIGHT(IDrawableAnimated.StartDirection.LEFT, true),
    HIDE_RIGHT_TO_LEFT(IDrawableAnimated.StartDirection.RIGHT, true);
    
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
