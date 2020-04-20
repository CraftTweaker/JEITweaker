package com.blamejared.jeitweaker.display_tests;

import com.blamejared.crafttweaker.impl.item.transformed.*;
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
public class CTIngredientRenderer implements IIngredientRenderer<CTIngredientInfo> {
    
    public static CTIngredientRenderer INSTANCE = new CTIngredientRenderer();
    
    public CTIngredientRenderer() {
    }
    
    @Override
    public void render(int xPosition, int yPosition, @Nullable CTIngredientInfo ingredient) {
        
        
        if(ingredient == null) {
            return;
        }
        
        final ItemStack stack = ingredient.getStack().getInternal();
        
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
    public List<String> getTooltip(CTIngredientInfo ingredient, ITooltipFlag tooltipFlag) {
        final ArrayList<String> out = new ArrayList<>();
        out.add("Matching items:" + ingredient.getOriginal().getItems().length);
        if(ingredient.getOriginal() instanceof MCIngredientTransformed) {
            final List<String> transformerExplanation = ((MCIngredientTransformed<?>) ingredient.getOriginal())
                    .getTransformer()
                    .explain(ingredient.getOriginal());
            if(!transformerExplanation.isEmpty()) {
                out.add("");
                out.addAll(transformerExplanation);
            }
        }
        
        return out;
    }
}
