package no.guttab.raven.search.response;

import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.response.navigators.Navigator;
import no.guttab.raven.search.response.navigators.Navigators;
import no.guttab.raven.search.response.navigators.select.SelectNavigatorBuilder;
import no.guttab.raven.search.response.navigators.sort.SortNavigator;
import no.guttab.raven.search.response.navigators.sort.SortNavigatorBuilder;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

public class NavigatorsResponseProcessor<T> implements ResponseProcessor<T> {
   private SearchRequestTypeInfo searchRequestTypeInfo;
   private Class<T> responseType;

   public NavigatorsResponseProcessor(SearchRequestTypeInfo searchRequestTypeInfo, Class<T> responseType) {
      this.searchRequestTypeInfo = searchRequestTypeInfo;
      this.responseType = responseType;
   }

   @Override
   public void processResponse(final QueryResponse queryResponse, final MutableSearchResponse<T> response) {
      response.setNavigators(buildFor(queryResponse));
   }

   private Navigators buildFor(QueryResponse queryResponse) {
      final Navigation navigation = new Navigation(searchRequestTypeInfo, queryResponse);
      Navigators navigators = buildSelectNavigators(queryResponse, navigation);
      navigators.addNavigator(buildSortNavigator(navigation));
      return navigators;
   }

   private SortNavigator buildSortNavigator(Navigation navigation) {
      return new SortNavigatorBuilder(responseType, navigation).build();
   }

   private Navigators buildSelectNavigators(QueryResponse queryResponse, Navigation navigation) {
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
