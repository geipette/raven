package no.guttab.raven.search;

import no.guttab.raven.search.query.*;
import no.guttab.raven.search.response.ResponseBuilder;
import no.guttab.raven.search.response.ResponseProcessor;
import no.guttab.raven.search.response.content.DefaultDocumentBuilder;
import no.guttab.raven.search.response.content.DocumentBuilder;
import no.guttab.raven.search.response.content.DocumentContentResponseProcessor;
import no.guttab.raven.search.response.navigators.DefaultNavigatorStrategyProvider;
import no.guttab.raven.search.response.navigators.NavigatorStrategyProvider;
import no.guttab.raven.search.response.navigators.NavigatorsResponseProcessor;

import java.util.List;

import static java.util.Arrays.asList;

public class DefaultSearchContext<T> implements SearchContext<T> {
    private Object searchRequest;
    private Class<T> responseDocumentType;

    public DefaultSearchContext(Object searchRequest, Class<T> responseDocumentType) {
        this.searchRequest = searchRequest;
        this.responseDocumentType = responseDocumentType;
    }

    @Override
    public QueryBuilder queryBuilder() {
        return new QueryBuilder(queryProcessors());
    }

    @Override
    public ResponseBuilder<T> responseBuilder() {
        return new ResponseBuilder<T>(responseProcessors());
    }

    protected List<QueryProcessor> queryProcessors() {
        return asList(
                new FacetFieldQueryProcessor(),
                new FilterQueryProcessor(),
                new QueryStringQueryProcessor(),
                new SortQueryProcessor(),
                new PageQueryProcessor());
    }

    @SuppressWarnings({"unchecked"})
    protected List<ResponseProcessor<T>> responseProcessors() {
        return asList(
                new NavigatorsResponseProcessor<T>(requestType(searchRequest), navigatorStrategyProvider()),
                new DocumentContentResponseProcessor<T>(documentBuilder(responseDocumentType))
        );
    }

    protected <T> DocumentBuilder<T> documentBuilder(Class<T> responseDocumentType) {
        return new DefaultDocumentBuilder<T>(responseDocumentType);
    }

    protected NavigatorStrategyProvider navigatorStrategyProvider() {
        return new DefaultNavigatorStrategyProvider(requestType(searchRequest), responseDocumentType);
    }

    private Class<?> requestType(Object searchRequest) {
        return searchRequest.getClass();
    }
}
