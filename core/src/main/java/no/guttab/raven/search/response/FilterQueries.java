package no.guttab.raven.search.response;

public class FilterQueries {
   private FilterQueries() {
   }

   public static String extractFqCriteria(String fq) {
      int indexOfFqDelimiter = fq.indexOf(':');
      if (indexOfFqDelimiter < 0 || fq.length() < indexOfFqDelimiter + 1) {
         return "";
      }

      return fq.substring(indexOfFqDelimiter + 1);
   }


}
