package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import no.guttab.raven.common.DefaultConstructorInstantiator;
import no.guttab.raven.reflection.ClassUtils;
import no.guttab.raven.reflection.FieldFilter;
import org.apache.solr.common.SolrDocument;
import org.joda.time.DateTime;

import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static no.guttab.raven.reflection.FieldUtils.findField;
import static no.guttab.raven.reflection.FieldUtils.setFieldValue;

public class DefaultDocumentBuilder<T> implements DocumentBuilder<T> {
   private DefaultConstructorInstantiator<T> instantiator;

   public DefaultDocumentBuilder(Class<T> documentType) {
      this.instantiator = new DefaultConstructorInstantiator<T>(documentType);
   }

   @Override
   public T buildDocument(SolrDocument solrDocument) {
      T responseDocument = instantiator.newInstance();
      processDocument(solrDocument, responseDocument);
      return responseDocument;
   }

   private void processDocument(SolrDocument solrDocument, T responseDocument) {
      for (Map.Entry<String, Object> entry : solrDocument) {
         setFieldInDocumentFor(entry, responseDocument);
      }
   }

   private void setFieldInDocumentFor(final Map.Entry<String, Object> entry, T responseDocument) {
      Field field = findField(responseDocument, new EntryCompatibleFieldFilter(entry));
      if (field != null) {
         Object value = resolveValueFor(field, entry);
         setFieldValue(field, responseDocument, value);
      }
   }

   private Object resolveValueFor(Field field, Map.Entry<String, Object> entry) {
      if (field.getType().isArray()) {
         return ((Collection) entry.getValue()).toArray();
      } else if (field.getType().isAssignableFrom(DateTime.class)) {
         return new DateTime(entry.getValue());
      } else {
         return entry.getValue();
      }
   }


   private static class EntryCompatibleFieldFilter implements FieldFilter {
      private final Map.Entry<String, Object> entry;

      public EntryCompatibleFieldFilter(Map.Entry<String, Object> entry) {
         this.entry = entry;
      }

      @Override
      public boolean matches(Field field) {
         String name = getIndexFieldName(field);
         return name.equals(entry.getKey()) && isTypeCompatible(field, entry);
      }

      private boolean isTypeCompatible(Field field, Map.Entry<String, Object> entry) {
         Class<?> typeForField = normalizedType(field.getType());
         Class<?> typeForEntryValue = normalizedType(entry.getValue().getClass());

         return typeForField.isAssignableFrom(typeForEntryValue) || isCollectionTypes(typeForField, typeForEntryValue);
      }

      private boolean isCollectionTypes(Class<?> typeForField, Class<?> typeForEntryValue) {
         return false;
//         return ClassUtils.isCollectionType(typeForField) && ClassUtils.isCollectionType(typeForEntryValue);
      }

      private Class<?> normalizedType(Class<?> type) {
         if (type.isPrimitive()) {
            return ClassUtils.typeForPrimitive(type);
         } else if (type.isAssignableFrom(Date.class)) {
            return DateTime.class;
         } else {
            return type;
         }
      }

   }
}
