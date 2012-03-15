package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.filter.FilterQueries;
import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.navigators.NavigatorStrategy;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import no.guttab.raven.search.response.navigators.QueryResponseHeaderParams;


public class SortNavigatorStrategy implements NavigatorStrategy {

    private QueryResponseHeaderParams headerParams;
    private SortConfig sortConfig;
    private Class<?> responseDocumentType;

    public SortNavigatorStrategy(
            SortConfig sortConfig,
            Class<?> responseDocumentType,
            QueryResponseHeaderParams headerParams) {
        this.sortConfig = sortConfig;
        this.responseDocumentType = responseDocumentType;
        this.headerParams = headerParams;
    }

    @Override
    public void addUrlFragments(NavigatorUrls navigatorUrls) {
        new SortNavigatorUrlsProcessor(sortConfig.getSortRequestFieldName(), headerParams.getSort()).process(navigatorUrls);
    }

    @Override
    public void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators) {
        FilterQueries filterQueries = headerParams.getFilterQueries();
        SortNavigation navigation = new SortNavigation(
                sortConfig.getSortRequestFieldName(), headerParams.getSort(), navigatorUrls, filterQueries);
        navigators.addNavigator(new SortNavigatorBuilder(responseDocumentType, navigation).build());
    }
}
