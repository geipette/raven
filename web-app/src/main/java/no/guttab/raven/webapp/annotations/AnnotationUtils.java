package no.guttab.raven.webapp.annotations;

import java.lang.reflect.Field;

public class AnnotationUtils {
   private AnnotationUtils() {
   }

   public static String getIndexFieldName(Field field) {
      IndexFieldName indexFieldName = field.getAnnotation(IndexFieldName.class);
      return indexFieldName == null ? field.getName() : indexFieldName.value();
   }
}
