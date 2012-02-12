package no.guttab.raven.reflection;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ReflectionUtils {
   private ReflectionUtils() {
   }

   public static Constructor<?> findConstructorForType(Class<?> targetType, Class<?>... argTypes)
         throws NoSuchConstructorException {
      Constructor<?>[] constructors = targetType.getConstructors();
      for (Constructor<?> constructor : constructors) {
         Class<?>[] types = constructor.getParameterTypes();
         if (ArrayUtils.equals(types, argTypes)) {
            return constructor;
         }
      }
      throw new NoSuchConstructorException(
            "No constructor for " + targetType + " with argTypes: " + Arrays.toString(argTypes) + " exists");
   }

   public static class NoSuchConstructorException extends Exception {
      private NoSuchConstructorException(String message) {
         super(message);
      }
   }
}
