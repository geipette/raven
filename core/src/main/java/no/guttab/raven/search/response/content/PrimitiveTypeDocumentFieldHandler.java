package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;

import no.guttab.raven.reflection.ClassUtils;

public class PrimitiveTypeDocumentFieldHandler implements DocumentFieldHandler {
   @Override
   public boolean accepts(Class<?> typeForDocumentField, Class<?> typeForSolrEntry) {
      if (typeForDocumentField.isPrimitive() || typeForSolrEntry.isPrimitive()) {
         return normalizedType(typeForDocumentField).equals(normalizedType(typeForSolrEntry));
      }
      return false;
   }

   @Override
   public Object resolveDocumentFieldValueFor(Field field, Object solrEntryValue) {
      return solrEntryValue;
   }

   private Class<?> normalizedType(Class<?> type) {
      if (type.isPrimitive()) {
         return ClassUtils.typeForPrimitive(type);
      } else {
         return type;
      }
   }

}
