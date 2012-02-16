package no.guttab.raven.search;

import java.util.ArrayList;
import java.util.List;

import no.guttab.raven.search.query.FacetFieldQueryProcessor;
import no.guttab.raven.search.query.FilterQueryProcessor;
import no.guttab.raven.search.query.QueryProcessor;
import no.guttab.raven.search.query.QueryStringQueryProcessor;
import no.guttab.raven.search.query.SortQueryProcessor;
import org.apache.solr.client.solrj.SolrQuery;

import static java.util.Arrays.asList;

public class QueryBuilder {
   private List<QueryProcessor> queryProcessors = new ArrayList<QueryProcessor>();

   public QueryBuilder() {
      this(asList(
            new FacetFieldQueryProcessor(),
            new FilterQueryProcessor(),
            new QueryStringQueryProcessor(),
            new SortQueryProcessor()
      ));
   }

   private QueryBuilder(List<QueryProcessor> queryProcessors) {
      this.queryProcessors.addAll(queryProcessors);
   }

   public QueryBuilder withQueryProcessors(List<QueryProcessor> queryProcessors) {
      return new QueryBuilder(queryProcessors);
   }

   public QueryBuilder withAddedQueryProcessors(QueryProcessor... queryProcessors) {
      QueryBuilder defaultQueryBuilder = new QueryBuilder(this.queryProcessors);
      defaultQueryBuilder.queryProcessors.addAll(asList(queryProcessors));
      return defaultQueryBuilder;
   }

   public SolrQuery buildQuery(Search<?> search) {
      return buildQuery(search.getSearchRequest());
   }

   public SolrQuery buildQuery(Object queryInput) {
      final SolrQuery solrQuery = new SolrQuery();
      for (QueryProcessor queryProcessor : queryProcessors) {
         queryProcessor.buildQuery(queryInput, solrQuery);
      }
      return solrQuery;
   }

   public List<QueryProcessor> getQueryProcessors() {
      return queryProcessors;
   }
}
