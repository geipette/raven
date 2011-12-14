package no.guttab.raven.search.solr;

import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;

public class QueryResponseUtils {
   private QueryResponseUtils() {
   }

   @SuppressWarnings({"unchecked"})
   public static Set<String> getFqsFromHeader(QueryResponse queryResponse) {
      NamedList<Object> header = queryResponse.getHeader();
      if (header == null) {
         return null;
      }
      SimpleOrderedMap<String> map = (SimpleOrderedMap<String>) header.get("params");
      return new HashSet<String>(map.getAll("fq"));
   }
}
