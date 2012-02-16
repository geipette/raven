package no.guttab.raven.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.FacetField;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.FacetParams;

import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;

class FacetFieldAnnotatedFieldCallback implements AnnotatedFieldCallback<FacetField> {
   private final SolrQuery solrQuery;

   public FacetFieldAnnotatedFieldCallback(SolrQuery solrQuery) {
      this.solrQuery = solrQuery;
   }

   @Override
   public void doFor(Field field, FacetField annotation) {
      solrQuery.setFacet(true);
      String indexFieldName = getIndexFieldName(field);
      solrQuery.addFacetField(indexFieldName);
      setFacetMinCountIfNecessary(annotation, indexFieldName, solrQuery);
   }

   private void setFacetMinCountIfNecessary(FacetField facetField, String indexFieldName, SolrQuery solrQuery) {
      if (shouldSetFacetMinCount(facetField)) {
         solrQuery.set(resolveFacetMinCountParameterKey(indexFieldName), facetField.minCount());
      }
   }

   private boolean shouldSetFacetMinCount(FacetField facetField) {
      return facetField.minCount() >= 0;
   }

   private String resolveFacetMinCountParameterKey(String indexFieldName) {
      return "f." + indexFieldName + '.' + FacetParams.FACET_MINCOUNT;
   }

}
