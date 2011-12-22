package no.guttab.raven.search.solr;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;

public class QueryResponseUtils {
   private static final Pattern GROUPED_FIELD_FQ = Pattern.compile("^([^:]+):\\((.+)\\)$");
   private static final Pattern GROUP_TOKEN = Pattern.compile("([^\"\\s]+)|(\"[^\"]+\")");
   private static final Set<String> BOOLEAN_OPERATORS = new HashSet<String>(Arrays.asList(
         "AND", "OR", "NOT"
   ));

   private QueryResponseUtils() {
   }

   @SuppressWarnings({"unchecked"})
   public static Set<String> getFqsFromHeader(QueryResponse queryResponse) {
      final NamedList<Object> header = queryResponse.getHeader();
      if (header == null) {
         return null;
      }
      final SimpleOrderedMap<String> map = (SimpleOrderedMap<String>) header.get("params");
      return createFqsFromSimpleOrderedMap(map);
   }

   private static Set<String> createFqsFromSimpleOrderedMap(SimpleOrderedMap<String> map) {
      final HashSet<String> results = new HashSet<String>();
      List<String> fqs = map.getAll("fq");
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

   private static Collection<? extends String> splitGroupedFq(String indexFieldName, String groupedFq) {
      Set<String> fqs = new HashSet<String>();
      Matcher tokenMatcher = GROUP_TOKEN.matcher(groupedFq);
      while (tokenMatcher.find()) {
         String token = tokenMatcher.group(0);
         if (!BOOLEAN_OPERATORS.contains(token)) {
            fqs.add(indexFieldName + ":" + token);
         }
      }
      return fqs;
   }

}
