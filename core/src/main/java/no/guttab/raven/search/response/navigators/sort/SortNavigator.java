package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.response.Navigator;
import no.guttab.raven.search.response.NavigatorItems;

import java.util.List;

public class SortNavigator implements Navigator<SortNavigatorItem> {
    private NavigatorItems<SortNavigatorItem> navigatorItems;

    public SortNavigator(NavigatorItems<SortNavigatorItem> navigatorItems) {
        this.navigatorItems = navigatorItems;
    }

    @Override
    public String getDisplayName() {
        return "sort";
    }

    @Override
    public boolean isSelected() {
        return !navigatorItems.getSelectedItems().isEmpty();
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
