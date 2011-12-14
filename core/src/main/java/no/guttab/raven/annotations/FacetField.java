package no.guttab.raven.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static no.guttab.raven.annotations.FacetFieldMode.AND;
import static no.guttab.raven.annotations.FacetFieldType.SINGLE_SELECT;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FacetField {
   FacetFieldType type() default SINGLE_SELECT;
   FacetFieldMode mode() default AND;
}
