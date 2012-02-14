package no.guttab.raven.search.response.navigators.sort;

import java.util.List;

import no.guttab.raven.search.response.navigators.Navigator;

public class SortNavigator implements Navigator<SortNavigatorItem> {
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
      return null;
   }

   @Override
   public List<SortNavigatorItem> getItems() {
      return null;
   }

   @Override
   public List<SortNavigatorItem> getSelectedItems() {
      return null;
   }
}
