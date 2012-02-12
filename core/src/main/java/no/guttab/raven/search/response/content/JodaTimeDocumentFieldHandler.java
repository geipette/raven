package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

public class JodaTimeDocumentFieldHandler implements DocumentFieldHandler {
   @Override
   public boolean accepts(Class<?> typeForDocumentField, Class<?> typeForSolrEntry) {
      return typeForDocumentField.isAssignableFrom(ReadableInstant.class) ||
            typeForDocumentField.isAssignableFrom(ReadablePartial.class);
   }

   @Override
   public Object resolveDocumentFieldValueFor(Field field, Object solrEntryValue) {
      Date solrValue = (Date) solrEntryValue;

      return new DateTime(solrValue);
   }


}
