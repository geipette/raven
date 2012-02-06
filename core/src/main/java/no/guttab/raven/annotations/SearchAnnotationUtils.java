package no.guttab.raven.annotations;

import java.lang.reflect.Field;

public final class SearchAnnotationUtils {

   private SearchAnnotationUtils() {
   }

   public static String getIndexFieldName(Field field) {
      IndexFieldName indexFieldName = field.getAnnotation(IndexFieldName.class);
      return indexFieldName == null ? field.getName() : indexFieldName.value();
   }

}
