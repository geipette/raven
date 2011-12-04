package no.guttab.raven.webapp.search.response;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

public class SingleSelectFacetNavigator implements Navigator {
   private FacetField facetField;

   public SingleSelectFacetNavigator(FacetField facetField) {
      this.facetField = facetField;
      for (FacetField.Count count : facetField.getValues()) {

      }
   }

   @Override
   public String getDisplayName() {
      return null;  //To change body of implemented methods use File | Settings | File Templates.
   }

   @Override
   public String getId() {
      return null;  //To change body of implemented methods use File | Settings | File Templates.
   }

   public List<NavigatorItem> getNavigatorItems() {

      return null;
   }
}
