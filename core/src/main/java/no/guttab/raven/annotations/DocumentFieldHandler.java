package no.guttab.raven.annotations;

import java.lang.reflect.Field;

public interface DocumentFieldHandler {
   boolean accepts(Class<?> typeForDocumentField, Class<?> typeForSolrEntry);

   Object resolveDocumentFieldValueFor(Field field, Object solrEntryValue);
}
