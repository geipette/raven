package no.guttab.raven.search.query;

import no.guttab.raven.annotations.Query;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

public class QueryStringQueryProcessorTest {
   @Mock
   private SolrQuery solrQuery;

   private QueryStringQueryProcessor queryStringQueryProcessor;

   @Before
   public void setUp() throws Exception {
      queryStringQueryProcessor = new QueryStringQueryProcessor();
   }

   @Test
   public void buildQuery_should_set_query_when_query_annotation_defined() throws Exception {
      class TestInput {
         @Query
         String keyword = "bil";
      }

      queryStringQueryProcessor.buildQuery(new TestInput(), solrQuery);

      Mockito.verify(solrQuery).setQuery("bil");
   }

   @Test
   public void buildQuery_should_do_nothing_when_query_annotation_not_defined() throws Exception {
      class TestInput {
         String keyword = "bil";
      }

      queryStringQueryProcessor.buildQuery(new TestInput(), solrQuery);

      Mockito.verifyNoMoreInteractions(solrQuery);
   }


   @Test
   public void buildQuery_should_set_queryType_when_defined_in_annotation() throws Exception {
      class TestInput {
         @Query(type = "dismax")
         String keyword = "bil";
      }

      queryStringQueryProcessor.buildQuery(new TestInput(), solrQuery);

      Mockito.verify(solrQuery).setQuery("bil");
      Mockito.verify(solrQuery).setQueryType("dismax");

   }

   @Test
   public void buildQuery_should_not_set_queryType_when_not_defined_in_annotation() throws Exception {
      class TestInput {
         @Query
         String keyword = "bil";
      }

      queryStringQueryProcessor.buildQuery(new TestInput(), solrQuery);

      Mockito.verify(solrQuery).setQuery("bil");
      Mockito.verifyNoMoreInteractions(solrQuery);
   }


}
