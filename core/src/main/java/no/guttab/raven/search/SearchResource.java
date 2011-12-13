package no.guttab.raven.search;

import org.apache.solr.client.solrj.SolrServer;

public interface SearchResource {
   SolrServer getServer();
}
