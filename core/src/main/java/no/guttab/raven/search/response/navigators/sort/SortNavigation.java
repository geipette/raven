package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.filter.FilterQueries;
import no.guttab.raven.search.response.navigators.Navigation;
import no.guttab.raven.search.response.navigators.NavigatorUrls;

class SortNavigation extends Navigation {
    private String sortFieldName;
    private String sortValue;

    public SortNavigation(
            String sortFieldName, String sortValue,
            NavigatorUrls navigatorUrls, FilterQueries filterQueries) {
        super(navigatorUrls, filterQueries);
        this.sortFieldName = sortFieldName;
        this.sortValue = sortValue;
    }

    public String getSortFieldName() {
        return sortFieldName;
    }

    public String getSortValue() {
        return sortValue;
    }
}
