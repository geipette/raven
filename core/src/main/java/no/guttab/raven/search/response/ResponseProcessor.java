package no.guttab.raven.search.response;

import org.apache.solr.client.solrj.response.QueryResponse;

public interface ResponseProcessor<T extends SearchResponse> {
   void processResponse(QueryResponse queryResponse, T response);
}
