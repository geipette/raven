package no.guttab.raven.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static no.guttab.raven.annotations.SortDirection.ASCENDING;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface SortVariant {
   SortDirection value() default ASCENDING;

   String displayName() default "";

   String name() default "";
}
