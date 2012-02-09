package no.guttab.raven.search.query;

import no.guttab.raven.annotations.MoreLikeThisSearchRequest;
import org.apache.solr.client.solrj.SolrQuery;

public class MoreLikeThisQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(Object queryInput, SolrQuery solrQuery) {
      if (isMoreLikeThisSearchRequest(queryInput)) {
         solrQuery.setQueryType("mlt");
      }
   }

   private boolean isMoreLikeThisSearchRequest(Object queryInput) {
      return getMoreLikeThisAnnotationFor(queryInput) != null;

   }

   private MoreLikeThisSearchRequest getMoreLikeThisAnnotationFor(Object queryInput) {
      return queryInput.getClass().getAnnotation(MoreLikeThisSearchRequest.class);
   }
}
