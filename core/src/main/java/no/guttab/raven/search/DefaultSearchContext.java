package no.guttab.raven.search;

import no.guttab.raven.search.query.*;
import no.guttab.raven.search.response.ResponseBuilder;
import no.guttab.raven.search.response.ResponseProcessor;
import no.guttab.raven.search.response.content.DefaultDocumentBuilder;
import no.guttab.raven.search.response.content.DocumentBuilder;
import no.guttab.raven.search.response.content.DocumentContentResponseProcessor;
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

    protected List<QueryProcessor> queryProcessors() {
        return asList(
                new FacetFieldQueryProcessor(),
                new FilterQueryProcessor(),
                new QueryStringQueryProcessor(),
                new SortQueryProcessor());
    }

    @Override
    public ResponseBuilder<T> responseBuilder() {
        return new ResponseBuilder<T>(responseProcessors());
    }

    @SuppressWarnings({"unchecked"})
    protected List<ResponseProcessor<T>> responseProcessors() {
        return asList(
                new NavigatorsResponseProcessor<T>(searchRequestTypeInfo(searchRequest), responseDocumentType),
                new DocumentContentResponseProcessor<T>(documentBuilder(responseDocumentType))
        );
    }

    protected <T> DocumentBuilder<T> documentBuilder(Class<T> responseDocumentType) {
        return new DefaultDocumentBuilder<T>(responseDocumentType);
    }

    private SearchRequestTypeInfo searchRequestTypeInfo(Object searchRequest) {
        return new SearchRequestTypeInfo(requestType(searchRequest));
    }

    private Class<?> requestType(Object searchRequest) {
        return searchRequest.getClass();
    }
}
