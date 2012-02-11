package no.guttab.raven.search.response.content;

import no.guttab.raven.common.DefaultConstructorInstantiator;
import org.apache.solr.common.SolrDocument;

public class DefaultDocumentBuilder<T> implements DocumentBuilder<T> {
   private DefaultConstructorInstantiator<T> instantiator;

   public DefaultDocumentBuilder(Class<T> documentType) {
      this.instantiator = new DefaultConstructorInstantiator<T>(documentType);
   }

   @Override
   public T buildDocument(SolrDocument solrDocument) {
      T responseDocument = instantiator.newInstance();
      processDocument(solrDocument, responseDocument);
      return responseDocument;
   }

   private void processDocument(SolrDocument solrDocument, T responseDocument) {

   }
}
