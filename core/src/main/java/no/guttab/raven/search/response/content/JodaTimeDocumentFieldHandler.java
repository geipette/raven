package no.guttab.raven.search.response.content;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import no.guttab.raven.reflection.ReflectionUtils;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

import static no.guttab.raven.reflection.ReflectionUtils.NoSuchConstructorException;
import static no.guttab.raven.reflection.ReflectionUtils.findConstructorForType;

public class JodaTimeDocumentFieldHandler implements DocumentFieldHandler {
   @Override
   public boolean accepts(Class<?> typeForDocumentField, Class<?> typeForSolrEntry) {
      return isJodaTimeType(typeForDocumentField);
   }

   @Override
   public Object resolveDocumentFieldValueFor(Field field, Object solrEntryValue) {
      Date solrValue = (Date) solrEntryValue;
      return createJodaTime(field.getType(), solrValue);
   }

   private Object createJodaTime(Class<?> type, Date date) {
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
      try {
         return findConstructorForType(targetType, Date.class);
      } catch (ReflectionUtils.NoSuchConstructorException e) {
         return findConstructorForType(targetType, Object.class);
      }
   }

   private boolean isJodaTimeType(Class<?> type) {
      return isInstant(type) || isPartial(type);
   }

   private boolean isInstant(Class<?> type) {
      return ReadableInstant.class.isAssignableFrom(type);
   }

   private boolean isPartial(Class<?> type) {
      return ReadablePartial.class.isAssignableFrom(type);
   }

   public static class CouldNotCreateJodaTimeException extends RuntimeException {
      public CouldNotCreateJodaTimeException(Throwable cause) {
         super(cause);
      }

   }


}
