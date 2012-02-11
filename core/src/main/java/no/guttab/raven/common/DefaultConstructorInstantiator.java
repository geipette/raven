package no.guttab.raven.common;

public class DefaultConstructorInstantiator<T> {
   private Class<T> type;

   public DefaultConstructorInstantiator(Class<T> type) {
      this.type = type;
   }

   public T newInstance() {
      try {
         return type.newInstance();
      } catch (InstantiationException e) {
         throw new CouldNotInstantiateResponseTypeException(type, e);
      } catch (IllegalAccessException e) {
         throw new CouldNotInstantiateResponseTypeException(type, e);
      }
   }

   public static class CouldNotInstantiateResponseTypeException extends RuntimeException {
      public CouldNotInstantiateResponseTypeException(Class<?> responseType, InstantiationException e) {
         super("ResponseType: " + responseType.getName() +
               " can not be instantiated. Does it have a default constructor?", e);
      }

      public CouldNotInstantiateResponseTypeException(Class<?> responseType, IllegalAccessException e) {
         super("ResponseType:" + responseType.getName() + " can not be instantiated.", e);
      }

   }

}
