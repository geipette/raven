package no.guttab.raven.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchServerTest {
   @Mock
   private SearchResource searchResource;

   @Mock
   private SolrQuery solrQuery;

   @Mock
   private SolrServer solrServer;

   @Before
   public void setUp() throws Exception {
      when(searchResource.getServer()).thenReturn(solrServer);
   }

   @Test
   public void search_should_get_server_from_searchResource_and_call_query_with_the_supplied_solrQuery() throws Exception {
      SearchServer searchServer = new SearchServer(searchResource);

      searchServer.search(solrQuery);

      verify(solrServer).query(solrQuery);
   }

   @Test(expected = SearchServer.SearchServerRuntimeException.class)
   public void search_should_throw_exception_when_solrServer_query_throws_exception() throws Exception {
      when(solrServer.query(solrQuery)).thenThrow(new SolrServerException(""));
      SearchServer searchServer = new SearchServer(searchResource);

      searchServer.search(solrQuery);
   }


}
