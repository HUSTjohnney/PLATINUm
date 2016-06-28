package it.uniroma3.epsl2.framework.microkernel.annotation.framework.lifcycle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Post Construct method for completing the initialization 
 * of an instance within the EPSL2 environment
 * 
 * @author anacleto
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostConstruct {

}
