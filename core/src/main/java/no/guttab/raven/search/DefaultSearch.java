package no.guttab.raven.search;

import no.guttab.raven.search.query.QueryBuilder;
import no.guttab.raven.search.response.ResponseProcessors;
import no.guttab.raven.search.response.content.DefaultDocumentBuilder;
import no.guttab.raven.search.response.content.DocumentBuilder;
import org.apache.solr.client.solrj.SolrQuery;

public class DefaultSearch<T> implements Search<T> {
   private Object searchRequest;
   private QueryBuilder queryBuilder;
   private ResponseProcessors<T> responseProcessors;
   private DocumentBuilder<T> documentFactory;

   public DefaultSearch(Object searchRequest, Class<T> responseDocumentType) {
      this(searchRequest, new DefaultDocumentBuilder<T>(responseDocumentType));
   }

   public DefaultSearch(Object searchRequest, DocumentBuilder<T> documentFactory) {
      this(searchRequest,
            documentFactory,
            new QueryBuilder(),
            ResponseProcessors.<T>defaultProcessors(searchRequest.getClass(), documentFactory));
   }

   public DefaultSearch(
         Object searchRequest,
         DocumentBuilder<T> documentFactory,
         QueryBuilder queryBuilder,
         ResponseProcessors<T> responseProcessors) {
      this.searchRequest = searchRequest;
      this.documentFactory = documentFactory;
      this.queryBuilder = queryBuilder;
      this.responseProcessors = responseProcessors;
   }

   public Search<T> withDocumentFactory(DocumentBuilder<T> documentFactory) {
      return new DefaultSearch<T>(searchRequest, documentFactory, queryBuilder, responseProcessors);
   }

   public Search<T> withQueryBuilder(QueryBuilder queryBuilder) {
      return new DefaultSearch<T>(searchRequest, documentFactory, queryBuilder, responseProcessors);
   }

   public Search<T> withResponseProcessors(ResponseProcessors<T> responseProcessors) {
      return new DefaultSearch<T>(searchRequest, documentFactory, queryBuilder, responseProcessors);
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
   public DocumentBuilder<T> getDocumentFactory() {
      return documentFactory;
   }

   @Override
   public ResponseProcessors<T> getResponseProcessors() {
      return responseProcessors;
   }
}
