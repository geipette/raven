package no.guttab.raven.search;

import no.guttab.raven.search.query.QueryBuilder;
import no.guttab.raven.search.response.DefaultConstructorSearchResponseFactory;
import no.guttab.raven.search.response.ResponseProcessors;
import no.guttab.raven.search.response.SearchResponse;
import no.guttab.raven.search.response.SearchResponseFactory;
import org.apache.solr.client.solrj.SolrQuery;

public class DefaultSearch<T extends SearchResponse> implements Search<T> {
   private Object searchRequest;
   private SearchResponseFactory<T> searchResponseFactory;
   private QueryBuilder queryBuilder;
   private ResponseProcessors<T> responseProcessors;

   public DefaultSearch(Object searchRequest, Class<T> searchResponseType) {
      this(searchRequest, new DefaultConstructorSearchResponseFactory<T>(searchResponseType));
   }

   public DefaultSearch(Object searchRequest, SearchResponseFactory<T> searchResponseFactory) {
      this(searchRequest,
            searchResponseFactory,
            new QueryBuilder(),
            ResponseProcessors.<T>defaultProcessors(searchRequest.getClass()));
   }

   private DefaultSearch(
         Object searchRequest,
         SearchResponseFactory<T> searchResponseFactory,
         QueryBuilder queryBuilder,
         ResponseProcessors<T> responseProcessors) {
      this.searchRequest = searchRequest;
      this.searchResponseFactory = searchResponseFactory;
      this.queryBuilder = queryBuilder;
      this.responseProcessors = responseProcessors;
   }

   public Search<T> withQueryBuilder(QueryBuilder queryBuilder) {
      return new DefaultSearch<T>(searchRequest, searchResponseFactory, queryBuilder, responseProcessors);
   }

   public Search<T> withSearchResponseFactory(SearchResponseFactory<T> searchResponseFactory) {
      return new DefaultSearch<T>(searchRequest, searchResponseFactory, queryBuilder, responseProcessors);
   }

   public Search<T> withResponseProcessors(ResponseProcessors<T> responseProcessors) {
      return new DefaultSearch<T>(searchRequest, searchResponseFactory, queryBuilder, responseProcessors);
   }

   @Override
   public Object getSearchRequest() {
      return searchRequest;
   }

   @Override
   public SolrQuery buildQuery() {
      return queryBuilder.buildQuery(searchRequest);
   }

   @Override
   public SearchResponseFactory<T> getSearchResponseFactory() {
      return searchResponseFactory;
   }

   @Override
   public ResponseProcessors<T> getResponseProcessors() {
      return responseProcessors;
   }
}
