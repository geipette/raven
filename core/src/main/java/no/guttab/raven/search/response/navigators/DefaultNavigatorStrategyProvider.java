package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.filter.FilterQueries;
import no.guttab.raven.search.response.SearchRequestTypeInfo;
import no.guttab.raven.search.response.navigators.select.SelectNavigatorStrategy;
import no.guttab.raven.search.response.navigators.sort.SortNavigatorStrategy;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

import static java.util.Arrays.asList;

public class DefaultNavigatorStrategyProvider implements NavigatorStrategyProvider {
    private SearchRequestTypeInfo searchRequestTypeInfo;
    private Class<?> responseType;

    public DefaultNavigatorStrategyProvider(SearchRequestTypeInfo searchRequestTypeInfo, Class<?> responseType) {
        this.searchRequestTypeInfo = searchRequestTypeInfo;
        this.responseType = responseType;
    }

    @Override
    public List<NavigatorStrategy> initStrategies(QueryResponse queryResponse) {
        final QueryResponseHeaderParams headerParams = new QueryResponseHeaderParams(queryResponse.getResponseHeader());
        final FilterQueries filterQueries = headerParams.getFilterQueries();
        return navigatorStrategyList(queryResponse, headerParams, filterQueries);
    }

    private List<NavigatorStrategy> navigatorStrategyList(
            QueryResponse queryResponse, QueryResponseHeaderParams headerParams, FilterQueries filterQueries) {
        return asList(
                new SelectNavigatorStrategy(filterQueries, queryResponse.getFacetFields()),
                new SortNavigatorStrategy(searchRequestTypeInfo, responseType, headerParams)
        );
    }
}
