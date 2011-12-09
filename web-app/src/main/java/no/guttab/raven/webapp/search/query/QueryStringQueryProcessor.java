package no.guttab.raven.webapp.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.annotations.AnnotatedFieldExecutor;
import no.guttab.raven.webapp.annotations.Query;
import no.guttab.raven.webapp.reflection.FieldUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.webapp.annotations.AnnotationUtils.executeForFirstAnnotatedFieldOn;

public class QueryStringQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      executeForFirstAnnotatedFieldOn(queryInput, Query.class, new AnnotatedFieldExecutor<Query>() {
         @Override
         public void execute(Field field, Query annotation) {
            setQueryType(annotation, solrQuery);
            setQueryString(field, queryInput, solrQuery);
         }
      });
   }

   private void setQueryString(Field field, Object queryInput, SolrQuery solrQuery) {
      String queryString = String.valueOf(FieldUtils.getFieldValue(field, queryInput));
      solrQuery.setQuery(queryString);
   }

   private void setQueryType(Query annotation, SolrQuery solrQuery) {
      String annotationType = annotation.type();
      if (!StringUtils.isEmpty(annotationType)) {
         solrQuery.setQueryType(annotationType);
      }
   }
}
