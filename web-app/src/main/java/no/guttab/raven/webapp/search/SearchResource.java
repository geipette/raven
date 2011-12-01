package no.guttab.raven.webapp.search;

import org.apache.solr.client.solrj.SolrServer;

public interface SearchResource {
   SolrServer getServer();
}
