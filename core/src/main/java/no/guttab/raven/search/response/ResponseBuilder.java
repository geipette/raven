package no.guttab.raven.search.response;

import no.guttab.raven.search.Search;
import org.apache.solr.client.solrj.response.QueryResponse;

public class ResponseBuilder<T> {
   private Search<T> search;

   public ResponseBuilder(Search<T> search) {
      this.search = search;
   }

   public SearchResponse<T> buildResponse(QueryResponse queryResponse) {
      SearchResponse<T> searchResponse = new SearchResponse<T>();
      executeResponseProcessors(queryResponse, searchResponse);
      return searchResponse;
   }

   private void executeResponseProcessors(QueryResponse queryResponse, SearchResponse<T> searchResponse) {
      for (ResponseProcessor<T> responseProcessor : search.getResponseProcessors()) {
         responseProcessor.processResponse(queryResponse, searchResponse);
      }
   }
}
