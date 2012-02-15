package no.guttab.raven.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AnnotationUtils {
   private AnnotationUtils() {
   }

   public static void doForEachAnnotatedFieldOn(Object target, AnnotationsWithCallback annotationsWithCallback) {
      doForEachAnnotatedFieldOn(target.getClass(), annotationsWithCallback);
   }

   public static void doForEachAnnotatedFieldOn(Class<?> type, AnnotationsWithCallback annotationsWithCallback) {
      for (Field field : type.getDeclaredFields()) {
         callbackIfFieldAnnotated(field, annotationsWithCallback);
      }
   }

   public static <T extends Annotation> void doForEachAnnotatedFieldOn(
         Object target, Class<T> annotationType, AnnotatedFieldCallback<T> callback) {
      doForEachAnnotatedFieldOn(target.getClass(), annotationType, callback);
   }

   public static <T extends Annotation> void doForEachAnnotatedFieldOn(
         Class<?> type, Class<T> annotationType, AnnotatedFieldCallback<T> callback) {
      for (Field field : type.getDeclaredFields()) {
         callbackIfFieldAnnotated(field, field.getAnnotation(annotationType), callback);
      }
   }

   public static <T extends Annotation> void doForFirstAnnotatedFieldOn(
         Object target, Class<T> annotationType, AnnotatedFieldCallback<T> callback) {
      doForFirstAnnotatedFieldOn(target.getClass(), annotationType, callback);
   }

   public static <T extends Annotation> void doForFirstAnnotatedFieldOn(
         Class<?> type, Class<T> annotationType, AnnotatedFieldCallback<T> callback) {
      for (Field field : type.getDeclaredFields()) {
         if (callbackIfFieldAnnotated(field, field.getAnnotation(annotationType), callback)) {
            return;
         }
      }
   }

   private static <T extends Annotation> boolean callbackIfFieldAnnotated(Field field, T annotation, AnnotatedFieldCallback<T> callback) {
      if (annotation != null) {
         callback.doFor(field, annotation);
         return true;
      }
      return false;
   }

   private static void callbackIfFieldAnnotated(Field field, AnnotationsWithCallback annotationsWithCallback) {
      final Map<Class<? extends Annotation>, Annotation> annotations =
            findAnnotationsFor(field, annotationsWithCallback.getAnnotationTypes());
      if (!annotations.isEmpty()) {
         annotationsWithCallback.doFor(field, annotations);
      }
   }

   private static Map<Class<? extends Annotation>, Annotation> findAnnotationsFor(
         Field field, List<Class<? extends Annotation>> annotationTypes) {
      final Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<Class<? extends Annotation>, Annotation>();
      for (Class<? extends Annotation> annotationType : annotationTypes) {
         Annotation annotation = field.getAnnotation(annotationType);
         if (annotation != null) {
            annotations.put(annotationType, annotation);
         }
      }
      return annotations;
   }
}
