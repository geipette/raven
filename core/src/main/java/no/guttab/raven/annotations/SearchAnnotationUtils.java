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

   public static boolean isFacetField(Field field) {
      FacetField facetField = field.getAnnotation(FacetField.class);
      return facetField != null;
   }

   public static FacetFieldMode getFacetFieldMode(Field field) {
      final FacetField facetField = field.getAnnotation(FacetField.class);
      final FacetFieldMode facetFieldMode;
      if (facetField != null) {
         facetFieldMode = facetField.mode();
      } else {
         facetFieldMode = (FacetFieldMode) getDefaultValue(FacetField.class, "mode");
      }
      return facetFieldMode;
   }

   public static FacetFieldType getFacetFieldType(Field field) {
      final FacetField facetField = field.getAnnotation(FacetField.class);
      final FacetFieldType facetFieldType;
      if (facetField != null) {
         facetFieldType = facetField.type();
      } else {
         facetFieldType = (FacetFieldType) getDefaultValue(FacetField.class, "type");
      }
      return facetFieldType;
   }

}
