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

   public static <T extends Annotation> void executeForFirstAnnotatedFieldOn(
         Object object, Class<T> annotationType, AnnotatedFieldExecutor<T> executor) {
      for (Field field : object.getClass().getDeclaredFields()) {
         T annotation = field.getAnnotation(annotationType);
         if (annotation != null) {
            executor.execute(field, annotation);
            return;
         }
      }
   }


   public static Field findAnnotatedField(Object object, Class<? extends Annotation> annotationType) {
      for (Field field : object.getClass().getDeclaredFields()) {
         Annotation annotation = field.getAnnotation(annotationType);
         if (annotation != null) {
            return field;
         }
      }
      return null;
   }

}
