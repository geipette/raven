package no.guttab.raven.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import no.guttab.raven.search.query.StringValueFilterQueryCriteriaBuilder;

import static no.guttab.raven.annotations.CombineOperator.AND;

/**
 * A field annotated with this annotation can contain one or more filterValues that should
 * be filtered on.
 * <p/>
 * Examples:
 * <p/>
 * <hr/>
 * Boolean filter that only include documents with inStock = <tt>true</tt>:
 * <pre>
 * class SearchRequest {
 *    &#64;FilterQuery
 *    Boolean inStock = true;
 * }
 * </pre>
 * <hr/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface FilterQuery {
   Class<? extends FilterQueryCriteriaBuilder<?>> queryCriteriaBuilder() default StringValueFilterQueryCriteriaBuilder.class;

   CombineOperator mode() default AND;
}
