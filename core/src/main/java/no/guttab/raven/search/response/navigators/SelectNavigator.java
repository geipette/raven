package no.guttab.raven.search.response.navigators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

import static org.springframework.util.CollectionUtils.isEmpty;

public class SelectNavigator implements Navigator<SelectNavigatorItem> {
   private final FacetField facetField;
   private final List<SelectNavigatorItem> items;
   private final List<SelectNavigatorItem> selectedItems;

   public SelectNavigator(FacetField facetField) {
      this(facetField, new ArrayList<SelectNavigatorItem>(), new ArrayList<SelectNavigatorItem>());
   }

   public SelectNavigator(FacetField facetField,
                          List<SelectNavigatorItem> items,
                          List<SelectNavigatorItem> selectedItems) {
      this.facetField = facetField;
      this.items = items;
      this.selectedItems = selectedItems;
   }

   @Override
   public String getDisplayName() {
      return facetField.getName();
   }

   @Override
   public List<SelectNavigatorItem> getItems() {
      return Collections.unmodifiableList(items);
   }

   @Override
   public boolean isSelected() {
      return !isEmpty(selectedItems);
   }

   @Override
   public SelectNavigatorItem getFirstSelectedItem() {
      if (selectedItems == null) {
         return null;
      }
      final Iterator<SelectNavigatorItem> it = selectedItems.iterator();
      return it.hasNext() ? it.next() : null;
   }

   @Override
   public List<SelectNavigatorItem> getSelectedItems() {
      return Collections.unmodifiableList(selectedItems);
   }

}
