package no.guttab.raven.webapp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import no.guttab.raven.webapp.search.query.FilterQueryCriteriaBuilder;
import no.guttab.raven.webapp.search.query.StringValueFilterQueryCriteriaBuilder;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FilterQuery {
   boolean isFacetField() default false;
   Class<? extends FilterQueryCriteriaBuilder<?>> queryCriteriaBuilder() default StringValueFilterQueryCriteriaBuilder.class;
}
