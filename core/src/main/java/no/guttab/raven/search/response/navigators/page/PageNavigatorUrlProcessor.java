package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.navigators.NavigatorUrls;

import static java.lang.String.valueOf;

class PageNavigatorUrlProcessor {
    private PageNavigation pageNavigation;

    public PageNavigatorUrlProcessor(PageNavigation pageNavigation) {
        this.pageNavigation = pageNavigation;
    }

    public void process(NavigatorUrls navigatorUrls) {
        if (!pageNavigation.resultsStartsAtZero()) {
            addPaginationUrlFragment(navigatorUrls);
            addStartIndexUrlFragment(navigatorUrls);
        }
    }

    private void addPaginationUrlFragment(NavigatorUrls navigatorUrls) {
        if (pageNavigation.requestHasPagination()) {
            navigatorUrls.addVolatileUrlFragment(
                    pageNavigation.getPageRequestFieldName(),
                    valueOf(pageNavigation.getCurrentPage()));
        }
    }

    private void addStartIndexUrlFragment(NavigatorUrls navigatorUrls) {
        if (pageNavigation.requestHasStartIndexFieldName()) {
            navigatorUrls.addVolatileUrlFragment(
                    pageNavigation.getStartRequestFieldName(),
                    valueOf(pageNavigation.getCurrentResultStartIndex()));
        }
    }

}
