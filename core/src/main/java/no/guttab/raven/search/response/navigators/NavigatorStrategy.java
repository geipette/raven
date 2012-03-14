package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.response.Navigators;

public interface NavigatorStrategy {

    void addUrlFragments(NavigatorUrls navigatorUrls);

    void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators);

}
