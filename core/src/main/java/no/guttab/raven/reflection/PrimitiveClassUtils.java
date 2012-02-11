package no.guttab.raven.reflection;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveClassUtils {
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

   private PrimitiveClassUtils() {
   }

   public static Class typeForPrimitive(Class primitive) {
      return primitiveClassMap.get(primitive);
   }
}
