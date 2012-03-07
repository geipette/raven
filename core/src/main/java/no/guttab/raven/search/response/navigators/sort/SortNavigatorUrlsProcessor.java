package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.response.navigators.NavigatorUrls;

import static org.apache.commons.lang3.StringUtils.isEmpty;

class SortNavigatorUrlsProcessor {
    private String sortValue;
    private String sortFieldName;

    public SortNavigatorUrlsProcessor(String sortFieldName, String sortValue) {
        this.sortFieldName = sortFieldName;
        this.sortValue = sortValue;
    }

    public void process(NavigatorUrls navigatorUrls) {
        if (sortFieldName_And_SortValue_ContainsData()) {
            navigatorUrls.addUrlFragment(sortFieldName, sortValue);
        }
    }

    private boolean sortFieldName_And_SortValue_ContainsData() {
        return !(isEmpty(sortValue) || isEmpty(sortFieldName));
    }
}
