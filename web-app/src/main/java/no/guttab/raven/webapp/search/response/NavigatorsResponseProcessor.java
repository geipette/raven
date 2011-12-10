package no.guttab.raven.webapp.search.response;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.annotations.AnnotationUtils;
import no.guttab.raven.webapp.annotations.FilterQuery;
import no.guttab.raven.webapp.reflection.FieldCallback;
import no.guttab.raven.webapp.reflection.FieldFilter;
import no.guttab.raven.webapp.reflection.FieldUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import static no.guttab.raven.webapp.reflection.FieldUtils.doForFirstFieldOfType;
import static no.guttab.raven.webapp.reflection.FieldUtils.setFieldValue;

public class NavigatorsResponseProcessor implements ResponseProcessor {
   private Class<?> requestType;

   public NavigatorsResponseProcessor(Class<?> requestType) {
      this.requestType = requestType;
   }

   @Override
   public void buildResponse(final QueryResponse queryResponse, final Object response) {
      doForFirstFieldOfType(response, Navigators.class, new FieldCallback() {
         @Override
         public void doFor(Field field) {
            final Navigators navigators = new Navigators();
            final ResponseFilterQueries responseFilterQueries = new ResponseFilterQueries(queryResponse);
//            final NavigatorUrls navigatorUrls = new
            for (final FacetField facetField : queryResponse.getFacetFields()) {
               String fq = responseFilterQueries.findFqForFacetField(facetField);
               if (!StringUtils.isEmpty(fq)) {
                  Field requestField = FieldUtils.findField(requestType, new FieldFilter() {
                     @Override
                     public boolean matches(Field field) {
                        FilterQuery filterQuery = field.getAnnotation(FilterQuery.class);
                        return filterQuery != null && AnnotationUtils.getIndexFieldName(field).equals(facetField.getName());
                     }
                  });
                  makeUrlFragmentForRequestField(requestField, fq);
               }
            }

            setFieldValue(field, response, navigators);
         }
      });
   }

   private void makeUrlFragmentForRequestField(Field requestField, String fq) {
      //To change body of created methods use File | Settings | File Templates.
   }

   private Navigator<?> buildNavigatorFor(FacetField facetField, String fq) {

      return null;
   }

}
