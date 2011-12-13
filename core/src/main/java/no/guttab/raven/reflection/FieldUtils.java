package no.guttab.raven.reflection;

import java.lang.reflect.Field;

import static org.springframework.util.ReflectionUtils.*;

public class FieldUtils {
   private FieldUtils() {
   }

   public static Object getFieldValue(Field field, Object target) {
      makeAccessible(field);
      return getField(field, target);
   }

   public static void setFieldValue(Field field, Object target, Object value) {
      makeAccessible(field);
      setField(field, target, value);
   }

   public static Field findField(Class<?> type, FieldFilter fieldFilter) {
      for (Field field : type.getDeclaredFields()) {
         if (fieldFilter.matches(field)) {
            return field;
         }
      }
      return null;
   }

   public static void doForEachFieldOfType(Object target, Class<?> type, FieldCallback fieldCallback) {
      for (Field field : target.getClass().getDeclaredFields()) {
         if (field.getType() == type) {
            fieldCallback.doFor(field);
         }
      }
   }

   public static void doForFirstFieldOfType(Object target, Class<?> type, FieldCallback fieldCallback) {
      for (Field field : target.getClass().getDeclaredFields()) {
         if (field.getType() == type) {
            fieldCallback.doFor(field);
            return;
         }
      }
   }




}
