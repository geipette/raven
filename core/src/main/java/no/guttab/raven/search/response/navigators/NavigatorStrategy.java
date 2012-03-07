package no.guttab.raven.search.response.navigators;

public interface NavigatorStrategy {

    void addUrlFragments(NavigatorUrls navigatorUrls);

    void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators);

}
