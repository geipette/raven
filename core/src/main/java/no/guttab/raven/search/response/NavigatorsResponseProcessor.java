package no.guttab.raven.search.response;

import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.response.navigators.Navigator;
import no.guttab.raven.search.response.navigators.Navigators;
import no.guttab.raven.search.response.navigators.select.SelectNavigatorBuilder;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

public class NavigatorsResponseProcessor<T> implements ResponseProcessor<T> {
   private SearchRequestTypeInfo searchRequestTypeInfo;

   public NavigatorsResponseProcessor(SearchRequestTypeInfo searchRequestTypeInfo) {
      this.searchRequestTypeInfo = searchRequestTypeInfo;
   }

   @Override
   public void processResponse(final QueryResponse queryResponse, final MutableSearchResponse<T> response) {
      response.setNavigators(buildFor(queryResponse));
   }

   private Navigators buildFor(QueryResponse queryResponse) {
      final Navigation navigation = new Navigation(searchRequestTypeInfo, queryResponse);
      final SelectNavigatorBuilder builder = new SelectNavigatorBuilder(navigation);
      return buildNavigatorsForEachFacetField(queryResponse, builder);
   }

   private Navigators buildNavigatorsForEachFacetField(QueryResponse queryResponse, SelectNavigatorBuilder builder) {
      final Navigators navigators = new Navigators();
      for (FacetField facetField : queryResponse.getFacetFields()) {
         Navigator navigator = builder.buildFor(facetField);
         navigators.addNavigator(navigator);
      }
      return navigators;
   }


}
