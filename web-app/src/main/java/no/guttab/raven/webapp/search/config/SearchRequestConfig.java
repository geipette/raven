package no.guttab.raven.webapp.search.config;

import java.lang.reflect.Field;

import no.guttab.raven.webapp.annotations.AnnotatedFieldCallback;
import no.guttab.raven.webapp.annotations.AnnotationUtils;
import no.guttab.raven.webapp.annotations.FilterQuery;
import no.guttab.raven.webapp.common.ReverseLookupMap;

import static no.guttab.raven.webapp.annotations.AnnotationUtils.getIndexFieldName;

public class SearchRequestConfig {
   private ReverseLookupMap<String, String> indexFieldNameMap = new ReverseLookupMap<String, String>();

   public SearchRequestConfig(Class<?> requestType) {
      AnnotationUtils.doForEachAnnotatedFieldOn(requestType, FilterQuery.class, new AnnotatedFieldCallback<FilterQuery>() {
         @Override
         public void doFor(Field field, FilterQuery annotation) {
            indexFieldNameMap.put(field.getName(), getIndexFieldName(field));
         }
      });
   }

   public String indexFieldNameFor(String requestFieldName) {
      return indexFieldNameMap.get(requestFieldName);
   }

   public String requestFieldNameFor(String indexFieldName) {
      return indexFieldNameMap.getByValue(indexFieldName);
   }

}
