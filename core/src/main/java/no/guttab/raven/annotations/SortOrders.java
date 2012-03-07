package no.guttab.raven.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface SortOrders {
    SortOrder[] value() default {};
}
