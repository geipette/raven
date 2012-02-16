package no.guttab.raven.search.response;

import java.util.List;
import java.util.Set;

import no.guttab.raven.search.QueryResponseHeaderParams;
import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.filter.FilterQueries;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import static no.guttab.raven.search.filter.FilterQueries.filterQueriesFor;
import static org.springframework.util.CollectionUtils.isEmpty;

public class Navigation {
   private SearchRequestTypeInfo searchRequestTypeInfo;
   private QueryResponse queryResponse;

   private NavigatorUrls navigatorUrls;
   private FilterQueries filterQueries;
   private QueryResponseHeaderParams headerParams;

   public Navigation(SearchRequestTypeInfo searchRequestTypeInfo, QueryResponse queryResponse) {
      this.searchRequestTypeInfo = searchRequestTypeInfo;
      this.queryResponse = queryResponse;

      headerParams = new QueryResponseHeaderParams(queryResponse.getResponseHeader());
      filterQueries = filterQueriesFor(headerParams);
      buildNavigatorUrls();
   }

   private void buildNavigatorUrls() {
      navigatorUrls = new NavigatorUrls(searchRequestTypeInfo);
      new FacetNavigatorUrlsProcessor(filterQueries, queryResponse.getFacetFields()).process(navigatorUrls);
      new SortNavigatorUrlsProcessor(headerParams).process(navigatorUrls);
   }

   public String getSortFieldName() {
      return searchRequestTypeInfo.getSortFieldName();
   }

   public String getSortValue() {
      return headerParams.getSort();
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

   private static class FacetNavigatorUrlsProcessor {
      private List<FacetField> facetFields;
      private FilterQueries filterQueries;

      public FacetNavigatorUrlsProcessor(FilterQueries filterQueries, List<FacetField> facetFields) {
         this.filterQueries = filterQueries;
         this.facetFields = facetFields;
      }

      public void process(NavigatorUrls navigatorUrls) {
         for (final FacetField facetField : facetFields) {
            addUrlFragmentsFor(facetField, navigatorUrls);
         }
      }

      private void addUrlFragmentsFor(FacetField facetField, NavigatorUrls navigatorUrls) {
         final Set<String> fqCriterias = filterQueries.findFqCriteriasFor(facetField);
         if (!isEmpty(fqCriterias)) {
            for (String fqCriteria : fqCriterias) {
               navigatorUrls.addUrlFragment(facetField.getName(), fqCriteria);
            }
         }
      }

   }

   private static class SortNavigatorUrlsProcessor {
      private QueryResponseHeaderParams headerParams;

      public SortNavigatorUrlsProcessor(QueryResponseHeaderParams headerParams) {
         this.headerParams = headerParams;
      }

      public void process(NavigatorUrls navigatorUrls) {
         if (!StringUtils.isEmpty(headerParams.getSort())) {
            navigatorUrls.addUrlFragment("sort", headerParams.getSort());
         }
      }
   }
}
