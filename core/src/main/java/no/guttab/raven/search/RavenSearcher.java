package no.guttab.raven.search;

import no.guttab.raven.search.response.ResponseBuilder;
import no.guttab.raven.search.response.SearchResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

public class RavenSearcher<T extends SearchResponse> {
   private SearchServer searchServer;

   public RavenSearcher(SearchServer searchServer) {
      this.searchServer = searchServer;
   }

   public T search(Search<T> search) {
      SolrQuery solrQuery = search.buildQuery();
      QueryResponse queryResponse = searchServer.search(solrQuery);
      return new ResponseBuilder<T>(search).buildResponse(queryResponse);
   }

}
