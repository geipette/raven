package no.guttab.raven.search.response;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import no.guttab.raven.search.SearchRequestTypeInfo;

public class ResponseProcessors<T extends SearchResponse> implements Iterable<ResponseProcessor<T>> {
   private final List<ResponseProcessor<T>> processors;

   @SuppressWarnings({"unchecked"})
   public static <T extends SearchResponse> ResponseProcessors<T> defaultProcessors(Class<?> searchRequestType) {
      final SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(searchRequestType);
      return new ResponseProcessors<T>(
            new NavigatorsResponseProcessor<T>(searchRequestTypeInfo)
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
