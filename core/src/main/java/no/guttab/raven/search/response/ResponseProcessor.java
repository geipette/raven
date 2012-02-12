package no.guttab.raven.search.response;

import org.apache.solr.client.solrj.response.QueryResponse;

public interface ResponseProcessor<T> {
   void processResponse(QueryResponse queryResponse, MutableSearchResponse<T> response);
}
