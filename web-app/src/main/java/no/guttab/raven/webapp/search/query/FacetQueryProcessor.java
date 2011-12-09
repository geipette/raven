package no.guttab.raven.webapp.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.annotations.AnnotatedFieldExecutor;
import no.guttab.raven.webapp.annotations.FilterQuery;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.webapp.annotations.AnnotationUtils.executeForEachAnnotatedFieldOn;
import static no.guttab.raven.webapp.annotations.AnnotationUtils.getIndexFieldName;

public class FacetQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      executeForEachAnnotatedFieldOn(queryInput, FilterQuery.class, new AnnotatedFieldExecutor<FilterQuery>() {
         @Override
         public void execute(Field field, FilterQuery annotation) {
            if (annotation.isFacetField()) {
               solrQuery.setFacet(true);
               solrQuery.addFacetField(getIndexFieldName(field));
            }
         }
      });
   }
}
