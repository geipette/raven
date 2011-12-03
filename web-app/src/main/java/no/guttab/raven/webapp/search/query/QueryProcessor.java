package no.guttab.raven.webapp.search.query;

import org.apache.solr.client.solrj.SolrQuery;

public interface QueryProcessor {
   void buildQuery(Object queryInput, SolrQuery solrQuery);
}
