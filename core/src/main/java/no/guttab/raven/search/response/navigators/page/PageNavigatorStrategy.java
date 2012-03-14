package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.SearchRequestTypeInfo;
import no.guttab.raven.search.response.navigators.NavigatorStrategy;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.apache.solr.client.solrj.response.QueryResponse;

public class PageNavigatorStrategy implements NavigatorStrategy {
    private SearchRequestTypeInfo searchRequestTypeInfo;
    private QueryResponse queryResponse;


    public PageNavigatorStrategy(SearchRequestTypeInfo searchRequestTypeInfo, QueryResponse queryResponse) {
        this.searchRequestTypeInfo = searchRequestTypeInfo;
        this.queryResponse = queryResponse;
    }

    @Override
    public void addUrlFragments(NavigatorUrls navigatorUrls) {
//        if (!onFirstPage()) {
//            if (requestHasPagination()) {
//
//            }
//        }
    }

    private boolean requestHasPagination() {
        return searchRequestTypeInfo.getResultsPerPage() != null;
    }

    private boolean onFirstPage() {
        return queryResponse.getResults().getStart() == 0;
    }

    @Override
    public void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators) {

    }
}
