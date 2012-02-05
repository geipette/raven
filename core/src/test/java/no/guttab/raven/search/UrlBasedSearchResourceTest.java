package no.guttab.raven.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class UrlBasedSearchResourceTest {

   @Test
   public void constructor_should_initialize_commonsHttpServer_with_supplied_url() throws Exception {
      UrlBasedSearchResource urlBasedSearchResource = new UrlBasedSearchResource("http://solr");

      SolrServer actual = urlBasedSearchResource.getServer();

      assertThat(actual, instanceOf(CommonsHttpSolrServer.class));
      assertThat(((CommonsHttpSolrServer) actual).getBaseURL(), is("http://solr"));
   }
}
