package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigator;

import java.util.ArrayList;
import java.util.List;

public class PageNavigator implements Navigator<PageNavigatorItem> {
    private List<PageNavigatorItem> navigatorItems = new ArrayList<PageNavigatorItem>();
    private PageNavigation pageNavigation;

    public PageNavigator(PageNavigation pageNavigation) {
        this.pageNavigation = pageNavigation;
        initializeNavigatorItems();
    }

    private void initializeNavigatorItems() {
        for (int i = 1; i <= pageNavigation.getNumberOfPages(); i++) {
            navigatorItems.add(new PageNavigatorItem(i));
        }
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
    public PageNavigatorItem getFirstSelectedItem() {
        return null;
    }

    @Override
    public List<PageNavigatorItem> getItems() {
        return navigatorItems;
    }

    @Override
    public List<PageNavigatorItem> getSelectedItems() {
        return null;
    }
}
