package no.guttab.raven.search.response;

import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;

public class ResponseProcessorList implements ResponseProcessor {
   List<ResponseProcessor> responseProcessors;

   public ResponseProcessorList(List<ResponseProcessor> responseProcessors) {
      this.responseProcessors = responseProcessors;
   }

   @Override
   public void buildResponse(QueryResponse queryResponse, Object response) {
      for (ResponseProcessor responseProcessor : responseProcessors) {
         responseProcessor.buildResponse(queryResponse, response);
      }
   }
}
