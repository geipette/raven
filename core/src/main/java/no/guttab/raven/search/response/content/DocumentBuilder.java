package no.guttab.raven.search.response.content;

import org.apache.solr.common.SolrDocument;

public interface DocumentBuilder<T> {
   T buildDocument(SolrDocument document);

   Class<T> getDocumentType();
}
