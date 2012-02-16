package no.guttab.raven.search.response.content;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import no.guttab.raven.reflection.NoSuchConstructorException;

import static no.guttab.raven.reflection.ReflectionUtils.findConstructorForType;

class JodaTimeInstantiator {
   private Class<?> type;
   private Date date;

   public JodaTimeInstantiator(Class<?> type, Date date) {
      this.type = type;
      this.date = date;
   }

   public Object newInstance() {
      try {
         Constructor<?> constructorForDate = findConstructorForDate(type);
         return constructorForDate.newInstance(date);
      } catch (InvocationTargetException e) {
         throw new CouldNotCreateJodaTimeException(e);
      } catch (InstantiationException e) {
         throw new CouldNotCreateJodaTimeException(e);
      } catch (IllegalAccessException e) {
         throw new CouldNotCreateJodaTimeException(e);
      } catch (NoSuchConstructorException e) {
         throw new CouldNotCreateJodaTimeException(e);
      }
   }

   private Constructor<?> findConstructorForDate(Class<?> targetType) throws NoSuchConstructorException {
      Constructor<?> constructor = findConstructorForType(targetType, Date.class);
      if (constructor == null) {
         constructor = findConstructorForType(targetType, Object.class);
      }
      return constructor;
   }

   public static class CouldNotCreateJodaTimeException extends RuntimeException {
      public CouldNotCreateJodaTimeException(Throwable cause) {
         super(cause);
      }
   }
}
