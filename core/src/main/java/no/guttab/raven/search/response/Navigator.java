package no.guttab.raven.search.response;

import java.util.List;

public interface Navigator<T extends NavigatorItem> {
   List<T> getItems();

   boolean isSelected();

   T getSelectedItem();
}
