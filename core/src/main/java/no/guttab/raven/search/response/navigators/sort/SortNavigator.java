package no.guttab.raven.search.response.navigators.sort;

import java.util.List;

import no.guttab.raven.search.response.navigators.Navigator;
import no.guttab.raven.search.response.navigators.NavigatorItems;

public class SortNavigator implements Navigator<SortNavigatorItem> {
   private NavigatorItems<SortNavigatorItem> navigatorItems;

   public SortNavigator(NavigatorItems<SortNavigatorItem> navigatorItems) {
      this.navigatorItems = navigatorItems;
   }

   @Override
   public String getDisplayName() {
      return null;
   }

   @Override
   public boolean isSelected() {
      return false;
   }

   @Override
   public SortNavigatorItem getFirstSelectedItem() {
      return navigatorItems.getSelectedItems().isEmpty() ? null : navigatorItems.getSelectedItems().get(0);
   }

   @Override
   public List<SortNavigatorItem> getItems() {
      return navigatorItems.getItems();
   }

   @Override
   public List<SortNavigatorItem> getSelectedItems() {
      return navigatorItems.getSelectedItems();
   }
}
