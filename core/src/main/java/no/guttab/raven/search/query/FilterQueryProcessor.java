package no.guttab.raven.search.query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import no.guttab.raven.annotations.AnnotationsWithCallback;
import no.guttab.raven.annotations.CombineOperator;
import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.annotations.FilterQueryCriteriaBuilder;
import no.guttab.raven.reflection.ClassUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static no.guttab.raven.reflection.FieldUtils.getFieldValue;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.core.annotation.AnnotationUtils.getDefaultValue;

public class FilterQueryProcessor implements QueryProcessor {
   private static final Logger log = LoggerFactory.getLogger(FilterQueryProcessor.class);

   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      doForEachAnnotatedFieldOn(queryInput, new FilterQueryAnnotationsWithCallback(queryInput, solrQuery));
   }

   private String buildFilterQuery(FilterQuery filterQuery, Object queryInput, Field field) {
      Object fieldValue = getFieldValue(field, queryInput);
      if (fieldValue == null) {
         return null;
      }

      Collection<String> queryCriterias = buildQueryCriterias(filterQuery, fieldValue);
      if (CollectionUtils.isEmpty(queryCriterias)) {
         return null;
      }

      return buildFilterQueryForCriterias(field, queryCriterias, filterQuery.mode());
   }

   private FilterQuery getFilterQueryAnnotation(Map<Class<? extends Annotation>, ? extends Annotation> annotations) {
      FilterQuery filterQueryAnnotation = (FilterQuery) annotations.get(FilterQuery.class);
      if (filterQueryAnnotation == null) {
         filterQueryAnnotation = new DefaultFilterQueryAnnotation();
      }
      return filterQueryAnnotation;
   }

   private String buildFilterQueryForCriterias(
         Field field, Collection<String> queryCriterias, CombineOperator filterQueryMode) {
      final StringBuilder criteria = new StringBuilder();
      appendCriterias(criteria, queryCriterias, filterQueryMode);
      addIndexFieldName(field, queryCriterias, criteria);
      return criteria.toString();
   }

   private void addIndexFieldName(Field field, Collection<String> queryCriterias, StringBuilder criteria) {
      if (criteria.length() > 0) {
         String prefix = getIndexFieldName(field) + ':';
         if (queryCriterias.size() > 1) {
            prefix += '(';
            criteria.append(')');
         }
         criteria.insert(0, prefix);
      }
   }

   private void appendCriterias(
         StringBuilder criteria, Collection<String> queryCriterias, CombineOperator filterQueryMode) {
      for (String queryCriteria : queryCriterias) {
         if (criteria.length() > 0) {
            criteria.append(' ').append(filterQueryMode.getQueryCriteriaSeparator()).append(' ');
         }
         criteria.append(queryCriteria);
      }
   }

   @SuppressWarnings({"unchecked"})
   private Collection<String> buildQueryCriterias(FilterQuery filterQuery, Object fieldValue) {
      final FilterQueryCriteriaBuilder criteriaBuilder = ClassUtils.newInstance(filterQuery.queryCriteriaBuilder());
      return criteriaBuilder.buildQueryCriterias(fieldValue);
   }

   private class FilterQueryAnnotationsWithCallback extends AnnotationsWithCallback {
      private final Object queryInput;
      private final SolrQuery solrQuery;

      @SuppressWarnings({"unchecked"})
      public FilterQueryAnnotationsWithCallback(Object queryInput, SolrQuery solrQuery) {
         super(FilterQuery.class, FacetField.class);
         this.queryInput = queryInput;
         this.solrQuery = solrQuery;
      }

      @Override
      public void doFor(Field field, Map<Class<? extends Annotation>, ? extends Annotation> annotations) {
         final FilterQuery filterQueryAnnotation = getFilterQueryAnnotation(annotations);
         if (filterQueryAnnotation != null) {
            String filterQuery = buildFilterQuery(filterQueryAnnotation, queryInput, field);
            if (!isEmpty(filterQuery)) {
               solrQuery.addFilterQuery(filterQuery);
            }
         }
      }
   }

   @SuppressWarnings({"ClassExplicitlyAnnotation"})
   private static class DefaultFilterQueryAnnotation implements FilterQuery {
      @Override
      public Class<? extends FilterQueryCriteriaBuilder<?>> queryCriteriaBuilder() {
         //noinspection unchecked
         return (Class<? extends FilterQueryCriteriaBuilder<?>>)
               getDefaultValue(FilterQuery.class, "queryCriteriaBuilder");
      }

      @Override
      public CombineOperator mode() {
         return (CombineOperator) getDefaultValue(FilterQuery.class, "mode");
      }

      @Override
      public Class<? extends Annotation> annotationType() {
         return FilterQuery.class;
      }
   }

}
