package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;
import java.util.Map;

import no.guttab.raven.reflection.FieldFilter;
import org.apache.solr.common.SolrDocument;

import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static no.guttab.raven.reflection.ClassUtils.newInstance;
import static no.guttab.raven.reflection.FieldUtils.findField;

public class DefaultDocumentBuilder<T> implements DocumentBuilder<T> {

   private Class<T> documentType;

   public DefaultDocumentBuilder(Class<T> documentType) {
      this.documentType = documentType;
   }

   @Override
   public T buildDocument(SolrDocument solrDocument) {
      T responseDocument = newInstance(documentType);
      processDocument(solrDocument, responseDocument);
      return responseDocument;
   }

   @Override
   public Class<T> getDocumentType() {
      return documentType;
   }

   private void processDocument(SolrDocument solrDocument, T responseDocument) {
      final DocumentFieldHandlers<T> documentFieldHandlers = new DocumentFieldHandlers<T>(responseDocument);
      for (Map.Entry<String, Object> entry : solrDocument) {
         Field field = findField(responseDocument.getClass(), new IndexFieldNameFieldFilter(entry.getKey()));
         if (field != null) {
            documentFieldHandlers.handleDocumentField(field, entry.getValue());
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
