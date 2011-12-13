package no.guttab.raven.search.query;

import org.apache.solr.client.solrj.SolrQuery;

public interface QueryProcessor {
   void buildQuery(Object queryInput, SolrQuery solrQuery);
}
