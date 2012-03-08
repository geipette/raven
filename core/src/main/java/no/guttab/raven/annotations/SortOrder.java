package no.guttab.raven.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface SortOrder {
    String value();

    String sortCriteria();
}
