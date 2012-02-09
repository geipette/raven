package no.guttab.raven.search.query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.SearchRequest;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.FacetParams;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;

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
      doForEachAnnotatedFieldOn(queryInput, FacetField.class, new AnnotatedFieldCallback() {
         @Override
         public void doFor(Field field, Annotation annotation) {
            solrQuery.setFacet(true);
            String indexFieldName = getIndexFieldName(field);
            solrQuery.addFacetField(indexFieldName);
            setFacetMinCountIfNecessary((FacetField) annotation, indexFieldName, solrQuery);
         }
      });
   }

   private void setFacetMinCountIfNecessary(FacetField facetField, String indexFieldName, SolrQuery solrQuery) {
      if (shouldSetFacetMinCount(facetField)) {
         solrQuery.set(resolveFacetMinCountParameterKey(indexFieldName), facetField.minCount());
      }
   }

   private String resolveFacetMinCountParameterKey(String indexFieldName) {
      return "f." + indexFieldName + '.' + FacetParams.FACET_MINCOUNT;
   }

   private boolean shouldSetFacetMinCount(SearchRequest facetSettings) {
      return facetSettings != null && facetSettings.facetMinCount() >= 0;
   }

   private boolean shouldSetFacetMinCount(FacetField facetField) {
      return facetField.minCount() >= 0;
   }

}
