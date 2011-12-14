package no.guttab.raven.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.FilterQuery;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.annotations.AnnotationUtils.*;

public class FacetQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      doForEachAnnotatedFieldOn(queryInput, FilterQuery.class, new AnnotatedFieldCallback<FilterQuery>() {
         @Override
         public void doFor(Field field, FilterQuery annotation) {
            if (isFacetField(field)) {
               solrQuery.setFacet(true);
               solrQuery.addFacetField(getIndexFieldName(field));
            }
         }
      });
   }
}