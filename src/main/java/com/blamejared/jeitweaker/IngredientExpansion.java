package com.blamejared.jeitweaker;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.data.*;
import net.minecraft.item.crafting.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.item.IIngredient")
public class IngredientExpansion {
    
    @ZenCodeType.Method
    public static IIngredient withJEIText(IIngredient _this, String text) {
        return new MCIngredientWithJEIText(_this, text);
    }
    
    @ZenRegister
    @ZenCodeType.Name("mods.jei.item.MCIngredientWithJEIText")
    public static final class MCIngredientWithJEIText implements IIngredient {
        private final IIngredient base;
        private final String text;
    
        public MCIngredientWithJEIText(IIngredient base, String text) {
            this.base = base;
            this.text = text;
        }
    
        @ZenCodeType.Getter("text")
        public String getText() {
            return text;
        }
    
        @Override
        @ZenCodeType.Method
        public boolean matches(IItemStack stack) {
            return base.matches(stack);
        }
    
        @Override
        public Ingredient asVanillaIngredient() {
            return base.asVanillaIngredient();
        }
    
        @Override
        @ZenCodeType.Method
        public IItemStack getRemainingItem(IItemStack stack) {
            return base.getRemainingItem(stack);
        }
    
        @Override
        @ZenCodeType.Getter("commandString")
        public String getCommandString() {
            return base.getCommandString();
        }
    
        @Override
        @ZenCodeType.Getter("items")
        public IItemStack[] getItems() {
            return base.getItems();
        }
    
        @Override
        @ZenCodeType.Caster(implicit = true)
        public MapData asMapData() {
            return base.asMapData();
        }
    
        @Override
        @ZenCodeType.Caster(implicit = true)
        public IData asIData() {
            return base.asIData();
        }
    }
}
