package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.filter.FilterQueries;
import no.guttab.raven.search.response.navigators.page.PageAnnotationConfig;
import no.guttab.raven.search.response.navigators.page.PageNavigatorStrategy;
import no.guttab.raven.search.response.navigators.select.SelectNavigatorStrategy;
import no.guttab.raven.search.response.navigators.sort.SortAnnotationConfig;
import no.guttab.raven.search.response.navigators.sort.SortNavigatorStrategy;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

import static java.util.Arrays.asList;

public class DefaultNavigatorStrategyProvider implements NavigatorStrategyProvider {
    private Class<?> responseDocumentType;
    private Class<?> requestType;

    public DefaultNavigatorStrategyProvider(Class<?> requestType, Class<?> responseDocumentType) {
        this.requestType = requestType;
        this.responseDocumentType = responseDocumentType;
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
                new SortNavigatorStrategy(sortConfig(), responseDocumentType, headerParams),
                new PageNavigatorStrategy(pageConfig(), queryResponse)
        );
    }

    private PageAnnotationConfig pageConfig() {
        return new PageAnnotationConfig(requestType);
    }

    private SortAnnotationConfig sortConfig() {
        return new SortAnnotationConfig(requestType);
    }
}
