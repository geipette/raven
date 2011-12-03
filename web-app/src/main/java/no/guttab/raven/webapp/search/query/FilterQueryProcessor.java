package no.guttab.raven.webapp.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.annotations.FilterQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class FilterQueryProcessor implements QueryProcessor {
   private static final Logger log = LoggerFactory.getLogger(FilterQueryProcessor.class);

   @Override
   public void buildQuery(Object queryInput, SolrQuery solrQuery) {
      for (Field field : queryInput.getClass().getDeclaredFields()) {
         FilterQuery filterQueryAnnotation = field.getAnnotation(FilterQuery.class);
         if (filterQueryAnnotation != null) {
            String filterQuery = buildFilterQuery(filterQueryAnnotation, queryInput, field);
            if (!isEmpty(filterQuery)) {
               solrQuery.addFilterQuery(filterQuery);
            }
         }
      }
   }

   private String buildFilterQuery(FilterQuery filterQuery, Object queryInput, Field field) {
      Object fieldValue = getFieldValue(queryInput, field);
      if (fieldValue == null) {
         return null;
      }
      String queryCriteria = buildQueryCriteria(filterQuery, fieldValue);
      if (queryCriteria == null) {
         return null;
      }
      return getIndexFieldName(filterQuery, field.getName()) + ':' + queryCriteria;
   }

   private String getIndexFieldName(FilterQuery filterQuery, String name) {
      return isEmpty(filterQuery.indexFieldName()) ? name : filterQuery.indexFieldName();
   }

   @SuppressWarnings({"unchecked"})
   private String buildQueryCriteria(FilterQuery filterQuery, Object fieldValue) {
      try {
         FilterQueryCriteriaBuilder criteriaBuilder = filterQuery.queryCriteriaBuilder().newInstance();
         return criteriaBuilder.buildQueryCriteria(fieldValue);
      } catch (InstantiationException e) {
         log.error("Could not instantiate class. FilterQuery skipped.", e);
      } catch (IllegalAccessException e) {
         log.error("Could not instantiate class. FilterQuery skipped.", e);
      }
      return null;
   }

   private Object getFieldValue(Object queryInput, Field field) {
      ReflectionUtils.makeAccessible(field);
      return ReflectionUtils.getField(field, queryInput);
   }

}
