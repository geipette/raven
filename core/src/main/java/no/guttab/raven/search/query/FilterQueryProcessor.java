package no.guttab.raven.search.query;

import java.lang.reflect.Field;
import java.util.Collection;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
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

public class FilterQueryProcessor implements QueryProcessor {
   private static final Logger log = LoggerFactory.getLogger(FilterQueryProcessor.class);

   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      doForEachAnnotatedFieldOn(queryInput, FilterQuery.class, new AnnotatedFieldCallback<FilterQuery>() {
         @Override
         public void doFor(Field field, FilterQuery annotation) {
            String filterQuery = buildFilterQuery(annotation, queryInput, field);
            if (!isEmpty(filterQuery)) {
               solrQuery.addFilterQuery(filterQuery);
            }
         }
      });
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

}
