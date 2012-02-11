package no.guttab.raven.search.response;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import no.guttab.raven.search.SearchRequestTypeInfo;
import no.guttab.raven.search.response.content.DocumentContentResponseProcessor;
import no.guttab.raven.search.response.content.DocumentFactory;

public class ResponseProcessors<T> implements Iterable<ResponseProcessor<T>> {
   private final List<ResponseProcessor<T>> processors;

   @SuppressWarnings({"unchecked"})
   public static <T> ResponseProcessors<T> defaultProcessors(
         Class<?> searchRequestType, DocumentFactory<T> documentFactory) {
      final SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(searchRequestType);
      return new ResponseProcessors<T>(
            new NavigatorsResponseProcessor<T>(searchRequestTypeInfo),
            new DocumentContentResponseProcessor<T>(documentFactory)
      );
   }

   public ResponseProcessors(List<ResponseProcessor<T>> processors) {
      this.processors = processors;
   }

   public ResponseProcessors(ResponseProcessor<T>... processors) {
      this.processors = Arrays.asList(processors);
   }

   @Override
   public Iterator<ResponseProcessor<T>> iterator() {
      return processors.iterator();
   }
}
