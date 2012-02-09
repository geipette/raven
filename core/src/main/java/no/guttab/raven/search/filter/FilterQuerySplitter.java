package no.guttab.raven.search.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FilterQuerySplitter {
   private static final Pattern GROUPED_FIELD_FQ = Pattern.compile("^([^:]+):\\((.+)\\)$");
   private static final Pattern GROUP_TOKEN = Pattern.compile("[^\"\\s\\\\]+(?:\\\\\\s[^\"\\s\\\\]+)*|(\"[^\"]+\")");
   private static final Set<String> BOOLEAN_OPERATORS = new HashSet<String>(Arrays.asList(
         "AND", "OR", "NOT"
   ));

   private List<String> fqs;

   public FilterQuerySplitter(List<String> fqs) {
      this.fqs = fqs;
   }

   public Set<String> splitFqs() {
      final HashSet<String> results = new HashSet<String>();
      for (String fq : fqs) {
         Matcher matcher = GROUPED_FIELD_FQ.matcher(fq);
         if (matcher.matches()) {
            results.addAll(splitGroupedFq(matcher.group(1), matcher.group(2)));
         } else {
            results.add(fq);
         }
      }
      return results;
   }

   private Collection<? extends String> splitGroupedFq(String indexFieldName, String groupedFq) {
      Set<String> result = new HashSet<String>();
      Matcher tokenMatcher = GROUP_TOKEN.matcher(groupedFq);
      while (tokenMatcher.find()) {
         String token = tokenMatcher.group(0);
         if (!BOOLEAN_OPERATORS.contains(token)) {
            result.add(indexFieldName + ":" + token);
         }
      }
      return result;
   }

}
