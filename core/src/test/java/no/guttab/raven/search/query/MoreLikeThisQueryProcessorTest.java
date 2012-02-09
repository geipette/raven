package no.guttab.raven.search.query;

import no.guttab.raven.annotations.MoreLikeThisSearchRequest;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoreLikeThisQueryProcessorTest {

   @Mock
   private SolrQuery solrQuery;

   @Test
   public void buildQuery_should_set_queryType_to_mlt_if_the_request_is_a_MoreLikeThisSearchRequest() throws Exception {
      @MoreLikeThisSearchRequest(fields = "aField")
      class TestQuery {
      }

      MoreLikeThisQueryProcessor queryProcessor = new MoreLikeThisQueryProcessor();
      queryProcessor.buildQuery(new TestQuery(), solrQuery);

      verify(solrQuery).setQueryType("mlt");
   }

   @Test
   public void buildQuery_not_should_set_queryType_to_mlt_if_the_request_is_not_a_MoreLikeThisSearchRequest()
         throws Exception {
      class TestQuery {
      }

      MoreLikeThisQueryProcessor queryProcessor = new MoreLikeThisQueryProcessor();
      queryProcessor.buildQuery(new TestQuery(), solrQuery);

      verify(solrQuery, never()).setQueryType("mlt");
   }


}
