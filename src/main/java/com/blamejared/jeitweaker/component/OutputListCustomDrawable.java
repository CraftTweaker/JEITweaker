package com.blamejared.jeitweaker.component;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

public final class OutputListCustomDrawable implements IDrawable {
    
    private static final int FLEX_1 = 3;
    private static final int FLEX_2 = 12;
    private static final int FLEX_3 = 3;
    
    private static final int ROW_HEIGHT = 18;
    private static final int TEX_H = 256;
    private static final int TEX_W = 256;
    private static final int U = 94;
    private static final int V = 0;
    private static final int WIDTH = 162;
    
    private final ResourceLocation atlas;
    private final int rows;
    
    public OutputListCustomDrawable(final ResourceLocation atlas, final int rows) {
        
        this.atlas = atlas;
        this.rows = rows;
    }
    
    @Override
    public int getWidth() {
        
        return WIDTH;
    }
    
    @Override
    public int getHeight() {
        
        return ROW_HEIGHT * this.rows;
    }
    
    @Override
    public void draw(final MatrixStack matrixStack, final int xOffset, final int yOffset) {
        
        final Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.atlas);
        
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        final Matrix4f pose = matrixStack.getLast().getMatrix();
        
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        
        this.drawFlex(buffer, pose);
        
        tessellator.draw();
    }
    
    private void drawFlex(final BufferBuilder buffer, final Matrix4f pose) {
        
        final int flex2Height = this.getHeight() - FLEX_1 - FLEX_3;
        final int flex2Quantity = flex2Height / FLEX_2;
        final int difference = flex2Height - (flex2Quantity * FLEX_2);
        
        /*mutable*/ float y = 0.0F;
        
        this.quad(buffer, pose, y, FLEX_1, V);
        
        y += (float) FLEX_1;
        
        for (int i = 0; i < flex2Quantity; ++i) {
            
            this.quad(buffer, pose, y, FLEX_2, V + FLEX_1);
            y += (float) FLEX_2;
        }
        
        if (difference > 0) {
            
            this.quad(buffer, pose, y, (float) difference, V + FLEX_1);
            y += (float) difference;
        }
        
        this.quad(buffer, pose, y, FLEX_3, V + FLEX_1 + FLEX_2);
    }
    
    private void quad(final BufferBuilder buffer, final Matrix4f pose, final float y, final float h, final float v) {
        
        final float un = 1.0F / TEX_W;
        final float vn = 1.0F / TEX_H;
        
        this.vertexData(buffer, pose, 0.0F, y, (float) U * un, v * vn);
        this.vertexData(buffer, pose, 0.0F, y + h, (float) U * un, (v + h) * vn);
        this.vertexData(buffer, pose, 0.0F + (float) WIDTH, y + h, (float) (U + WIDTH) * un, (v + h) * vn);
        this.vertexData(buffer, pose, 0.0F + (float) WIDTH, y, (float) (U + WIDTH) * un, v * vn);
    }
    
    private void vertexData(final BufferBuilder buffer, final Matrix4f pose, final float x, final float y, final float u, final float v) {
        
        buffer.pos(pose, x, y, 0.0F).tex(u, v).endVertex();
    }
    
}
