package no.guttab.raven.webapp.controller;

import no.guttab.raven.search.SearchServer;
import no.guttab.raven.search.query.QueryProcessor;
import no.guttab.raven.search.response.ResponseProcessor;
import no.guttab.raven.search.response.SearchResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

public class RavenSearcher<S, R extends SearchResponse> {
   private Class<R> responseType;
   private SearchServer searchServer;
   private ResponseProcessor responseProcessor;
   private QueryProcessor queryProcessor;

   public RavenSearcher(
         Class<R> responseType,
         QueryProcessor queryProcessor, ResponseProcessor responseProcessor,
         SearchServer searchServer) {
      this.responseType = responseType;
      this.searchServer = searchServer;
      this.responseProcessor = responseProcessor;
      this.queryProcessor = queryProcessor;
   }

   public R search(S searchRequest) {
      SolrQuery solrQuery = new SolrQuery();
      queryProcessor.buildQuery(searchRequest, solrQuery);
      QueryResponse queryResponse = searchServer.search(solrQuery);
      R searchResponse = instantiateResponse();
      responseProcessor.processResponse(queryResponse, searchResponse);
      return searchResponse;
   }

   public R instantiateResponse() {
      try {
         return responseType.newInstance();
      } catch (InstantiationException e) {
         throw new IllegalArgumentException(
               "ResponseType: " + responseType.getName() + " can not be instantiated. Does it have a default constructor?", e);
      } catch (IllegalAccessException e) {
         throw new IllegalArgumentException(
               "ResponseType:" + responseType.getName() + " can not be instantiated.", e);
      }
   }
}
