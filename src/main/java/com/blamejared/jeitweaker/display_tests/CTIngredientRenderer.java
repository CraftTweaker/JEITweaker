package com.blamejared.jeitweaker.display_tests;

import com.blamejared.crafttweaker.api.item.*;
import com.mojang.blaze3d.platform.*;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.util.*;
import net.minecraft.item.*;

import javax.annotation.*;
import java.util.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CTIngredientRenderer implements IIngredientRenderer<IItemStack> {
    
    public static CTIngredientRenderer INSTANCE = new CTIngredientRenderer();
    
    private CTIngredientRenderer(){}
    
    @Override
    public void render(int xPosition, int yPosition, @Nullable IItemStack ingredient) {
        if(ingredient == null) {
            return;
        }
    
        final ItemStack stack = ingredient.getInternal();
        
        //Code stolen from ItemStackRenderer
        GlStateManager.enableDepthTest();
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer font = getFontRenderer(minecraft, ingredient);
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        itemRenderer.renderItemAndEffectIntoGUI(null, stack, xPosition, yPosition);
        itemRenderer.renderItemOverlayIntoGUI(font, stack, xPosition, yPosition, null);
        GlStateManager.disableBlend();
        RenderHelper.disableStandardItemLighting();
    }
    
    @Override
    public List<String> getTooltip(IItemStack ingredient, ITooltipFlag tooltipFlag) {
        return Collections.singletonList("Matching items: " + ingredient.getItems().length);
    }
}
