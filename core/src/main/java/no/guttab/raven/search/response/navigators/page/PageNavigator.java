package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigator;

import java.util.ArrayList;
import java.util.List;

public class PageNavigator implements Navigator<PageNavigatorItem> {
    private List<PageNavigatorItem> navigatorItems = new ArrayList<PageNavigatorItem>();
    private List<PageNavigatorItem> selectedNavigatorItems = new ArrayList<PageNavigatorItem>();

    void addNavigatorItem(PageNavigatorItem pageNavigatorItem) {
        navigatorItems.add(pageNavigatorItem);
    }

    void addSelectedNavigatorItem(PageNavigatorItem pageNavigatorItem) {
        selectedNavigatorItems.add(pageNavigatorItem);
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public boolean isSelected() {
        return !selectedNavigatorItems.isEmpty();
    }

    @Override
    public PageNavigatorItem getFirstSelectedItem() {
        return isSelected() ? selectedNavigatorItems.get(0) : null;
    }

    @Override
    public List<PageNavigatorItem> getItems() {
        return navigatorItems;
    }

    @Override
    public List<PageNavigatorItem> getSelectedItems() {
        return selectedNavigatorItems;
    }
}
