package no.guttab.raven.search.response.content;

import java.util.Arrays;

import no.guttab.raven.search.response.MutableSearchResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DocumentContentResponseProcessorTest {

   @Mock
   private DocumentBuilder documentFactory;
   @Mock
   private QueryResponse queryResponse;
   @Mock
   private MutableSearchResponse searchResponse;
   @Mock
   private NamedList<Object> responseList;
   @Mock
   private SolrDocumentList solrDocumentList;

   @Before
   public void setUp() throws Exception {
      when(queryResponse.getResponse()).thenReturn(responseList);
      when(responseList.get("response")).thenReturn(solrDocumentList);
   }

   @SuppressWarnings({"unchecked"})
   @Test
   public void processResponse_should_not_add_documents_when_no_documents_available() throws Exception {
      setupDocumentList();
      DocumentContentResponseProcessor responseProcessor = new DocumentContentResponseProcessor(documentFactory);

      responseProcessor.processResponse(queryResponse, searchResponse);

      verify(searchResponse, never()).addDocument(any());
      verify(documentFactory, never()).buildDocument(Matchers.<SolrDocument>any());
   }

   @SuppressWarnings({"unchecked"})
   @Test
   public void processResponse_should_call_document_factory_when_documentList_contains_documents() throws Exception {
      SolrDocument solrDocument = new SolrDocument();
      setupDocumentList(solrDocument);
      DocumentContentResponseProcessor responseProcessor = new DocumentContentResponseProcessor(documentFactory);

      responseProcessor.processResponse(queryResponse, searchResponse);

      verify(documentFactory).buildDocument(solrDocument);
   }

   @SuppressWarnings({"unchecked"})
   @Test
   public void processResponse_should_add_built_document_to_response() throws Exception {
      SolrDocument solrDocument = new SolrDocument();
      setupDocumentList(solrDocument);
      Object responseDocument = new Object();
      when(documentFactory.buildDocument(solrDocument)).thenReturn(responseDocument);
      DocumentContentResponseProcessor responseProcessor = new DocumentContentResponseProcessor(documentFactory);

      responseProcessor.processResponse(queryResponse, searchResponse);

      verify(searchResponse).addDocument(responseDocument);
   }

   @SuppressWarnings({"unchecked"})
   @Test
   public void processResponse_should_set_resultCount_on_response() throws Exception {
      long expectedResultCount = 15;
      setupDocumentList();
      DocumentContentResponseProcessor responseProcessor = new DocumentContentResponseProcessor(documentFactory);
      when(solrDocumentList.getNumFound()).thenReturn(expectedResultCount);

      responseProcessor.processResponse(queryResponse, searchResponse);

      verify(searchResponse).setResultCount(expectedResultCount);
   }


   private void setupDocumentList(SolrDocument... documents) {
      when(solrDocumentList.iterator()).thenReturn(
            Arrays.asList(documents).iterator()
      );
   }
}
