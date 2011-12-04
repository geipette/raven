package no.guttab.raven.webapp.search.response;

import org.apache.solr.client.solrj.response.QueryResponse;

public interface ResponseProcessor {
   void buildResponse(QueryResponse queryResponse, SearchResponse response);
}
