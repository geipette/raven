package no.guttab.raven.webapp.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.annotations.AnnotatedFieldCallback;
import no.guttab.raven.webapp.annotations.FilterQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static no.guttab.raven.webapp.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.webapp.annotations.AnnotationUtils.getIndexFieldName;
import static no.guttab.raven.webapp.reflection.FieldUtils.getFieldValue;
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
      String queryCriteria = buildQueryCriteria(filterQuery, fieldValue);
      if (queryCriteria == null) {
         return null;
      }
      return getIndexFieldName(field) + ':' + queryCriteria;
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

}
