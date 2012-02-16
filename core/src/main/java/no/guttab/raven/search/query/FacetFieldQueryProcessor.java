package no.guttab.raven.search.query;

import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.SearchRequest;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;

public class FacetFieldQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
      handleFacetSettingsAnnotation(queryInput, solrQuery);
      handleFacetFieldAnnotations(queryInput, solrQuery);
   }

   private void handleFacetSettingsAnnotation(Object queryInput, SolrQuery solrQuery) {
      SearchRequest facetSettings = queryInput.getClass().getAnnotation(SearchRequest.class);
      if (shouldSetFacetMinCount(facetSettings)) {
         solrQuery.setFacetMinCount(facetSettings.facetMinCount());
      }
   }

   private void handleFacetFieldAnnotations(Object queryInput, final SolrQuery solrQuery) {
      doForEachAnnotatedFieldOn(queryInput, FacetField.class, new FacetFieldAnnotatedFieldCallback(solrQuery));
   }

   private boolean shouldSetFacetMinCount(SearchRequest facetSettings) {
      return facetSettings != null && facetSettings.facetMinCount() >= 0;
   }


}
