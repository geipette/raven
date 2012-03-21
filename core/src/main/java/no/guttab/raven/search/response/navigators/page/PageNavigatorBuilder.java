package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.navigators.NavigatorUrls;

class PageNavigatorBuilder {
    private PageNavigation pageNavigation;

    public PageNavigatorBuilder(PageNavigation pageNavigation) {
        this.pageNavigation = pageNavigation;
    }

    public PageNavigator build(NavigatorUrls navigatorUrls) {
        final PageNavigator pageNavigator = new PageNavigator(pageNavigation.getPageRequestFieldName());
        addNavigatorItemForEachPage(navigatorUrls, pageNavigator);
        return pageNavigator;
    }

    private void addNavigatorItemForEachPage(NavigatorUrls navigatorUrls, PageNavigator pageNavigator) {
        for (int i = 1; i <= pageNavigation.getNumberOfPages(); i++) {
            addNavigatorItem(navigatorUrls, pageNavigator, i);
        }
    }

    private void addNavigatorItem(NavigatorUrls navigatorUrls, PageNavigator pageNavigator, int i) {
        final PageNavigatorItem navigatorItem = createNavigatorItem(navigatorUrls, i);
        if (pageIsSelected(i)) {
            pageNavigator.addSelectedNavigatorItem(navigatorItem);
        }
        pageNavigator.addNavigatorItem(navigatorItem);
    }

    private PageNavigatorItem createNavigatorItem(NavigatorUrls navigatorUrls, int i) {
        final String url = navigatorUrls.buildUrlFor(pageNavigation.getPageRequestFieldName(), String.valueOf(i));
        final String deselectUrl = navigatorUrls.resetUrlFor(pageNavigation.getPageRequestFieldName());
        return new PageNavigatorItem(i, url, deselectUrl);
    }

    private boolean pageIsSelected(int i) {
        return pageNavigation.getCurrentPage() > 0 ? pageNavigation.getCurrentPage() == i : i == 1;
    }
}
