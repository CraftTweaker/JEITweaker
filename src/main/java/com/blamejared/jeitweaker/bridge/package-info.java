/**
 * Hosts bridges that connect the ZenCode API exposed in the {@code zen} package to the internals of JeiTweaker.
 *
 * <p>Due to the current structure of JeiTweaker, some plugins may need to reference these internals. This need will be
 * removed in a future major API version. The only class considered to be public API in this package is
 * {@link com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge}.</p>
 *
 * @since 1.1.0
 */
package com.blamejared.jeitweaker.bridge;