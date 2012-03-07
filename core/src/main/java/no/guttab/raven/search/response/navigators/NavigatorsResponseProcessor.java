package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.QueryResponseHeaderParams;
import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.filter.FilterQueries;
import no.guttab.raven.search.response.MutableSearchResponse;
import no.guttab.raven.search.response.ResponseProcessor;
import no.guttab.raven.search.response.navigators.select.SelectNavigatorStrategy;
import no.guttab.raven.search.response.navigators.sort.SortNavigatorStrategy;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.ArrayList;
import java.util.List;

import static no.guttab.raven.search.filter.FilterQueries.filterQueriesFor;

public class NavigatorsResponseProcessor<T> implements ResponseProcessor<T> {
    private SearchRequestTypeInfo searchRequestTypeInfo;
    private Class<T> responseType;

    public NavigatorsResponseProcessor(SearchRequestTypeInfo searchRequestTypeInfo, Class<T> responseType) {
        this.searchRequestTypeInfo = searchRequestTypeInfo;
        this.responseType = responseType;
    }

    @Override
    public void processResponse(final QueryResponse queryResponse, final MutableSearchResponse<T> response) {
        response.setNavigators(buildFor(queryResponse));
    }

    private Navigators buildFor(QueryResponse queryResponse) {
        final List<NavigatorStrategy> navigatorStrategies = initDefaultStrategies(queryResponse);
        final NavigatorUrls navigatorUrls = buildNavigatorUrls(navigatorStrategies);
        return buildNavigators(navigatorStrategies, navigatorUrls);
    }

    private List<NavigatorStrategy> initDefaultStrategies(QueryResponse queryResponse) {
        QueryResponseHeaderParams headerParams = new QueryResponseHeaderParams(queryResponse.getResponseHeader());
        FilterQueries filterQueries = filterQueriesFor(headerParams);
        List<NavigatorStrategy> navigatorStrategies = new ArrayList<NavigatorStrategy>();
        navigatorStrategies.add(new SelectNavigatorStrategy(filterQueries, queryResponse.getFacetFields()));
        navigatorStrategies.add(new SortNavigatorStrategy(searchRequestTypeInfo, responseType, headerParams));
        return navigatorStrategies;
    }

    private NavigatorUrls buildNavigatorUrls(List<NavigatorStrategy> navigatorStrategies) {
        final NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestTypeInfo);
        for (NavigatorStrategy navigatorStrategy : navigatorStrategies) {
            navigatorStrategy.addUrlFragments(navigatorUrls);
        }
        return navigatorUrls;
    }

    private Navigators buildNavigators(List<NavigatorStrategy> navigatorStrategies, NavigatorUrls navigatorUrls) {
        final Navigators navigators = new Navigators();
        for (NavigatorStrategy navigatorStrategy : navigatorStrategies) {
            navigatorStrategy.addNavigators(navigatorUrls, navigators);
        }
        return navigators;
    }

}
