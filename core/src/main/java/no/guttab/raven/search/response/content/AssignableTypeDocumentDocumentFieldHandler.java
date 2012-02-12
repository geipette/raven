package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;

public class AssignableTypeDocumentDocumentFieldHandler implements DocumentFieldHandler {

   @Override
   public boolean accepts(Class typeForDocumentField, Class typeForSolrEntry) {
      return typeForDocumentField.isAssignableFrom(typeForSolrEntry);
   }

   @Override
   public Object resolveDocumentFieldValueFor(Field field, Object solrEntryValue) {
      return solrEntryValue;
   }
}
