package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.navigators.NavigatorStrategy;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.apache.solr.client.solrj.response.QueryResponse;

public class PageNavigatorStrategy implements NavigatorStrategy {
    private final PageNavigation pageNavigation;
    private final PageNavigatorUrlProcessor pageNavigatorUrlProcessor;
    private final PageNavigatorBuilder pageNavigatorBuilder;

    public PageNavigatorStrategy(PageConfig pageConfig, QueryResponse queryResponse) {
        this.pageNavigation = new PageNavigation(pageConfig, queryResponse);
        pageNavigatorUrlProcessor = new PageNavigatorUrlProcessor(pageNavigation);
        pageNavigatorBuilder = new PageNavigatorBuilder(pageNavigation);
    }

    @Override
    public void addUrlFragments(NavigatorUrls navigatorUrls) {
        pageNavigatorUrlProcessor.process(navigatorUrls);
    }

    @Override
    public void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators) {
        if (pageNavigation.requestHasPagination()) {
            navigators.addNavigator(pageNavigatorBuilder.build(navigatorUrls));
        }
    }

}
