package no.guttab.raven.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

public class SearchServer {
   private SearchResource searchResource;

   public SearchServer(SearchResource searchResource) {
      this.searchResource = searchResource;
   }

   public QueryResponse search(SolrQuery solrQuery) {
      try {
         return searchResource.getServer().query(solrQuery);
      } catch (SolrServerException e) {
         throw new SearchServerException(e);
      }
   }

   public static class SearchServerException extends RuntimeException {
      public SearchServerException(SolrServerException e) {
         super(e);
      }
   }
}
