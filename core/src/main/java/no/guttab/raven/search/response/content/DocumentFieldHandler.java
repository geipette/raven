package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;

public interface DocumentFieldHandler {
   boolean accepts(Class<?> typeForDocumentField, Class<?> typeForSolrEntry);

   Object resolveDocumentFieldValueFor(Field field, Object solrEntryValue);
}
