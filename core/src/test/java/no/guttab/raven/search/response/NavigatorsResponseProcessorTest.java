package no.guttab.raven.search.response;

import no.guttab.raven.search.config.SearchRequestTypeInfo;
import no.guttab.raven.search.response.navigators.Navigators;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class NavigatorsResponseProcessorTest {
   @Mock
   private SearchRequestTypeInfo searchRequestTypeInfo;
   @Mock
   private SearchResponse searchResponse;
   @Mock
   private QueryResponse queryResponse;

   @Test
   @Ignore
   public void processResponse_should_setNavigators_on_searchResponse() throws Exception {
      NavigatorsResponseProcessor<SearchResponse> navigatorsResponseProcessor =
            new NavigatorsResponseProcessor<SearchResponse>(searchRequestTypeInfo);

      navigatorsResponseProcessor.processResponse(queryResponse, searchResponse);

      verify(searchResponse).setNavigators(Matchers.<Navigators>any(Navigators.class));
      verifyNoMoreInteractions(searchResponse);
   }


}
