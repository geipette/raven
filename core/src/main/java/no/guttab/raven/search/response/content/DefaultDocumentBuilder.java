package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;
import java.util.Map;

import no.guttab.raven.common.DefaultConstructorInstantiator;
import no.guttab.raven.reflection.FieldFilter;
import no.guttab.raven.reflection.FieldUtils;
import no.guttab.raven.reflection.PrimitiveClassUtils;
import org.apache.solr.common.SolrDocument;

import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
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
      Field field = FieldUtils.findField(responseDocument, new FieldFilter() {
         @Override
         public boolean matches(Field field) {
            String name = getIndexFieldName(field);
            return name.equals(entry.getKey()) && isTypeCompatible(field, entry);
         }
      });

      if (field != null) {
         setFieldValue(field, responseDocument, entry.getValue());
      }
   }

   private boolean isTypeCompatible(Field field, Map.Entry<String, Object> entry) {
      return boxedType(field.getType()).isAssignableFrom(boxedType(entry.getValue().getClass()));
   }

   private Class<?> boxedType(Class<?> type) {
      if (type.isPrimitive()) {
         return PrimitiveClassUtils.typeForPrimitive(type);
      } else {
         return type;
      }
   }

}
