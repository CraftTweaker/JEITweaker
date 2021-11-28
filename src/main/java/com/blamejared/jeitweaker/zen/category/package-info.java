/**
 * Hosts the ZenCode API for custom JEI categories, exposed to a script.
 *
 * <p>Due to the current structure of JeiTweaker, some plugins may need to reference script internals exposed in
 * this and other {@code zen} packages. A list of classes JeiTweaker plugins might want to reference is provided in
 * each package.</p>
 *
 * <p>Plugin writers that may want to create a new custom category might want to reference
 * {@link com.blamejared.jeitweaker.zen.category.JeiCategory} and
 * {@link com.blamejared.jeitweaker.zen.category.SimpleJeiCategory}. Other classes should be considered internal and not
 * exposed for public use.</p>
 *
 * @since 1.1.0
 */
package com.blamejared.jeitweaker.zen.category;