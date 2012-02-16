package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import no.guttab.raven.annotations.DocumentFieldHandler;
import no.guttab.raven.annotations.DocumentFieldHandlerType;

import static no.guttab.raven.reflection.ClassUtils.newInstance;
import static no.guttab.raven.reflection.FieldUtils.setFieldValue;

public class DocumentFieldHandlers<T> {
   private static List<DocumentFieldHandler> documentFieldHandlers = (List<DocumentFieldHandler>) Arrays.asList(
         new AssignableTypeDocumentDocumentFieldHandler(),
         new PrimitiveTypeDocumentFieldHandler(),
         new JodaTimeDocumentFieldHandler(),
         new ArrayTypeDocumentFieldHandler()
   );

   private T responseDocument;

   public DocumentFieldHandlers(T responseDocument) {
      this.responseDocument = responseDocument;
   }

   public void handleDocumentField(Field field, Object solrEntryValue) {
      DocumentFieldHandlerType annotation = field.getAnnotation(DocumentFieldHandlerType.class);
      if (annotation != null) {
         executeCustomFieldHandler(field, solrEntryValue, annotation);
      } else {
         executeDefaultFieldHandlers(field, solrEntryValue);
      }
   }

   private void executeCustomFieldHandler(Field field, Object solrEntryValue, DocumentFieldHandlerType annotation) {
      final DocumentFieldHandler documentFieldHandler = createCustomDocumentFieldHandler(annotation);
      resolveAndSetFieldValue(field, solrEntryValue, documentFieldHandler);
   }

   private DocumentFieldHandler createCustomDocumentFieldHandler(DocumentFieldHandlerType annotation) {
      return newInstance(annotation.value());
   }

   private void executeDefaultFieldHandlers(Field field, Object solrEntryValue) {
      for (DocumentFieldHandler documentFieldHandler : documentFieldHandlers) {
         if (documentFieldHandler.accepts(field.getType(), solrEntryValue.getClass())) {
            resolveAndSetFieldValue(field, solrEntryValue, documentFieldHandler);
         }
      }
   }

   private void resolveAndSetFieldValue(Field field, Object solrEntryValue, DocumentFieldHandler documentFieldHandler) {
      Object resolvedValue = documentFieldHandler.resolveDocumentFieldValueFor(field, solrEntryValue);
      setFieldValue(field, responseDocument, resolvedValue);
   }

}
