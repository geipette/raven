package no.guttab.raven.search.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.AnnotationUtils;
import no.guttab.raven.annotations.FacetFieldType;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.common.ReverseLookupMap;

import static no.guttab.raven.annotations.SearchAnnotationUtils.getFacetFieldType;
import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static org.apache.commons.lang3.reflect.FieldUtils.getDeclaredField;

public class SearchRequestConfig {
   private ReverseLookupMap<String, String> indexFieldNameMap = new ReverseLookupMap<String, String>();
   private Class<?> requestType;

   public SearchRequestConfig(Class<?> requestType) {
      this.requestType = requestType;
      AnnotationUtils.doForEachAnnotatedFieldOn(requestType, new AnnotatedFieldCallback() {
         @Override
         public void doFor(Field field, Annotation annotation) {
            indexFieldNameMap.put(field.getName(), getIndexFieldName(field));
         }
      }, FilterQuery.class);
   }

   public String indexFieldNameFor(String requestFieldName) {
      return indexFieldNameMap.get(requestFieldName);
   }

   public String requestFieldNameFor(String indexFieldName) {
      return indexFieldNameMap.getByValue(indexFieldName);
   }

   public boolean isIndexFieldMultiSelect(String indexFieldName) {
      return isRequestFieldMultiSelect(requestFieldNameFor(indexFieldName));
   }

   public boolean isRequestFieldMultiSelect(String requestFieldName) {
      final Field field = getDeclaredField(requestType, requestFieldName, true);
      final FacetFieldType facetFieldType = getFacetFieldType(field);

      return facetFieldType == FacetFieldType.MULTI_SELECT;
   }

}
