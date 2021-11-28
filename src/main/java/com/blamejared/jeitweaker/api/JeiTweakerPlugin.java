package com.blamejared.jeitweaker.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark {@link JeiTweakerPluginProvider}s that should be loaded by JeiTweaker automatically.
 *
 * <p>Classes annotated with this annotation are automatically discovered at runtime by JeiTweaker. Any class annotated
 * with this annotation should act as a service provider for {@link JeiTweakerPluginProvider}, meaning that it has to
 * implement the interface and provide a constructor with no arguments for automatic construction.</p>
 *
 * @since 1.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JeiTweakerPlugin {}
