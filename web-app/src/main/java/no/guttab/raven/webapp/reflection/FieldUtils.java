package no.guttab.raven.webapp.reflection;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;

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

}
