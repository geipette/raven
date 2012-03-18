package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.NavigatorItem;

public class PageNavigatorItem implements NavigatorItem {
    private int page;
    private String url;
    private String deselectUrl;

    public PageNavigatorItem(int page, String url, String deselectUrl) {
        this.page = page;
        this.url = url;
        this.deselectUrl = deselectUrl;
    }

    @Override
    public String getName() {
        return String.valueOf(page);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getDeselectUrl() {
        return deselectUrl;
    }
}
