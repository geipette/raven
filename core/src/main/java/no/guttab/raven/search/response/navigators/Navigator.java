package no.guttab.raven.search.response.navigators;

import java.util.List;

public interface Navigator<T extends NavigatorItem> {
   List<T> getItems();

   boolean isSelected();

   T getFirstSelectedItem();

   List<T> getSelectedItems();

   String getDisplayName();
}
