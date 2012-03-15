package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.response.MutableSearchResponse;
import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.ResponseProcessor;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

public class NavigatorsResponseProcessor<T> implements ResponseProcessor<T> {
    private Class<?> requestType;
    private NavigatorStrategyProvider navigatorStrategyProvider;

    public NavigatorsResponseProcessor(
            Class<?> requestType,
            NavigatorStrategyProvider navigatorStrategyProvider) {
        this.requestType = requestType;
        this.navigatorStrategyProvider = navigatorStrategyProvider;
    }

    @Override
    public void processResponse(final QueryResponse queryResponse, final MutableSearchResponse<T> response) {
        response.setNavigators(buildNavigatorsFor(queryResponse));
    }

    private Navigators buildNavigatorsFor(QueryResponse queryResponse) {
        final List<NavigatorStrategy> navigatorStrategies = navigatorStrategyProvider.initStrategies(queryResponse);
        final NavigatorUrls navigatorUrls = buildNavigatorUrls(navigatorStrategies);
        return buildNavigators(navigatorStrategies, navigatorUrls);
    }

    private NavigatorUrls buildNavigatorUrls(List<NavigatorStrategy> navigatorStrategies) {
        final NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestTypeInfo());
        for (NavigatorStrategy navigatorStrategy : navigatorStrategies) {
            navigatorStrategy.addUrlFragments(navigatorUrls);
        }
        return navigatorUrls;
    }

    private SearchRequestTypeInfo searchRequestTypeInfo() {
        return new SearchRequestTypeInfo(requestType);
    }

    private Navigators buildNavigators(List<NavigatorStrategy> navigatorStrategies, NavigatorUrls navigatorUrls) {
        final Navigators navigators = new Navigators();
        for (NavigatorStrategy navigatorStrategy : navigatorStrategies) {
            navigatorStrategy.addNavigators(navigatorUrls, navigators);
        }
        return navigators;
    }

}
