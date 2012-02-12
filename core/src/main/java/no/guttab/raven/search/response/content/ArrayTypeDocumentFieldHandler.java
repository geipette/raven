package no.guttab.raven.search.response.content;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

import no.guttab.raven.reflection.ClassUtils;

public class ArrayTypeDocumentFieldHandler implements DocumentFieldHandler {
   @Override
   public boolean accepts(Class<?> typeForDocumentField, Class<?> typeForSolrEntry) {
      return ClassUtils.isCollectionType(typeForSolrEntry) && typeForDocumentField.isArray();
   }

   @Override
   public Object resolveDocumentFieldValueFor(Field field, Object solrEntryValue) {
      final Collection solrEntryCollection = (Collection) solrEntryValue;
      final Object resultArray = createArrayFor(solrEntryCollection, field.getType());

      populateArray(resultArray, solrEntryCollection);
      return resultArray;
   }

   private Object createArrayFor(Collection solrEntryCollection, Class<?> documentFieldType) {
      return Array.newInstance(documentFieldType.getComponentType(), solrEntryCollection.size());
   }

   private void populateArray(Object resultArray, Collection solrEntryCollection) {
      int i = 0;
      for (Object solrCollectionEntry : solrEntryCollection) {
         Array.set(resultArray, i, solrCollectionEntry);
         i++;
      }
   }
}
