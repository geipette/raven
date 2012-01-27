package no.guttab.raven.search.response.navigators;

import java.util.List;

public interface NavigatorItems<T extends NavigatorItem> {
   List<T> getItems();

   List<T> getSelectedItems();
}
