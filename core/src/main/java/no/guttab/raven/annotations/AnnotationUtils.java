package no.guttab.raven.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public final class AnnotationUtils {
   private AnnotationUtils() {
   }

   public static <T extends Annotation> void doForEachAnnotatedFieldOn(
         Class<?> type, Class<T> annotationType, AnnotatedFieldCallback<T> callback) {
      for (Field field : type.getDeclaredFields()) {
         T annotation = field.getAnnotation(annotationType);
         if (annotation != null) {
            callback.doFor(field, annotation);
         }
      }
   }

   public static <T extends Annotation> void doForEachAnnotatedFieldOn(
         Object target, Class<T> annotationType, AnnotatedFieldCallback<T> callback) {
      doForEachAnnotatedFieldOn(target.getClass(), annotationType, callback);
   }

   public static <T extends Annotation> void doForFirstAnnotatedFieldOn(
         Class<?> type, Class<T> annotationType, AnnotatedFieldCallback<T> callback) {
      for (Field field : type.getDeclaredFields()) {
         T annotation = field.getAnnotation(annotationType);
         if (annotation != null) {
            callback.doFor(field, annotation);
            return;
         }
      }
   }

   public static <T extends Annotation> void doForFirstAnnotatedFieldOn(
         Object target, Class<T> annotationType, AnnotatedFieldCallback<T> callback) {
      doForFirstAnnotatedFieldOn(target.getClass(), annotationType, callback);
   }

}
