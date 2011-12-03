package no.guttab.raven.webapp.search.response;

import org.apache.solr.client.solrj.response.QueryResponse;

public class ResponseBuilder {
   public SearchResponse buildResponse(QueryResponse queryResponse) {
      queryResponse.getFacetFields().get(0).getValues().get(0).getAsFilterQuery();
      return null;
   }
}
