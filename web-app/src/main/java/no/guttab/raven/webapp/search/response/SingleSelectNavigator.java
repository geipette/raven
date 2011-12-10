package no.guttab.raven.webapp.search.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

public class SingleSelectNavigator implements Navigator<SingleSelectNavigatorItem> {
   private FacetField facetField;
   private SingleSelectNavigatorItem selectedItem;
   private List<SingleSelectNavigatorItem> items;

   public SingleSelectNavigator(FacetField facetField, SingleSelectNavigatorItem selectedItem) {
      this.facetField = facetField;
      this.selectedItem = selectedItem;
      this.items = initItems(facetField);
   }

   private List<SingleSelectNavigatorItem> initItems(FacetField facetField) {
      final List<SingleSelectNavigatorItem> items = new ArrayList<SingleSelectNavigatorItem>();
      for (FacetField.Count count : facetField.getValues()) {
         items.add(new SingleSelectNavigatorItem(count));
      }
      return items;
   }

   public List<SingleSelectNavigatorItem> getItems() {
      return Collections.unmodifiableList(items);
   }

   @Override
   public boolean isSelected() {
      return selectedItem != null;
   }

   @Override
   public SingleSelectNavigatorItem selectedItem() {
      return null;  //To change body of implemented methods use File | Settings | File Templates.
   }

}
