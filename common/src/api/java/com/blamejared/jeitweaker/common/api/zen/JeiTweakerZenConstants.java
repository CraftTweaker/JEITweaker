package com.blamejared.jeitweaker.common.api.zen;

import com.blamejared.jeitweaker.common.api.JeiTweakerConstants;

/**
 * Holds various constants related to the ZenCode script interoperability interface.
 *
 * @since 4.0.0
 */
public final class JeiTweakerZenConstants {
    
    /**
     * Represents the root of the ZenCode module associated to the JeiTweaker mod.
     *
     * @since 4.0.0
     */
    public static final String ZEN_MODULE_ROOT = "mods." + JeiTweakerConstants.MOD_ID;
    
    /**
     * Represents the package name for all ingredient-related classes available to ZenCode scripts.
     *
     * @since 4.0.0
     */
    public static final String INGREDIENT_PACKAGE_NAME = ZEN_MODULE_ROOT + ".ingredient";
    
    private JeiTweakerZenConstants() {}
}
