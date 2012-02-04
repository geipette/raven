package no.guttab.raven.search.response;

import no.guttab.raven.search.config.SearchRequest;
import org.apache.solr.client.solrj.response.QueryResponse;

public class SearchResponseBuilder<T extends SearchResponse> {
   private SearchRequest<T> searchRequest;

   public SearchResponseBuilder(SearchRequest<T> searchRequest) {
      this.searchRequest = searchRequest;
   }

   public T buildResponse(QueryResponse queryResponse) {
      T searchResponse = searchRequest.getSearchResponseFactory().newInstance();
      executeResponseProcessors(queryResponse, searchResponse);
      return searchResponse;
   }

   private void executeResponseProcessors(QueryResponse queryResponse, T searchResponse) {
      for (ResponseProcessor<T> responseProcessor : searchRequest.getResponseProcessors()) {
         responseProcessor.processResponse(queryResponse, searchResponse);
      }
   }
}
