package no.guttab.raven.search;

import no.guttab.raven.search.response.ResponseProcessors;
import no.guttab.raven.search.response.SearchResponse;
import no.guttab.raven.search.response.SearchResponseFactory;
import org.apache.solr.client.solrj.SolrQuery;

public interface Search<T extends SearchResponse> {
   SolrQuery buildQuery();

   SearchResponseFactory<T> getSearchResponseFactory();

   ResponseProcessors<T> getResponseProcessors();

   Object getSearchRequest();
}
