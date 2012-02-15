package no.guttab.raven.reflection;

class DefaultConstructorInstantiator<T> {
   private Class<? extends T> type;

   public DefaultConstructorInstantiator(Class<? extends T> type) {
      this.type = type;
   }

   public T newInstance() {
      try {
         return type.newInstance();
      } catch (InstantiationException e) {
         throw new CouldNotInstantiateTypeException(type, e);
      } catch (IllegalAccessException e) {
         throw new CouldNotInstantiateTypeException(type, e);
      }
   }

   public static class CouldNotInstantiateTypeException extends RuntimeException {
      public CouldNotInstantiateTypeException(Class<?> type, InstantiationException e) {
         super(type.getName() + " can not be instantiated. Does it have a default constructor?", e);
      }

      public CouldNotInstantiateTypeException(Class<?> type, IllegalAccessException e) {
         super(type.getName() + " can not be instantiated. IllegalAccess.", e);
      }

   }

}
