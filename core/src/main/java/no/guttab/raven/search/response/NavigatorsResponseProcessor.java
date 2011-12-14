package no.guttab.raven.search.response;

import no.guttab.raven.search.response.navigators.NavigatorBuilder;
import org.apache.solr.client.solrj.response.QueryResponse;

public class NavigatorsResponseProcessor implements ResponseProcessor {
   private NavigatorBuilder navigatorBuilder;

   public NavigatorsResponseProcessor(NavigatorBuilder navigatorBuilder) {
      this.navigatorBuilder = navigatorBuilder;
   }

   @Override
   public void processResponse(final QueryResponse queryResponse, final SearchResponse response) {
      response.setNavigators(navigatorBuilder.buildFor(queryResponse));
   }

}
