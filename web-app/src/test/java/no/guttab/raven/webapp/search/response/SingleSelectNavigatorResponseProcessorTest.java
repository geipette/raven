package no.guttab.raven.webapp.search.response;

import no.guttab.raven.webapp.annotations.IndexFieldName;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SingleSelectNavigatorResponseProcessorTest {
   @Mock
   QueryResponse queryResponse;

   private SingleSelectNavigatorResponseProcessor singleSelectNavigatorResponseProcessor;


   @Before
   public void setUp() throws Exception {
      singleSelectNavigatorResponseProcessor = new SingleSelectNavigatorResponseProcessor();
   }

   @Test
   public void buildResponse_should_build_navigator_when_facetField_returned() throws Exception {
      class ResponseForTest {
         SingleSelectNavigator areaId;
      }
      final ResponseForTest response = new ResponseForTest();

      Mockito.when(queryResponse.getFacetField("areaId")).thenReturn(facetFieldForTest());

      singleSelectNavigatorResponseProcessor.buildResponse(queryResponse, response);

      assertThat(response.areaId, is(not(nullValue())));
      assertThat(response.areaId.getItems().size(), is(2));
   }

   @Test
   public void buildResponse_do_nothing_when_facetField_is_not_returned() throws Exception {
      class ResponseForTest {
         SingleSelectNavigator areaId;
      }
      final ResponseForTest response = new ResponseForTest();

      singleSelectNavigatorResponseProcessor.buildResponse(queryResponse, response);

      assertThat(response.areaId, is(nullValue()));
   }

   @Test
   public void buildResponse_should_use_indexFieldName_when_defined() throws Exception {
      class ResponseForTest {
         @IndexFieldName("indexFieldAreaId")
         SingleSelectNavigator areaId;
      }
      final ResponseForTest response = new ResponseForTest();

      Mockito.when(queryResponse.getFacetField("indexFieldAreaId")).thenReturn(facetFieldForTest());

      singleSelectNavigatorResponseProcessor.buildResponse(queryResponse, response);

      assertThat(response.areaId, is(not(nullValue())));
      assertThat(response.areaId.getItems().size(), is(2));
   }


   private FacetField facetFieldForTest() {
      FacetField facetField = new FacetField("areaId");
      facetField.add("100", 1456);
      facetField.add("200", 854);
      return facetField;
   }

}
