package no.guttab.raven.reflection;

import java.lang.reflect.Constructor;
import java.util.Arrays;

class ByArgTypesConstructorFinder {
   private Class<?>[] argTypes;
   private Class<?> targetType;

   public ByArgTypesConstructorFinder(Class<?> targetType, Class<?>... argTypes) {
      this.targetType = targetType;
      this.argTypes = argTypes;
   }

   public Constructor<?> find() throws NoSuchConstructorException {
      for (Constructor<?> constructor : targetType.getConstructors()) {
         if (constructorHasCorrectArgTypes(constructor)) {
            return constructor;
         }
      }
      throw new NoSuchConstructorException(
            "No constructor for " + targetType + " with argTypes: " + Arrays.toString(argTypes) + " exists");
   }

   private boolean constructorHasCorrectArgTypes(Constructor<?> constructor) {
      final Class<?>[] types = constructor.getParameterTypes();
      return ArrayUtils.equals(types, argTypes);
   }
}
