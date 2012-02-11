package no.guttab.raven.search.response.content;

import no.guttab.raven.search.response.ResponseProcessor;
import no.guttab.raven.search.response.SearchResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentContentResponseProcessor<T> implements ResponseProcessor<T> {
   private static final Logger log = LoggerFactory.getLogger(DocumentContentResponseProcessor.class);
   private DocumentBuilder<T> documentFactory;

   public DocumentContentResponseProcessor(DocumentBuilder<T> documentFactory) {
      this.documentFactory = documentFactory;
   }

   @Override
   public void processResponse(QueryResponse queryResponse, SearchResponse<T> response) {
      SolrDocumentList documentList = getSolrDocumentListFor(queryResponse);
      for (SolrDocument document : documentList) {
         addDocumentTo(response, document);
      }
   }

   private SolrDocumentList getSolrDocumentListFor(QueryResponse queryResponse) {
      return (SolrDocumentList) queryResponse.getResponse().get("response");
   }

   private void addDocumentTo(SearchResponse<T> response, SolrDocument document) {
      response.addDocument(documentFactory.buildDocument(document));
   }

}
