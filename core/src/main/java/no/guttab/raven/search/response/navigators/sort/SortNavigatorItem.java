package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.response.navigators.NavigatorItem;

public class SortNavigatorItem implements NavigatorItem {
    private String name;
    private String url;
    private String sortCriteria;
    private String deselectUrl;

    public SortNavigatorItem(String name, String sortCriteria, String url) {
        this.name = name;
        this.url = url;
        this.sortCriteria = sortCriteria;
    }

    public SortNavigatorItem(String name, String sortCriteria, String url, String deselectUrl) {
        this.name = name;
        this.url = url;
        this.sortCriteria = sortCriteria;
        this.deselectUrl = deselectUrl;
    }


    public String getSortCriteria() {
        return sortCriteria;
    }

    @Override
    public String getName() {
        return name;
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
