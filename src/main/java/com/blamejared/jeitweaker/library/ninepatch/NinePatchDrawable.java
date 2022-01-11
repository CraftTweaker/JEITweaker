package com.blamejared.jeitweaker.library.ninepatch;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Supplier;

public final class NinePatchDrawable implements IDrawable {
    
    @FunctionalInterface
    private interface Drawer {
        
        void draw(final NinePatchDrawable drawable, final NinePatchImage image, final BufferBuilder buffer, final Matrix4f pose, final int xOffset, final int yOffset);
        
    }
    
    private static final Drawer[] DRAWERS = {
            NinePatchDrawable::drawNoneStretchingNinePatch,
            NinePatchDrawable::drawVerticallyStretchingNinePatch,
            NinePatchDrawable::drawHorizontallyStretchingNinePatch,
            NinePatchDrawable::drawBothStretchingNinePatch
    };
    
    private final int expectedWidth;
    private final int expectedHeight;
    private final float texWidth;
    private final float texHeight;
    private final Supplier<NinePatchImage> image;
    
    private NinePatchDrawable(final ResourceLocation atlas, final int u, final int v, final int width, final int height,
                              final int expectedWidth, final int expectedHeight, final int texWidth, final int texHeight) {
        
        this.expectedWidth = expectedWidth;
        this.expectedHeight = expectedHeight;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.image = Suppliers.memoize(() -> data(atlas, u, v, width, height, texWidth, texHeight));
    }
    
    public static NinePatchDrawable of(final ResourceLocation atlas, final int u, final int v, final int width, final int height, final int expectedWidth,
                                       final int expectedHeight, final int textureWidth, final int textureHeight) {
        
        return new NinePatchDrawable(atlas, u, v, width, height, expectedWidth, expectedHeight, textureWidth, textureHeight);
    }
    
    public static NinePatchDrawable of(final ResourceLocation atlas, final int u, final int v, final int width, final int height, final int expectedWidth, final int expectedHeight) {
        
        return of(atlas, u, v, width, height, expectedWidth, expectedHeight, 256, 256);
    }
    
    private static NinePatchImage data(final ResourceLocation atlas, final int u, final int v, final int width, final int height, final int texWidth, final int texHeight) {
        
        try {
            
            final NinePatchImage image = NinePatchImage.from(atlas, u, v, width, height, texWidth, texHeight);
            
            if(image.horizontal() != null && image.vertical() != null) {
                
                CraftTweakerAPI.LOGGER.error("Both horizontal and vertical 9-patch behavior is not yet supported");
            }
            
            return image;
        } catch(final InvalidNinePatchDataException e) {
            
            CraftTweakerAPI.LOGGER.error("An error occurred while loading 9-patch data texture", e);
            return null;
        }
    }
    
    @Override
    public int getWidth() {
        
        return this.expectedWidth;
    }
    
    @Override
    public int getHeight() {
        
        return this.expectedHeight;
    }
    
    @Override
    public void draw(final PoseStack poseStack, final int xOffset, final int yOffset) {
        
        final NinePatchImage image = this.image.get();
    
        if(image == null) {
            return;
        }
        
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, image.atlas());
        final Tesselator tessellator = Tesselator.getInstance();
        final BufferBuilder buffer = tessellator.getBuilder();
        final Matrix4f pose = poseStack.last().pose();
        
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        
        this.drawNinePatch(image, buffer, pose, xOffset, yOffset);
        
