package no.guttab.raven.search.response.content;

import no.guttab.raven.search.response.MutableSearchResponse;
import no.guttab.raven.search.response.ResponseProcessor;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class DocumentContentResponseProcessor<T> implements ResponseProcessor<T> {
   private DocumentBuilder<T> documentBuilder;

   public DocumentContentResponseProcessor(DocumentBuilder<T> documentBuilder) {
      this.documentBuilder = documentBuilder;
   }

   @Override
   public void processResponse(QueryResponse queryResponse, MutableSearchResponse<T> response) {
      SolrDocumentList documentList = getSolrDocumentListFor(queryResponse);
      addAllDocuments(response, documentList);
      setResultCount(response, documentList);
   }

   private void setResultCount(MutableSearchResponse<T> response, SolrDocumentList documentList) {
      response.setResultCount(documentList.getNumFound());
   }

   private void addAllDocuments(MutableSearchResponse<T> response, SolrDocumentList documentList) {
      for (SolrDocument document : documentList) {
         addDocumentTo(response, document);
      }
   }

   private SolrDocumentList getSolrDocumentListFor(QueryResponse queryResponse) {
      return (SolrDocumentList) queryResponse.getResponse().get("response");
   }

   private void addDocumentTo(MutableSearchResponse<T> response, SolrDocument document) {
      response.addDocument(documentBuilder.buildDocument(document));
   }

}
