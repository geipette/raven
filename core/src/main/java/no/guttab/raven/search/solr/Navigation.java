package no.guttab.raven.search.solr;

import java.util.List;
import java.util.Set;

import no.guttab.raven.search.config.SearchRequestConfig;
import no.guttab.raven.search.response.NavigatorUrls;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import static org.springframework.util.CollectionUtils.isEmpty;

public class Navigation {
   private SearchRequestConfig searchRequestConfig;

   NavigatorUrls navigatorUrls;
   FilterQueries filterQueries;

   private FilterQueries buildFilterQueries(QueryResponse queryResponse) {
      final QueryResponseHeaderParams headerParams = new QueryResponseHeaderParams(queryResponse.getResponseHeader());
      return headerParams.getFilterQueries();
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

}
