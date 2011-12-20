package no.guttab.raven.search.config;

import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.AnnotationUtils;
import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.FacetFieldType;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.common.ReverseLookupMap;

import static no.guttab.raven.annotations.AnnotationUtils.getIndexFieldName;
import static org.apache.commons.lang3.reflect.FieldUtils.getDeclaredField;
import static org.springframework.core.annotation.AnnotationUtils.getDefaultValue;

public class SearchRequestConfig {
   private ReverseLookupMap<String, String> indexFieldNameMap = new ReverseLookupMap<String, String>();
   private Class<?> requestType;

   public SearchRequestConfig(Class<?> requestType) {
      this.requestType = requestType;
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

   public boolean isIndexFieldMultiSelect(String indexFieldName) {
      return isRequestFieldMultiSelect(requestFieldNameFor(indexFieldName));
   }

   public boolean isRequestFieldMultiSelect(String requestFieldName) {
      final Field field = getDeclaredField(requestType, requestFieldName, true);
      final FacetField facetField = field.getAnnotation(FacetField.class);
      final FacetFieldType facetFieldType;

      if (facetField != null) {
         facetFieldType = facetField.type();
      } else {
         facetFieldType = (FacetFieldType) getDefaultValue(FacetField.class, requestFieldName);
      }

      return facetFieldType == FacetFieldType.MULTI_SELECT;
   }

}
