package no.guttab.raven.reflection;

import java.lang.reflect.Constructor;

public class ReflectionUtils {
   private ReflectionUtils() {
   }

   public static Constructor<?> findConstructorForType(Class<?> targetType, Class<?>... argTypes) {
      return new ByArgTypesConstructorFinder(targetType, argTypes).find();
   }

}
