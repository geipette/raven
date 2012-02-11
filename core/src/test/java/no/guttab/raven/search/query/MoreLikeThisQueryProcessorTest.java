package no.guttab.raven.search.query;

import no.guttab.raven.annotations.MoreLikeThis;
import no.guttab.raven.annotations.MoreLikeThisStream;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static no.guttab.raven.annotations.InterestingTerms.DEFAULT;
import static no.guttab.raven.annotations.InterestingTerms.DETAILS;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoreLikeThisQueryProcessorTest {

   @Mock
   private SolrQuery solrQuery;

   @Test
   public void buildQuery_should_set_queryType_to_mlt_when_the_request_is_annotated_with_MoreLikeThis() throws Exception {
      @MoreLikeThis(fields = "aField")
      class TestQuery {
      }

      MoreLikeThisQueryProcessor queryProcessor = new MoreLikeThisQueryProcessor();
      queryProcessor.buildQuery(new TestQuery(), solrQuery);

      verify(solrQuery).setQueryType("mlt");
   }

   @Test
   public void buildQuery_not_should_set_queryType_to_mlt_when_the_request_is_not_annotated_with_MoreLikeThis()
         throws Exception {
      class TestQuery {
      }

      MoreLikeThisQueryProcessor queryProcessor = new MoreLikeThisQueryProcessor();
      queryProcessor.buildQuery(new TestQuery(), solrQuery);

      verify(solrQuery, never()).setQueryType("mlt");
   }

   @Test
   public void buildQuery_should_set_mlt_fl_according_to_MoreLikeThis_annotation() throws Exception {
      @MoreLikeThis(fields = {"field1", "field2"})
      class TestQuery {
      }

      MoreLikeThisQueryProcessor queryProcessor = new MoreLikeThisQueryProcessor();
      queryProcessor.buildQuery(new TestQuery(), solrQuery);

      verify(solrQuery).set("mlt.fl", "field1", "field2");
   }

   @Test
   public void buildQuery_should_set_mlt_interestingTerms_according_to_MoreLikeThis_annotation() throws Exception {
      @MoreLikeThis(fields = {"aField"}, interestingTerms = DETAILS)
      class TestQuery {
      }

      MoreLikeThisQueryProcessor queryProcessor = new MoreLikeThisQueryProcessor();
      queryProcessor.buildQuery(new TestQuery(), solrQuery);

      verify(solrQuery).set("mlt.interestingTerms", "details");
   }

   @Test
   public void buildQuery_should_not_set_mlt_interestingTerms_when_MoreLikeThis_annotation_has_default_interestingTerms() throws Exception {
      @MoreLikeThis(fields = {"aField"}, interestingTerms = DEFAULT)
      class TestQuery {
      }

      MoreLikeThisQueryProcessor queryProcessor = new MoreLikeThisQueryProcessor();
      queryProcessor.buildQuery(new TestQuery(), solrQuery);

      verify(solrQuery, never()).set(eq("mlt.interestingTerms"), Matchers.<String>any());
   }

   @Test
   public void buildQuery_should_set_stream_when_MoreLikeThisStream_is_set_on_a_field() throws Exception {
      @MoreLikeThis(fields = "aField")
      class TestQuery {
         @MoreLikeThisStream
         String body = "bodyValue";
      }

      MoreLikeThisQueryProcessor queryProcessor = new MoreLikeThisQueryProcessor();
      queryProcessor.buildQuery(new TestQuery(), solrQuery);

      verify(solrQuery).set("stream.body", "bodyValue");
   }
}
