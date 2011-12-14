package no.guttab.raven.search.response.navigators;

import java.util.List;
import java.util.Set;

import no.guttab.raven.search.config.SearchRequestConfig;
import no.guttab.raven.search.response.NavigatorUrls;
import no.guttab.raven.search.solr.FilterQueries;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import static no.guttab.raven.search.solr.QueryResponseUtils.getFqsFromHeader;
import static org.springframework.util.CollectionUtils.isEmpty;

public class NavigatorBuilder {
   private SearchRequestConfig searchRequestConfig;

   public NavigatorBuilder(SearchRequestConfig searchRequestConfig) {
      this.searchRequestConfig = searchRequestConfig;
   }

   public Navigators buildFor(QueryResponse queryResponse) {
      final FilterQueries filterQueries = new FilterQueries(getFqsFromHeader(queryResponse));
      final NavigatorUrls navigatorUrls = buildNavigatorUrls(queryResponse.getFacetFields(), filterQueries);

      final Navigators navigators = new Navigators();
      for (FacetField facetField : queryResponse.getFacetFields()) {
         Navigator navigator = createNavigator(filterQueries, navigatorUrls, facetField);
         navigators.addNavigator(navigator);
      }
      return navigators;
   }

   private NavigatorUrls buildNavigatorUrls(List<FacetField> facetFields, FilterQueries filterQueries) {
      final NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);

      for (final FacetField facetField : facetFields) {
         Set<String> fqCriterias = filterQueries.findFqCriteriasFor(facetField);
         if (!isEmpty(fqCriterias)) {
            for (String fqCriteria : fqCriterias) {
               navigatorUrls.addUrlFragment(facetField.getName(), fqCriteria);
            }
         }
      }
      return navigatorUrls;
   }

   private SelectNavigator createNavigator(FilterQueries filterQueries, NavigatorUrls navigatorUrls, FacetField facetField) {
      return new SelectNavigator(facetField, navigatorUrls, filterQueries);
   }


}
