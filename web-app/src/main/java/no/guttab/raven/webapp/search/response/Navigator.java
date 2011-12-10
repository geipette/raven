package no.guttab.raven.webapp.search.response;

import java.util.List;

public interface Navigator<T extends NavigatorItem> {
   List<T> getItems();

   boolean isSelected();

   T selectedItem();
}
