package no.guttab.raven.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Page {
    int resultsPerPage() default 25;
}
