package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.QueryResponseHeaderParams;
import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.filter.FilterQueries;
import no.guttab.raven.search.response.navigators.NavigatorStrategy;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import no.guttab.raven.search.response.navigators.Navigators;

import static no.guttab.raven.search.filter.FilterQueries.filterQueriesFor;

public class SortNavigatorStrategy implements NavigatorStrategy {

    private QueryResponseHeaderParams headerParams;
    private SearchRequestTypeInfo searchRequestTypeInfo;
    private Class<?> responseType;

    public SortNavigatorStrategy(
            SearchRequestTypeInfo searchRequestTypeInfo,
            Class<?> responseType,
            QueryResponseHeaderParams headerParams) {
        this.searchRequestTypeInfo = searchRequestTypeInfo;
        this.responseType = responseType;
        this.headerParams = headerParams;
    }

    @Override
    public void addUrlFragments(NavigatorUrls navigatorUrls) {
        new SortNavigatorUrlsProcessor(searchRequestTypeInfo.getSortFieldName(), headerParams.getSort()).process(navigatorUrls);
    }

    @Override
    public void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators) {
        FilterQueries filterQueries = filterQueriesFor(headerParams);
        SortNavigation navigation = new SortNavigation(
                searchRequestTypeInfo.getSortFieldName(), headerParams.getSort(), navigatorUrls, filterQueries);
        navigators.addNavigator(new SortNavigatorBuilder(responseType, navigation).build());
    }
}