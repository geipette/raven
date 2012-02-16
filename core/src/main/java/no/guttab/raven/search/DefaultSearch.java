package no.guttab.raven.search;

import no.guttab.raven.search.response.ResponseProcessors;
import no.guttab.raven.search.response.content.DefaultDocumentBuilder;
import no.guttab.raven.search.response.content.DocumentBuilder;
import org.apache.solr.client.solrj.SolrQuery;

public class DefaultSearch<T> implements Search<T> {
   private Object searchRequest;
   private QueryBuilder queryBuilder;
   private ResponseProcessors<T> responseProcessors;
   private DocumentBuilder<T> documentBuilder;

   public DefaultSearch(Object searchRequest, Class<T> responseDocumentType) {
      this(searchRequest, new DefaultDocumentBuilder<T>(responseDocumentType));
   }

   public DefaultSearch(Object searchRequest, DocumentBuilder<T> documentBuilder) {
      this(searchRequest,
            documentBuilder,
            new QueryBuilder(),
            ResponseProcessors.<T>defaultProcessors(searchRequest.getClass(), documentBuilder));
   }

   public DefaultSearch(
         Object searchRequest,
         DocumentBuilder<T> documentBuilder,
         QueryBuilder queryBuilder,
         ResponseProcessors<T> responseProcessors) {
      this.searchRequest = searchRequest;
      this.documentBuilder = documentBuilder;
      this.queryBuilder = queryBuilder;
      this.responseProcessors = responseProcessors;
   }

   public Search<T> withDocumentBuilder(DocumentBuilder<T> documentFactory) {
      return new DefaultSearch<T>(searchRequest, documentFactory, queryBuilder, responseProcessors);
   }

   public Search<T> withQueryBuilder(QueryBuilder queryBuilder) {
      return new DefaultSearch<T>(searchRequest, documentBuilder, queryBuilder, responseProcessors);
   }

   public Search<T> withResponseProcessors(ResponseProcessors<T> responseProcessors) {
      return new DefaultSearch<T>(searchRequest, documentBuilder, queryBuilder, responseProcessors);
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
   public DocumentBuilder<T> getDocumentBuilder() {
      return documentBuilder;
   }

   @Override
   public ResponseProcessors<T> getResponseProcessors() {
      return responseProcessors;
   }
}
