package no.guttab.raven.search.response;

import no.guttab.raven.search.response.navigators.NavigatorBuilder;
import no.guttab.raven.search.response.navigators.Navigators;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NavigatorsResponseProcessorTest {
   @Mock
   private NavigatorBuilder navigatorBuilder;
   @Mock
   private SearchResponse searchResponse;
   @Mock
   private QueryResponse queryResponse;
   @Mock
   private Navigators navigators;

   @Test
   public void processResponse_should_setNavigators_on_searchResponse() throws Exception {
      NavigatorsResponseProcessor navigatorsResponseProcessor = new NavigatorsResponseProcessor(navigatorBuilder);
      when(navigatorBuilder.buildFor(queryResponse)).thenReturn(navigators);

      navigatorsResponseProcessor.processResponse(queryResponse, searchResponse);

      verify(searchResponse).setNavigators(navigators);
      verifyNoMoreInteractions(searchResponse);
   }


}
