package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.navigators.NavigatorStrategy;
import no.guttab.raven.search.response.navigators.NavigatorUrls;

public class PageNavigatorStrategy implements NavigatorStrategy {
    private final PageNavigation pageNavigation;
    private final PageNavigatorUrlProcessor pageNavigatorUrlProcessor;
    private final PageNavigator pageNavigator;

    public PageNavigatorStrategy(PageNavigation pageNavigation) {
        this.pageNavigation = pageNavigation;
        pageNavigatorUrlProcessor = new PageNavigatorUrlProcessor(pageNavigation);
        pageNavigator = new PageNavigator(pageNavigation);
    }

    @Override
    public void addUrlFragments(NavigatorUrls navigatorUrls) {
        pageNavigatorUrlProcessor.process(navigatorUrls);
    }

    @Override
    public void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators) {
        if (pageNavigation.requestHasPagination()) {
            navigators.addNavigator(pageNavigator);
        }
    }

}