        tessellator.end();
    }
    
    private void drawNinePatch(final NinePatchImage image, final BufferBuilder buffer, final Matrix4f pose, final int xOffset, final int yOffset) {
        
        this.findDrawer(image).draw(this, image, buffer, pose, xOffset, yOffset);
    }
    
    private Drawer findDrawer(final NinePatchImage image) {
        
        return DRAWERS[(image.horizontal() == null ? 0 : 2) | (image.vertical() == null ? 0 : 1)];
    }
    
    private void drawNoneStretchingNinePatch(final NinePatchImage image, final BufferBuilder buffer, final Matrix4f pose, final int xOffset, final int yOffset) {
        
        this.quad(buffer, pose, xOffset, yOffset, this.expectedWidth, this.expectedHeight, image.u(), image.v(), image.width(), image.height());
    }
    
    private void drawVerticallyStretchingNinePatch(final NinePatchImage image, final BufferBuilder buffer, final Matrix4f pose, final int xOffset, final int yOffset) {
        
        final NinePatchRegion region = Objects.requireNonNull(image.vertical());
        final int ninePatchY = region.beginning();
        final int notNinePatchSize = image.height() - region.size();
        final int ninePatchDim = this.expectedHeight - notNinePatchSize;
        
        this.quad(buffer, pose, xOffset, yOffset, this.expectedWidth, ninePatchY, image.u(), image.v(), image.width(), ninePatchY);
        
        if(region.behavior() == NinePatchBehavior.STRETCH) {
            
            this.quad(buffer, pose, xOffset, yOffset + ninePatchY, this.expectedWidth, ninePatchDim, image.u(), ninePatchY, image.width(), region.size());
        } else if(region.behavior() == NinePatchBehavior.TILE) {
            
            final int quantity = ninePatchDim / region.size();
            final int difference = ninePatchDim - (quantity * region.size());
            
            /*mutable*/
            int y = yOffset + ninePatchY;
            
            for(int i = 0; i < quantity; ++i) {
                
                this.quad(buffer, pose, xOffset, y, this.expectedWidth, region.size(), image.u(), ninePatchY, image.width(), region.size());
                y += (float) region.size();
            }
            
            if(difference > 0) {
                
                this.quad(buffer, pose, xOffset, y, this.expectedWidth, difference, image.u(), ninePatchY, image.width(), region.size());
            }
        } else {
            
            throw new IllegalStateException(region.behavior().name());
        }
        
        final int regionEnd = ninePatchY + region.size();
        final int lastQuadY = ninePatchY + ninePatchDim;
        final int lastQuadSize = image.height() - regionEnd;
        this.quad(buffer, pose, xOffset, yOffset + lastQuadY, this.expectedWidth, this.expectedHeight - lastQuadY, image.u(), regionEnd + 1, image.width(), lastQuadSize);
    }
    
    private void drawHorizontallyStretchingNinePatch(final NinePatchImage image, final BufferBuilder buffer, final Matrix4f pose, final int xOffset, final int yOffset) {
        
        final NinePatchRegion region = Objects.requireNonNull(image.horizontal());
        final int ninePatchX = region.beginning();
        final int notNinePatchSize = image.width() - region.size();
        final int ninePatchDim = this.expectedWidth - notNinePatchSize;
        
        this.quad(buffer, pose, xOffset, yOffset, ninePatchX, this.expectedHeight, image.u(), image.v(), ninePatchX, image.height());
        
        if(region.behavior() == NinePatchBehavior.STRETCH) {
            
            this.quad(buffer, pose, xOffset + ninePatchX, yOffset, ninePatchDim, this.expectedHeight, ninePatchX, image.v(), region.size(), image.height());
        } else if(region.behavior() == NinePatchBehavior.TILE) {
            
            final int quantity = ninePatchDim / region.size();
            final int difference = ninePatchDim - (quantity * region.size());
            
            /*mutable*/
            int x = xOffset + ninePatchX;
            
            for(int i = 0; i < quantity; ++i) {
                
                this.quad(buffer, pose, x, yOffset, region.size(), this.expectedHeight, ninePatchX, image.v(), region.size(), image.height());
                x += (float) region.size();
            }
            
            if(difference > 0) {
                
                this.quad(buffer, pose, x, yOffset, difference, this.expectedHeight, ninePatchX, image.v(), region.size(), image.height());
            }
        } else {
            
            throw new IllegalStateException(region.behavior().name());
        }
        
        final int regionEnd = ninePatchX + region.size();
        final int lastQuadX = ninePatchX + ninePatchDim;
        final int lastQuadSize = image.width() - regionEnd;
        this.quad(buffer, pose, xOffset + lastQuadX, yOffset, this.expectedWidth - lastQuadX, this.expectedHeight, regionEnd + 1, image.v(), lastQuadSize, image.height());
    }
    
    private void drawBothStretchingNinePatch(final NinePatchImage image, final BufferBuilder buffer, final Matrix4f pose, final int xOffset, final int yOffset) {
        
        this.drawVerticallyStretchingNinePatch(image, buffer, pose, xOffset, yOffset);
        // TODO("Properly implement: for now we redirect to vertical")
    }
    
    private void quad(final BufferBuilder buffer, final Matrix4f pose, final float x, final float y, final float width, final float height,
                      final float u, final float v, final float textureWidth, final float textureHeight) {
        
        final float un = 1.0F / this.texWidth;
        final float vn = 1.0F / this.texHeight;
        
        this.vertexData(buffer, pose, x, y, u * un, v * vn);
        this.vertexData(buffer, pose, x, y + height, u * un, (v + textureHeight) * vn);
        this.vertexData(buffer, pose, x + width, y + height, (u + textureWidth) * un, (v + textureHeight) * vn);
        this.vertexData(buffer, pose, x + width, y, (u + textureWidth) * un, v * vn);
    }
    
    private void vertexData(final BufferBuilder buffer, final Matrix4f pose, final float x, final float y, final float u, final float v) {
        
        buffer.vertex(pose, x, y, 0.0F).uv(u, v).endVertex();
    }
    
}
