package no.guttab.raven.webapp.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

public class SearchServer {
   private SearchResource searchResource;

   public void setSearchResource(SearchResource searchResource) {
      this.searchResource = searchResource;
   }

   public QueryResponse search(SolrQuery solrQuery) throws SearchServerRuntimeException {
      try {
         return searchResource.getServer().query(solrQuery.required());
      } catch (SolrServerException e) {
         throw new SearchServerRuntimeException(e);
      }
   }

   private class SearchServerRuntimeException extends RuntimeException {
      public SearchServerRuntimeException(SolrServerException e) {
         super(e);
      }
   }
}
