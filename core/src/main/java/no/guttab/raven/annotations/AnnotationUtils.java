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
         callbackIfAnnotationPresent(annotationsWithCallback, field);
      }
   }

   public static void doForEachAnnotatedFieldOn(
         Object target, AnnotatedFieldCallback callback, Class<? extends Annotation> annotationType) {
      doForEachAnnotatedFieldOn(target.getClass(), callback, annotationType);
   }

   public static void doForEachAnnotatedFieldOn(
         Class<?> type, AnnotatedFieldCallback callback, Class<? extends Annotation> annotationType) {
      for (Field field : type.getDeclaredFields()) {
         callbackIfAnnotationPresent(callback, field, field.getAnnotation(annotationType));
      }
   }

   public static void doForFirstAnnotatedFieldOn(
         Object target, AnnotatedFieldCallback callback, Class<? extends Annotation> annotationType) {
      doForFirstAnnotatedFieldOn(target.getClass(), callback, annotationType);
   }

   public static void doForFirstAnnotatedFieldOn(
         Class<?> type, AnnotatedFieldCallback callback, Class<? extends Annotation> annotationType) {
      for (Field field : type.getDeclaredFields()) {
         if (callbackIfAnnotationPresent(callback, field, field.getAnnotation(annotationType))) {
            return;
         }
      }
   }

   private static boolean callbackIfAnnotationPresent(AnnotatedFieldCallback callback, Field field, Annotation annotation) {
      if (annotation != null) {
         callback.doFor(field, annotation);
         return true;
      }
      return false;
   }

   private static void callbackIfAnnotationPresent(AnnotationsWithCallback annotationsWithCallback, Field field) {
      final Map<Class<? extends Annotation>, Annotation> annotations =
            findAnnotationsFor(field, annotationsWithCallback.getAnnotationTypes());
      if (!annotations.isEmpty()) {
         annotationsWithCallback.doFor(field, annotations);
      }
   }

   private static Map<Class<? extends Annotation>, Annotation> findAnnotationsFor(Field field, List<Class<? extends Annotation>> annotationTypes) {
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
