package no.guttab.raven.webapp.search.response;

import java.lang.reflect.Field;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static no.guttab.raven.webapp.reflection.FieldUtils.setFieldValue;
import static no.guttab.raven.webapp.annotations.AnnotationUtils.getIndexFieldName;

public class SingleSelectNavigatorResponseProcessor implements ResponseProcessor {
   private static final Logger log = LoggerFactory.getLogger(SingleSelectNavigatorResponseProcessor.class);

   @Override
   public void buildResponse(QueryResponse queryResponse, Object response) {
      for (Field field : response.getClass().getDeclaredFields()) {
         if (field.getType() == SingleSelectNavigator.class) {
            String indexFieldName = getIndexFieldName(field);
            SingleSelectNavigatorBuilder builder = new SingleSelectNavigatorBuilder(indexFieldName);
            SingleSelectNavigator singleSelectNavigator = builder.buildNavigator(queryResponse);
            setNavigatorOnTarget(response, field, singleSelectNavigator);
         }
      }
   }

   private void setNavigatorOnTarget(Object response, Field field, SingleSelectNavigator singleSelectNavigator) {
      if (singleSelectNavigator != null) {
         setFieldValue(field, response, singleSelectNavigator);
      } else {
         log.warn("SelectNavigator field " + field.getName() +
               " had no corresponding FacetField in the QueryResponse");
      }
   }


}
