package no.guttab.raven.search.response;

import java.util.List;
import java.util.Set;

import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.filter.FilterQueries;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import static no.guttab.raven.search.filter.FilterQueries.filterQueriesFor;
import static org.springframework.util.CollectionUtils.isEmpty;

public class Navigation {
   private SearchRequestTypeInfo searchRequestTypeInfo;

   private NavigatorUrls navigatorUrls;
   private FilterQueries filterQueries;

   public Navigation(SearchRequestTypeInfo searchRequestTypeInfo, QueryResponse queryResponse) {
      this.searchRequestTypeInfo = searchRequestTypeInfo;
      filterQueries = filterQueriesFor(queryResponse);
      buildNavigatorUrls(queryResponse.getFacetFields(), filterQueries);
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

   private void buildNavigatorUrls(List<FacetField> facetFields, FilterQueries filterQueries) {
      navigatorUrls = new NavigatorUrls(searchRequestTypeInfo);

      for (final FacetField facetField : facetFields) {
         addUrlFragmentsFor(facetField, filterQueries);
      }
   }

   private void addUrlFragmentsFor(FacetField facetField, FilterQueries filterQueries) {
      final Set<String> fqCriterias = filterQueries.findFqCriteriasFor(facetField);
      if (!isEmpty(fqCriterias)) {
         addUrlFragmentForEachFqCriteria(facetField, fqCriterias);
      }
   }

   private void addUrlFragmentForEachFqCriteria(FacetField facetField, Set<String> fqCriterias) {
      for (String fqCriteria : fqCriterias) {
         navigatorUrls.addUrlFragment(facetField.getName(), fqCriteria);
      }
   }

}
