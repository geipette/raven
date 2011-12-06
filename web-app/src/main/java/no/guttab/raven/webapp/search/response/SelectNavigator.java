package no.guttab.raven.webapp.search.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

public class SelectNavigator implements Navigator<SelectNavigatorItem> {
   private FacetField facetField;
   private List<SelectNavigatorItem> items;

   public SelectNavigator(FacetField facetField) {
      this.facetField = facetField;
      this.items = initItems(facetField);
   }

   private List<SelectNavigatorItem> initItems(FacetField facetField) {
      final List<SelectNavigatorItem> items = new ArrayList<SelectNavigatorItem>();
      for (FacetField.Count count : facetField.getValues()) {
         items.add(new SelectNavigatorItem(count));
      }
      return items;
   }

   public List<SelectNavigatorItem> getItems() {
      return Collections.unmodifiableList(items);
   }

   @Override
   public String getDisplayName() {
      return null;  //To change body of implemented methods use File | Settings | File Templates.
   }

   @Override
   public String getId() {
      return null;  //To change body of implemented methods use File | Settings | File Templates.
   }
}
