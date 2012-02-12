package no.guttab.raven.search.response.content;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import no.guttab.raven.annotations.DocumentFieldHandlerType;
import no.guttab.raven.common.DefaultConstructorInstantiator;

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
      final DefaultConstructorInstantiator<DocumentFieldHandler> instantiator =
            new DefaultConstructorInstantiator<DocumentFieldHandler>(annotation.value());
      return instantiator.newInstance();
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
