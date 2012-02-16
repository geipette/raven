package no.guttab.raven.search.filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.guttab.raven.search.QueryResponseHeaderParams;
import org.apache.solr.client.solrj.response.FacetField;

public class FilterQueries {
   private Set<String> fqs;

   public static FilterQueries filterQueriesFor(QueryResponseHeaderParams queryResponseHeaderParams) {
      return queryResponseHeaderParams.getFilterQueries();
   }

   public FilterQueries(List<String> fqs) {
      this.fqs = new FilterQuerySplitter(fqs).splitFqs();
   }

   public Set<String> findFqCriteriasFor(FacetField facetField) {
      Set<String> resultFqs = findFqsFor(facetField);
      if (fqs == null) {
         return null;
      }
      return extractFqCriterias(resultFqs);
   }

   public Set<String> findFqsFor(FacetField facetField) {
      if (facetField.getValues() == null) {
         return Collections.emptySet();
      }
      final Set<String> resultFqs = new HashSet<String>();
      for (FacetField.Count count : facetField.getValues()) {
         if (fqs.contains(count.getAsFilterQuery())) {
            resultFqs.add(count.getAsFilterQuery());
         }
      }
      return resultFqs;
   }

   public static Set<String> extractFqCriterias(Set<String> resultFqs) {
      Set<String> criterias = new HashSet<String>();
      for (String resultFq : resultFqs) {
         criterias.add(extractFqCriteria(resultFq));
      }
      return criterias;
   }

   public static String extractFqCriteria(String fq) {
      int indexOfFqDelimiter = fq.indexOf(':');
      if (indexOfFqDelimiter < 0 || fq.length() < indexOfFqDelimiter + 1) {
         return "";
      }

      return fq.substring(indexOfFqDelimiter + 1);
   }

}
