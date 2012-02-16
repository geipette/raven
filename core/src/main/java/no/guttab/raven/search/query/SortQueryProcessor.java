package no.guttab.raven.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.AnnotationUtils;
import no.guttab.raven.annotations.Sort;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.reflection.FieldUtils.getFieldValue;

public class SortQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      AnnotationUtils.doForFirstAnnotatedFieldOn(queryInput, Sort.class, new AnnotatedFieldCallback<Sort>() {
         @Override
         public void doFor(Field field, Sort annotation) {
            solrQuery.setSortField((String) getFieldValue(field, queryInput), SolrQuery.ORDER.asc);
         }
      });
   }
}
