package no.guttab.raven.webapp.search;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

public class UrlBasedSearchResource implements SearchResource {
   private final SolrServer server;

   public UrlBasedSearchResource(String solrServerUrl) throws MalformedURLException {
      server = new CommonsHttpSolrServer(solrServerUrl);
   }

   @Override
   public SolrServer getServer() {
      return server;
   }
}
