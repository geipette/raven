package no.guttab.raven.search.config;

import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.AnnotationUtils;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.common.ReverseLookupMap;

import static no.guttab.raven.annotations.AnnotationUtils.getIndexFieldName;

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
