package no.guttab.raven.webapp.search.response;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.reflection.FieldCallback;
import no.guttab.raven.webapp.search.config.SearchRequestConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import static no.guttab.raven.webapp.reflection.FieldUtils.doForFirstFieldOfType;
import static no.guttab.raven.webapp.reflection.FieldUtils.setFieldValue;

public class NavigatorsResponseProcessor implements ResponseProcessor {
   private SearchRequestConfig searchRequestConfig;

   public NavigatorsResponseProcessor(SearchRequestConfig searchRequestConfig) {
      this.searchRequestConfig = searchRequestConfig;
   }

   @Override
   public void buildResponse(final QueryResponse queryResponse, final Object response) {
      doForFirstFieldOfType(response, Navigators.class, new FieldCallback() {
         @Override
         public void doFor(Field field) {
            final ResponseFilterQueries responseFilterQueries = new ResponseFilterQueries(queryResponse);
            final NavigatorUrls navigatorUrls = buildNavigatorUrls(queryResponse, responseFilterQueries);
            final Navigators navigators = buildNavigators(navigatorUrls, queryResponse, responseFilterQueries);

            setFieldValue(field, response, navigators);
         }
      });
   }

   private Navigators buildNavigators(
         NavigatorUrls navigatorUrls, QueryResponse queryResponse, ResponseFilterQueries responseFilterQueries) {
      final Navigators navigators = new Navigators();
      for (FacetField facetField : queryResponse.getFacetFields()) {
         navigators.addNavigator(new SingleSelectNavigator(facetField, navigatorUrls, responseFilterQueries));
      }
      return navigators;
   }

   private NavigatorUrls buildNavigatorUrls(QueryResponse queryResponse, ResponseFilterQueries responseFilterQueries) {
      final NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);

      for (final FacetField facetField : queryResponse.getFacetFields()) {
         String fqCriteria = responseFilterQueries.findFqCriteriaFor(facetField);
         if (!StringUtils.isEmpty(fqCriteria)) {
            navigatorUrls.addUrlFragment(facetField.getName(), fqCriteria);
         }
      }
      return navigatorUrls;
   }


}
