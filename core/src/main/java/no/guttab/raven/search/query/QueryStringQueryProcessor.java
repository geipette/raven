package no.guttab.raven.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.Query;
import no.guttab.raven.reflection.FieldUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.annotations.AnnotationUtils.doForFirstAnnotatedFieldOn;

public class QueryStringQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      doForFirstAnnotatedFieldOn(queryInput, Query.class, new AnnotatedFieldCallback<Query>() {
         @Override
         public void doFor(Field field, Query annotation) {
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
