package com.blamejared.jeitweaker.common.api;

import net.minecraft.resources.ResourceLocation;

/**
 * Holds various constants related to JeiTweaker and utility to methods to operate on them.
 *
 * @since 4.0.0
 */
public final class JeiTweakerConstants {
    
    /**
     * Holds JeiTweaker's unique mod identifier.
     *
     * @since 4.0.0
     */
    @SuppressWarnings("SpellCheckingInspection") public static final String MOD_ID = "jeitweaker";
    
    private JeiTweakerConstants() {}
    
    /**
     * Constructs a new {@link ResourceLocation} with the given path and {@link #MOD_ID} as the namespace.
     *
     * <p>Clients of this API should never require to use this method, but it is nevertheless provided as a matter of
     * convenience.</p>
     *
     * @param path The path to use.
     * @return A {@link ResourceLocation}.
     * @throws NullPointerException If the {@code path} is {@code null}.
     * @throws net.minecraft.ResourceLocationException If the given path is not a valid resource location path.
     *
     * @since 4.0.0
     */
    public static ResourceLocation rl(final String path) {
        
        return new ResourceLocation(MOD_ID, path);
    }
}
