package no.guttab.raven.search.query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import no.guttab.raven.annotations.AnnotationsWithCallback;
import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.FacetFieldMode;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.annotations.FilterQueryCriteriaBuilder;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.annotations.SearchAnnotationUtils.getFacetFieldMode;
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

   private FilterQuery getFilterQueryAnnotation(Map<Class<? extends Annotation>, ? extends Annotation> annotations) {
      FilterQuery filterQueryAnnotation = (FilterQuery) annotations.get(FilterQuery.class);
      if (filterQueryAnnotation == null) {
         filterQueryAnnotation = new DefaultFilterQueryAnnotation();
      }
      return filterQueryAnnotation;
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

      return buildFilterQueryForCriterias(field, queryCriterias);
   }

   private String buildFilterQueryForCriterias(Field field, Collection<String> queryCriterias) {
      final FacetFieldMode facetFieldMode = getFacetFieldMode(field);

      final StringBuilder criteria = new StringBuilder();
      appendCriterias(criteria, queryCriterias, facetFieldMode);
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

   private void appendCriterias(StringBuilder criteria, Collection<String> queryCriterias, FacetFieldMode facetFieldMode) {
      for (String queryCriteria : queryCriterias) {
         if (criteria.length() > 0) {
            switch (facetFieldMode) {
               case AND:
                  criteria.append(" AND ");
                  break;
               case OR:
                  criteria.append(" OR ");
                  break;
            }
         }
         criteria.append(queryCriteria);
      }
   }

   @SuppressWarnings({"unchecked"})
   private Collection<String> buildQueryCriterias(FilterQuery filterQuery, Object fieldValue) {
      try {
         FilterQueryCriteriaBuilder criteriaBuilder = filterQuery.queryCriteriaBuilder().newInstance();
         return criteriaBuilder.buildQueryCriterias(fieldValue);
      } catch (InstantiationException e) {
         log.error("Could not instantiate class. FilterQuery skipped.", e);
      } catch (IllegalAccessException e) {
         log.error("Could not instantiate class. FilterQuery skipped.", e);
      }
      return null;
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
      public Class<? extends Annotation> annotationType() {
         return FilterQuery.class;
      }
   }

}
