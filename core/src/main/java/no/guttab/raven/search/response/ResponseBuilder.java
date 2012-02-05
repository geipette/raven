package no.guttab.raven.search.response;

import no.guttab.raven.search.Search;
import org.apache.solr.client.solrj.response.QueryResponse;

public class ResponseBuilder<T extends SearchResponse> {
   private Search<T> search;

   public ResponseBuilder(Search<T> search) {
      this.search = search;
   }

   public T buildResponse(QueryResponse queryResponse) {
      T searchResponse = search.getSearchResponseFactory().newInstance();
      executeResponseProcessors(queryResponse, searchResponse);
      return searchResponse;
   }

   private void executeResponseProcessors(QueryResponse queryResponse, T searchResponse) {
      for (ResponseProcessor<T> responseProcessor : search.getResponseProcessors()) {
         responseProcessor.processResponse(queryResponse, searchResponse);
      }
   }
}
