package no.guttab.raven.search.solr;

import java.util.List;
import java.util.Set;

import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.response.NavigatorUrls;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import static no.guttab.raven.search.solr.FilterQueries.filterQueriesFor;
import static org.springframework.util.CollectionUtils.isEmpty;

public class Navigation {
   private SearchRequestTypeInfo searchRequestTypeInfo;

   private NavigatorUrls navigatorUrls;
   private FilterQueries filterQueries;

   public Navigation(SearchRequestTypeInfo searchRequestTypeInfo, QueryResponse queryResponse) {
      this.searchRequestTypeInfo = searchRequestTypeInfo;
      filterQueries = filterQueriesFor(queryResponse);
      navigatorUrls = buildNavigatorUrls(queryResponse.getFacetFields(), filterQueries);
   }

   public Set<String> fqsFor(FacetField facetField) {
      return filterQueries.findFqsFor(facetField);
   }

   public String urlFor(String indexFieldName, String fqCriteria) {
      return navigatorUrls.buildUrlFor(indexFieldName, fqCriteria);
   }

   public String resetUrlFor(String indexFieldName, String fqCriteria) {
      return navigatorUrls.resetUrlFor(indexFieldName, fqCriteria);
   }

   private NavigatorUrls buildNavigatorUrls(List<FacetField> facetFields, FilterQueries filterQueries) {
      final NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestTypeInfo);

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

}
