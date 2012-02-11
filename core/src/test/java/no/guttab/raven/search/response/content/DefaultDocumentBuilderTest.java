package no.guttab.raven.search.response.content;

import org.apache.solr.common.SolrDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;


@RunWith(MockitoJUnitRunner.class)
public class DefaultDocumentBuilderTest {
   @Mock
   SolrDocument solrDocument;

   @Test
   public void newDocument_should_create_document_object()
         throws Exception {

      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      assertThat(defaultDocumentFactory.buildDocument(solrDocument), is(not(nullValue())));
   }

   @Test
   public void newDocument_()
         throws Exception {

      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      assertThat(defaultDocumentFactory.buildDocument(solrDocument), is(not(nullValue())));
   }


   public static class TestDocument {}

}
