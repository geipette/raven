package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.NavigatorItem;

public class PageNavigatorItem implements NavigatorItem {
    private int page;

    public PageNavigatorItem(int page) {
        this.page = page;
    }

    @Override
    public String getName() {
        return String.valueOf(page);
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getDeselectUrl() {
        return null;
    }
}
