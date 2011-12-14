package no.guttab.raven.search.solr;

import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.response.FacetField;

public class FilterQueries {
   private Set<String> fqs;

   public FilterQueries(Set<String> fqs) {
      this.fqs = fqs;
   }

   public Set<String> findFqCriteriasFor(FacetField facetField) {
      Set<String> resultFqs = findFqsFor(facetField);
      if (fqs == null) {
         return null;
      }
      return extractFqCriterias(resultFqs);
   }

   public Set<String> findFqsFor(FacetField facetField) {
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
