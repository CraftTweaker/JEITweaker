package com.blamejared.jeitweaker.library.ninepatch;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.IOException;

final class NinePatchImage {
    
    private final ResourceLocation atlas;
    private final int u;
    private final int v;
    private final int width;
    private final int height;
    private final NinePatchRegion horizontal;
    private final NinePatchRegion vertical;
    
    private NinePatchImage(final ResourceLocation atlas, final int u, final int v, final int width, final int height, final NinePatchRegion horizontal, final NinePatchRegion vertical) {
        
        this.atlas = atlas;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
        this.horizontal = convertToImageSpace(horizontal);
        this.vertical = convertToImageSpace(vertical);
    }
    
    public static NinePatchImage from(final ResourceLocation atlas, final int u, final int v, final int width,
                                      final int height, final int texWidth, final int texHeight) throws InvalidNinePatchDataException {
        
        try (final Resource resource = Minecraft.getInstance().getResourceManager().getResource(atlas)) {
            
            final NativeImage image = NativeImage.read(resource.getInputStream());

            return NinePatchReader.read(
                    (horizontal, vertical) -> new NinePatchImage(atlas, u + 1, v + 1, width - 2, height - 2, horizontal, vertical),
                    image,
                    u,
                    v,
                    width,
                    height,
                    texWidth,
                    texHeight
            );
        } catch (final IOException e) {
            
            throw new InvalidNinePatchDataException(e);
        }
    }
    
    private static NinePatchRegion convertToImageSpace(final NinePatchRegion region) {
        
        try {
            
            return region == null? null : NinePatchRegion.of(region.behavior(), region.beginning() - 1, region.size());
        } catch (final InvalidNinePatchDataException e) {
            
            throw new RuntimeException("Impossible", e);
        }
    }
    
    public ResourceLocation atlas() {
        
        return this.atlas;
    }
    
    public int u() {
        
        return this.u;
    }
    
    public int v() {
        
        return this.v;
    }
    
    public int width() {
        
        return this.width;
    }
    
    public int height() {
        
        return this.height;
    }
    
    public NinePatchRegion horizontal() {
        
        return this.horizontal;
    }
    
    public NinePatchRegion vertical() {
        
        return this.vertical;
    }
    
}
