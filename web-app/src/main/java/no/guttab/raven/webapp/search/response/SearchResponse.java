package no.guttab.raven.webapp.search.response;

import org.apache.solr.client.solrj.SolrResponse;

public interface SearchResponse {
   Navigators getNavigators();
   SolrResponse getSolrResponse();

}
