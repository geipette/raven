package no.guttab.raven.annotations;

import java.lang.reflect.Field;

import static org.springframework.core.annotation.AnnotationUtils.getDefaultValue;

public final class SearchAnnotationUtils {

   private SearchAnnotationUtils() {
   }

   public static String getIndexFieldName(Field field) {
      IndexFieldName indexFieldName = field.getAnnotation(IndexFieldName.class);
      return indexFieldName == null ? field.getName() : indexFieldName.value();
   }

   public static FacetFieldMode getFacetFieldMode(Field field) {
      final FacetField facetField = field.getAnnotation(FacetField.class);
      return facetField != null ? facetField.mode() : (FacetFieldMode) getDefaultValue(FacetField.class, "mode");
   }

}
