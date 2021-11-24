/**
 * Hosts the main API for basic JeiTweaker plugins.
 *
 * <p>The main entry point of the API resides in the {@link com.blamejared.jeitweaker.api.JeiTweakerPluginProvider}
 * interface. Refer to its documentation for a guide on how to get started.</p>
 *
 * <p>Plugins that want to provide custom categories, on the other hand, currently <em>require</em> to interface with
 * internals. In this case, refer to the {@link com.blamejared.jeitweaker.zen.category.JeiCategory} and
 * {@link com.blamejared.jeitweaker.bridge.JeiCategoryPluginBridge} classes. This requirement will be reworked in a
 * future major API version.</p>
 *
 * @since 1.1.0
 */
package com.blamejared.jeitweaker.api;