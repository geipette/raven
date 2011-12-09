package no.guttab.raven.webapp.search.query;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

public class QueryProcessorList implements QueryProcessor {

   private List<QueryProcessor> queryProcessors;

   public QueryProcessorList(List<QueryProcessor> queryProcessors) {
      this.queryProcessors = queryProcessors;
   }

   @Override
   public void buildQuery(Object queryInput, SolrQuery solrQuery) {
      for (QueryProcessor queryProcessor : queryProcessors) {
         queryProcessor.buildQuery(queryInput, solrQuery);
      }
   }
}
