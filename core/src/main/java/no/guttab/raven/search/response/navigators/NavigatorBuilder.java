package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.config.SearchRequestConfig;
import no.guttab.raven.search.solr.Navigation;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

public class NavigatorBuilder {
   private SearchRequestConfig searchRequestConfig;

   public NavigatorBuilder(SearchRequestConfig searchRequestConfig) {
      this.searchRequestConfig = searchRequestConfig;
   }

   public Navigators buildFor(QueryResponse queryResponse) {
      final Navigation navigation = new Navigation(searchRequestConfig, queryResponse);
      final SelectNavigatorBuilder builder = new SelectNavigatorBuilder(navigation);

      return buildNavigators(queryResponse, builder);
   }

   private Navigators buildNavigators(QueryResponse queryResponse, SelectNavigatorBuilder builder) {
      final Navigators navigators = new Navigators();
      for (FacetField facetField : queryResponse.getFacetFields()) {
         Navigator navigator = builder.buildFor(facetField);
         navigators.addNavigator(navigator);
      }
      return navigators;
   }


}
