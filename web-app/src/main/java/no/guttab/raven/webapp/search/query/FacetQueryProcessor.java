package no.guttab.raven.webapp.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.annotations.AnnotatedFieldCallback;
import no.guttab.raven.webapp.annotations.FilterQuery;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.webapp.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.webapp.annotations.AnnotationUtils.getIndexFieldName;

public class FacetQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      doForEachAnnotatedFieldOn(queryInput, FilterQuery.class, new AnnotatedFieldCallback<FilterQuery>() {
         @Override
         public void doFor(Field field, FilterQuery annotation) {
            if (annotation.isFacetField()) {
               solrQuery.setFacet(true);
               solrQuery.addFacetField(getIndexFieldName(field));
            }
         }
      });
   }
}
