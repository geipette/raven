package no.guttab.raven.reflection;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClassUtils {
   private static Map<Class, Class> primitiveClassMap = new HashMap<Class, Class>() {{
      put(Integer.TYPE, Integer.class);
      put(Long.TYPE, Long.class);
      put(Double.TYPE, Double.class);
      put(Float.TYPE, Float.class);
      put(Boolean.TYPE, Boolean.class);
      put(Character.TYPE, Character.class);
      put(Byte.TYPE, Byte.class);
      put(Void.TYPE, Void.class);
      put(Short.TYPE, Short.class);
   }};

   private ClassUtils() {
   }

   public static Class typeForPrimitive(Class primitive) {
      return primitiveClassMap.get(primitive);
   }

   public static boolean isCollectionType(Class type) {
      return Collection.class.isAssignableFrom(type) || type.isArray();
   }

   public static boolean isCollectionType(Field field) {
      return isCollectionType(field.getType());
   }

}
