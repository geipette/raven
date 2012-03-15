package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.navigators.NavigatorStrategy;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.apache.solr.client.solrj.response.QueryResponse;

public class PageNavigatorStrategy implements NavigatorStrategy {
    private QueryResponse queryResponse;
    private PageConfig pageConfig;


    public PageNavigatorStrategy(PageConfig pageConfig, QueryResponse queryResponse) {
        this.pageConfig = pageConfig;
        this.queryResponse = queryResponse;
    }

    @Override
    public void addUrlFragments(NavigatorUrls navigatorUrls) {
        if (!onFirstPage()) {
            if (requestHasPagination()) {
                navigatorUrls.addVolatileUrlFragment(pageConfig.getPageRequestFieldName(), getPage());
            }
        }
    }

    @Override
    public void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators) {

    }

    private String getPage() {
        if (pageConfig.getResultsPerPage() == 0) {
            return String.valueOf(1);
        } else {
            return String.valueOf(queryResponse.getResults().getStart() % pageConfig.getResultsPerPage());
        }
    }

    private boolean requestHasPagination() {
        return pageConfig.getResultsPerPage() != null;
    }

    private boolean onFirstPage() {
        return queryResponse.getResults().getStart() == 0;
    }
}
