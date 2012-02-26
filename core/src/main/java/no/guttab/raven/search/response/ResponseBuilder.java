package no.guttab.raven.search.response;

import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

public class ResponseBuilder<T> {
    private List<ResponseProcessor<T>> responseProcessors;

    public ResponseBuilder(List<ResponseProcessor<T>> responseProcessors) {
        this.responseProcessors = responseProcessors;
    }

    public SearchResponse<T> buildResponse(QueryResponse queryResponse) {
        final MutableSearchResponse<T> searchResponse = new MutableSearchResponse<T>();
        executeResponseProcessors(queryResponse, searchResponse);
        return searchResponse;
    }

    private void executeResponseProcessors(QueryResponse queryResponse, MutableSearchResponse<T> searchResponse) {
        for (ResponseProcessor<T> responseProcessor : responseProcessors) {
            responseProcessor.processResponse(queryResponse, searchResponse);
        }
    }
}
