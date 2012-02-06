package no.guttab.raven.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import no.guttab.raven.search.query.StringValueFilterQueryCriteriaBuilder;

import static no.guttab.raven.annotations.FilterQueryMode.AND;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface FilterQuery {
   Class<? extends FilterQueryCriteriaBuilder<?>> queryCriteriaBuilder() default StringValueFilterQueryCriteriaBuilder.class;

   FilterQueryMode mode() default AND;
}
