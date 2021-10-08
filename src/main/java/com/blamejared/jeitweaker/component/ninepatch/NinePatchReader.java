package com.blamejared.jeitweaker.component.ninepatch;

import net.minecraft.client.renderer.texture.NativeImage;

final class NinePatchReader {
    
    @FunctionalInterface
    interface NinePatchCreator {
        
        NinePatchImage of(final NinePatchRegion horizontal, final NinePatchRegion vertical);
    }
    
    static NinePatchImage read(final NinePatchCreator creator, final NativeImage image, final int x, final int y,
                               final int w, final int h, final int tw, final int ty) throws InvalidNinePatchDataException {
    
        if (image.getFormat() != NativeImage.PixelFormat.RGBA) {
        
            throw new InvalidNinePatchDataException("Image must be in RGBA format");
        }
    
        final double widthScale = (double) image.getWidth() / (double) tw;
        final double heightScale = (double) image.getHeight() / (double) ty;
    
        if (Math.ceil(widthScale) != widthScale || Math.ceil(heightScale) != heightScale) {
        
            throw new InvalidNinePatchDataException("Image size is not 256 or a multiple of it: found " + image.getWidth() + "x" + image.getHeight());
        }
    
        return readScaled(creator, image, x, y, w, h, (int) widthScale, (int) heightScale);
    }
    
    private static NinePatchImage readScaled(final NinePatchCreator creator, final NativeImage image, final int x, final int y,
                                             final int w, final int h, final int sx, final int sy) throws InvalidNinePatchDataException {
        
        verifyVersion(image, x, y, sx, sy);
        
        final NinePatchRegion top = readHorizontal(image, NinePatchBehavior.STRETCH, x + 1, y, w - 2, sx, sy);
        final NinePatchRegion left = readVertical(image, NinePatchBehavior.STRETCH, x, y + 1, h - 2, sx, sy);
        final NinePatchRegion right = readVertical(image, NinePatchBehavior.TILE, x + w - 1, y + 1, h - 2, sx, sy);
        final NinePatchRegion bottom = readHorizontal(image, NinePatchBehavior.TILE, x + 1, y + h - 1, w - 2, sx, sy);
        
        return merge(creator, top, left, bottom, right);
    }
    
    private static void verifyVersion(final NativeImage image, final int x, final int y, final int sx, final int sy) throws InvalidNinePatchDataException {
        
        final int color = readColor(image, x, y, sx, sy);
        if (isTransparent(color) || (color & 0xFFFFFF) != 0xFFFF00) {
            
            throw new InvalidNinePatchDataException("Unknown 9-patch version: 0x" + Integer.toHexString(color));
        }
    }
    
    private static NinePatchRegion readHorizontal(final NativeImage image, final NinePatchBehavior type, final int bx, final int y,
                                                  final int w, final int sx, final int sy) throws InvalidNinePatchDataException {
        
        /*mutable*/ int x = bx;
        
        while (x < w && !isBlackRegion(image, x, y, sx, sy)) {
            
            ++x;
        }
        
        if (x == w) return null;
        
        final int prevX = x;
        
        while (x < w && isBlackRegion(image, x, y, sx, sy)) {
            
            ++x;
        }
        
        final NinePatchRegion region = NinePatchRegion.of(type, prevX, x - prevX);
        
        while (x < w && !isBlackRegion(image, x, y, sx, sy)) {
            
            ++x;
        }
        
        if (x != w) {
            
            throw new InvalidNinePatchDataException("Patch data has been truncated");
        }
        
        return region;
    }
    
    private static NinePatchRegion readVertical(final NativeImage image, final NinePatchBehavior type, final int bx, final int by,
                                                final int h, final int sx, final int sy) throws InvalidNinePatchDataException {
        
        /*mutable*/ int y = by;
    
        while (y < h && !isBlackRegion(image, bx, y, sx, sy)) {
            
            ++y;
        }
    
        if (y == h) return null;
    
        final int prevY = y;
    
        while (y < h && isBlackRegion(image, bx, y, sx, sy)) {
            
            ++y;
        }
    
        final NinePatchRegion region = NinePatchRegion.of(type, prevY, y - prevY);
    
        while (y < h && !isBlackRegion(image, bx, y, sx, sy)) {
            
            ++y;
        }

        if (y != h) {
        
            throw new InvalidNinePatchDataException("9-patch data has been truncated");
        }
    
        return region;
    }
    
    private static boolean isBlackRegion(final NativeImage image, final int rx, final int ry, final int sx, final int sy) throws InvalidNinePatchDataException {
        
        final int color = readColor(image, rx, ry, sx, sy);
        final boolean transparentRegion = isTransparent(color);
        final boolean blackRegion = !transparentRegion && isBlack(color);
        
        if (!blackRegion && !transparentRegion) {
            
            throw new InvalidNinePatchDataException("Invalid color for 9-patch pixel @ (" + rx + ',' + ry + "): 0x" + Integer.toHexString(color));
        }
        
        return blackRegion;
    }
    
    private static int readColor(final NativeImage image, final int rx, final int ry, final int sx, final int sy) throws InvalidNinePatchDataException {
    
        final int color = image.getPixelRGBA(rx * sx, ry * sy);
    
        for (int i = rx * sx, m = (rx + 1) * sx; i < m; ++i) {
        
            for (int j = ry * sy, n = (ry + 1) * sx; j < n; ++j) {
            
                final int subpixelColor = image.getPixelRGBA(i, j);
            
                if (subpixelColor != color) {
                
                    throw new InvalidNinePatchDataException("Multiple colors for subpixel @ (" + rx + ',' + ry + "): initial was 0x" + color);
                }
            }
        }
    
        return color;
    }
    
    private static boolean isTransparent(final int color) {
        
        return ((color >> 24) & 0xFF) == 0x00;
    }
    
    private static boolean isBlack(final int color) {
        
        return !isTransparent(color) && (color & 0xFFFFFF) == 0x000000;
    }
    
    private static NinePatchImage merge(final NinePatchCreator creator, final NinePatchRegion top, final NinePatchRegion left,
                                        final NinePatchRegion bottom, final NinePatchRegion right) throws InvalidNinePatchDataException {
        
        validateCompatibleData(top, bottom);
        validateCompatibleData(left, right);
        
        final NinePatchRegion horizontal = top == null? bottom : top;
        final NinePatchRegion vertical = left == null? right : left;
        
        return creator.of(horizontal, vertical);
    }
    
    private static void validateCompatibleData(final NinePatchRegion a, final NinePatchRegion b) throws InvalidNinePatchDataException {
        
        if (a != null && b != null) {
            
            throw new InvalidNinePatchDataException("Malformed 9-patch data: cannot both stretch and tile a section (not yet supported or an actual error)");
        }
    }
}
