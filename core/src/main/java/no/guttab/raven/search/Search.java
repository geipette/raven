package no.guttab.raven.search;

import no.guttab.raven.search.response.ResponseProcessors;
import no.guttab.raven.search.response.content.DocumentBuilder;
import org.apache.solr.client.solrj.SolrQuery;

public interface Search<T> {
   SolrQuery buildQuery();

   DocumentBuilder<T> getDocumentFactory();

   ResponseProcessors<T> getResponseProcessors();

   Object getSearchRequest();
}
