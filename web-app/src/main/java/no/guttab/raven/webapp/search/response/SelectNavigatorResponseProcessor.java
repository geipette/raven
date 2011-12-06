package no.guttab.raven.webapp.search.response;

import java.lang.reflect.Field;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static no.guttab.raven.webapp.annotations.AnnotationUtils.getIndexFieldName;

public class SelectNavigatorResponseProcessor implements ResponseProcessor {
   private static final Logger log = LoggerFactory.getLogger(SelectNavigatorResponseProcessor.class);

   @Override
   public void buildResponse(QueryResponse queryResponse, Object response) {
      for (Field field : response.getClass().getDeclaredFields()) {
         if (field.getType() == SelectNavigator.class) {
            try {
               String indexFieldName = getIndexFieldName(field);
               SelectNavigator selectNavigator = buildNavigator(queryResponse, indexFieldName);
               if (selectNavigator != null) {
                  field.set(response, selectNavigator);
               } else {
                  log.warn("SelectNavigator field " + field.getName() +
                        " had no corresponding FacetField in the QueryResponse");
               }
            } catch (IllegalAccessException e) {
               log.error("Could not set navigator of field: " + field.getName(), e);
            }
         }
      }
   }

   private SelectNavigator buildNavigator(QueryResponse queryResponse, String indexFieldName) {
      final FacetField facetField = queryResponse.getFacetField(indexFieldName);
      if (facetField == null) {
         return null;
      }
      return new SelectNavigator(facetField);
   }
}
