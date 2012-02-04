package no.guttab.raven.search.response;

public class DefaultConstructorSearchResponseFactory<T extends SearchResponse> implements SearchResponseFactory<T> {
   private Class<T> responseType;

   public DefaultConstructorSearchResponseFactory(Class<T> responseType) {
      this.responseType = responseType;
   }

   @Override
   public T newInstance() {
      try {
         return responseType.newInstance();
      } catch (InstantiationException e) {
         throw new CouldNotInstantiateResponseTypeException(responseType, e);
      } catch (IllegalAccessException e) {
         throw new CouldNotInstantiateResponseTypeException(responseType, e);
      }
   }

   private static class CouldNotInstantiateResponseTypeException extends RuntimeException {
      public CouldNotInstantiateResponseTypeException(Class<?> responseType, InstantiationException e) {
         super("ResponseType: " + responseType.getName() +
               " can not be instantiated. Does it have a default constructor?", e);
      }

      public CouldNotInstantiateResponseTypeException(Class<?> responseType, IllegalAccessException e) {
         super("ResponseType:" + responseType.getName() + " can not be instantiated.", e);
      }

   }

}
