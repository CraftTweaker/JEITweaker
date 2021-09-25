package com.blamejared.jeitweaker.zen.component;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import org.openzen.zencode.java.ZenCodeType;

@Document("mods/JEITweaker/Component/JeiDrawable")
@FunctionalInterface
@ZenCodeType.Name("mods.jei.component.JeiDrawable")
@ZenRegister
public interface JeiDrawable {
    
    IDrawable getDrawable(final IGuiHelper helper);
}
