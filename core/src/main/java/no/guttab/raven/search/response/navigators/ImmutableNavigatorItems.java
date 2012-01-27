package no.guttab.raven.search.response.navigators;

import java.util.Collections;
import java.util.List;

public class ImmutableNavigatorItems<T extends NavigatorItem> implements NavigatorItems<T> {
   private List<T> items;
   private List<T> selectedItems;

   public ImmutableNavigatorItems(List<T> items, List<T> selectedItems) {
      this.items = items;
      this.selectedItems = selectedItems;
   }

   @Override
   public List<T> getItems() {
      return Collections.unmodifiableList(items);
   }

   @Override
   public List<T> getSelectedItems() {
      return Collections.unmodifiableList(selectedItems);
   }

}
