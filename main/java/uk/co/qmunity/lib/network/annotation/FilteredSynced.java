package uk.co.qmunity.lib.network.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Any array fields annotated with this will only sync the specified indexes.
 * 
 * @author MineMaarten
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilteredSynced{
    int index();
}
