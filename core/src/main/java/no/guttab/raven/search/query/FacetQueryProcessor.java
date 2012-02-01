package no.guttab.raven.search.query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.FilterQuery;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static no.guttab.raven.annotations.SearchAnnotationUtils.isFacetField;

public class FacetQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      doForEachAnnotatedFieldOn(queryInput, FilterQuery.class, new AnnotatedFieldCallback() {
         @Override
         public void doFor(Field field, Annotation annotation) {
            if (isFacetField(field)) {
               solrQuery.setFacet(true);
               solrQuery.addFacetField(getIndexFieldName(field));
            }
         }
      });
   }
}
