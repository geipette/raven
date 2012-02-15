package no.guttab.raven.reflection;

import java.lang.reflect.Constructor;

class ByArgTypesConstructorFinder {
   private Class<?>[] argTypes;
   private Class<?> targetType;

   public ByArgTypesConstructorFinder(Class<?> targetType, Class<?>... argTypes) {
      this.targetType = targetType;
      this.argTypes = argTypes;
   }

   public Constructor<?> find() {
      for (Constructor<?> constructor : targetType.getConstructors()) {
         if (constructorHasCorrectArgTypes(constructor)) {
            return constructor;
         }
      }
      return null;
   }

   private boolean constructorHasCorrectArgTypes(Constructor<?> constructor) {
      final Class<?>[] types = constructor.getParameterTypes();
      return ArrayUtils.equals(types, argTypes);
   }
}
