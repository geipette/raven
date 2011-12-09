package no.guttab.raven.webapp.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationUtils {
   private AnnotationUtils() {
   }

   public static String getIndexFieldName(Field field) {
      IndexFieldName indexFieldName = field.getAnnotation(IndexFieldName.class);
      return indexFieldName == null ? field.getName() : indexFieldName.value();
   }

   public static <T extends Annotation> void executeForEachAnnotatedFieldOn(
         Object object, Class<T> annotationType, AnnotatedFieldExecutor<T> executor) {
      for (Field field : object.getClass().getDeclaredFields()) {
         T annotation = field.getAnnotation(annotationType);
         if (annotation != null) {
            executor.execute(field, annotation);
         }
      }
   }

}
