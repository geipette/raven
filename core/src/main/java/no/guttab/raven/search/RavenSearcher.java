package no.guttab.raven.search;

import no.guttab.raven.search.config.SearchRequest;
import no.guttab.raven.search.response.SearchResponse;
import no.guttab.raven.search.response.SearchResponseBuilder;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

public class RavenSearcher<T extends SearchResponse> {
   private SearchServer searchServer;

   public RavenSearcher(SearchServer searchServer) {
      this.searchServer = searchServer;
   }

   public T search(SearchRequest<T> searchRequest) {
      SolrQuery solrQuery = searchRequest.getQueryBuilder().buildQuery(searchRequest);
      QueryResponse queryResponse = searchServer.search(solrQuery);
      return new SearchResponseBuilder<T>(searchRequest).buildResponse(queryResponse);
   }

}
