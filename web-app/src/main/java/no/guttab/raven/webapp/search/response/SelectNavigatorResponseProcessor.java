package no.guttab.raven.webapp.search.response;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.annotations.NavigatorConfig;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectNavigatorResponseProcessor implements ResponseProcessor {
   private static final Logger log = LoggerFactory.getLogger(SelectNavigatorResponseProcessor.class);

   @Override
   public void buildResponse(QueryResponse queryResponse, SearchResponse response) {
      for (Field field : response.getClass().getDeclaredFields()) {
         NavigatorConfig navigatorConfigAnnotation = field.getAnnotation(NavigatorConfig.class);
         if (navigatorConfigAnnotation != null) {
            try {
               field.set(response, buildNavigator(navigatorConfigAnnotation, queryResponse));
            } catch (IllegalAccessException e) {
               log.error("Could not set navigator of field: " + field.getName(), e);
            }
         }
      }

   }

   private Navigator buildNavigator(NavigatorConfig navigatorConfigAnnotation, QueryResponse queryResponse) {
      String indexFieldName = "test";
      return new SingleSelectFacetNavigator(queryResponse.getFacetField(indexFieldName));
   }
}
