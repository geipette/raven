package no.guttab.raven.search.query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.FacetField;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;

public class FacetFieldQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      doForEachAnnotatedFieldOn(queryInput, FacetField.class, new AnnotatedFieldCallback() {
         @Override
         public void doFor(Field field, Annotation annotation) {
            solrQuery.setFacet(true);
            solrQuery.addFacetField(getIndexFieldName(field));
         }
      });
   }

}
