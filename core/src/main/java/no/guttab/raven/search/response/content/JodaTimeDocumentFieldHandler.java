package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;
import java.util.Date;

import no.guttab.raven.annotations.DocumentFieldHandler;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

public class JodaTimeDocumentFieldHandler implements DocumentFieldHandler {
   @Override
   public boolean accepts(Class<?> typeForDocumentField, Class<?> typeForSolrEntry) {
      return isJodaTimeType(typeForDocumentField);
   }

   @Override
   public Object resolveDocumentFieldValueFor(Field field, Object solrEntryValue) {
      final Date solrValue = (Date) solrEntryValue;
      return createJodaTime(field.getType(), solrValue);
   }

   private Object createJodaTime(Class<?> type, Date date) {
      return new JodaTimeInstantiator(type, date).newInstance();
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

}
