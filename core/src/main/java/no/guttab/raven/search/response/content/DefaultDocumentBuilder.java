package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import no.guttab.raven.common.DefaultConstructorInstantiator;
import no.guttab.raven.reflection.FieldFilter;
import no.guttab.raven.reflection.FieldUtils;
import org.apache.solr.common.SolrDocument;

import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static no.guttab.raven.reflection.FieldUtils.setFieldValue;

public class DefaultDocumentBuilder<T> implements DocumentBuilder<T> {
   private DefaultConstructorInstantiator<T> instantiator;

   private List<DocumentFieldHandler> documentFieldHandlers = (List<DocumentFieldHandler>) Arrays.asList(
         new AssignableTypeDocumentDocumentFieldHandler(),
         new PrimitiveTypeDocumentDocumentFieldHandler(),
         new JodaTimeDocumentFieldHandler()
   );

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
      Field field = FieldUtils.findField(responseDocument, new IndexFieldNameFieldFilter(entry.getKey()));
      if (field != null) {
         handleDocumentField(field, entry, responseDocument);
      }
   }

   private void handleDocumentField(Field field, Map.Entry<String, Object> entry, T responseDocument) {
      for (DocumentFieldHandler documentFieldHandler : documentFieldHandlers) {
         Object solrEntryValue = entry.getValue();
         if (documentFieldHandler.accepts(field.getType(), solrEntryValue.getClass())) {
            Object resolvedValue = documentFieldHandler.resolveDocumentFieldValueFor(field, solrEntryValue);
            setFieldValue(field, responseDocument, resolvedValue);
         }
      }
   }

   private class IndexFieldNameFieldFilter implements FieldFilter {
      private String name;

      public IndexFieldNameFieldFilter(String name) {
         this.name = name;
      }

      @Override
      public boolean matches(Field field) {
         return getIndexFieldName(field).equals(name);
      }
   }
}
