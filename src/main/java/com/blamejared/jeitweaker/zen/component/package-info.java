/**
 * Hosts the ZenCode API for JEI components, exposed to a script.
 *
 * <p>Due to the current structure of JeiTweaker, some plugins may need to reference script internals exposed in
 * this and other {@code zen} packages. A list of classes JeiTweaker plugins might want to reference is provided in
 * each package.</p>
 *
 * <p>Plugin writers may need to reference both {@link com.blamejared.jeitweaker.zen.component.RawJeiIngredient} and the
 * generic {@link com.blamejared.jeitweaker.zen.component.JeiIngredient}; refer to their documentation for more details.
 * Moreover, they might want to reference the provided expansions for additional information on how to implement them
 * in their own JeiTweaker plugins.</p>
 *
 * @since 1.1.0
 */
package com.blamejared.jeitweaker.zen.component;