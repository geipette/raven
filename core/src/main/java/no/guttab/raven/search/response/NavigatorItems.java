package no.guttab.raven.search.response;

import java.util.List;

public interface NavigatorItems<T extends NavigatorItem> {
    List<T> getItems();

    List<T> getSelectedItems();
}
