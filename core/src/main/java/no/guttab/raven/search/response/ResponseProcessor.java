package no.guttab.raven.search.response;

import org.apache.solr.client.solrj.response.QueryResponse;

public interface ResponseProcessor {
   void buildResponse(QueryResponse queryResponse, Object response);
}
