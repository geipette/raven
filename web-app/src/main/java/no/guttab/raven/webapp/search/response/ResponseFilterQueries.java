package no.guttab.raven.webapp.search.response;

import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;

public class ResponseFilterQueries {
   private Set<String> fqs;

   public ResponseFilterQueries(QueryResponse queryResponse) {
      fqs = getFqsFromQueryResponseHeader(queryResponse);
   }

   public String findFqForFacetField(FacetField facetField) {
      for (FacetField.Count count : facetField.getValues()) {
         if (fqs.contains(count.getAsFilterQuery())) {
            return count.getAsFilterQuery();
         }
      }
      return null;
   }

   @SuppressWarnings({"unchecked"})
   private Set<String> getFqsFromQueryResponseHeader(QueryResponse queryResponse) {
      NamedList<Object> header = queryResponse.getHeader();
      if (header == null) {
         return null;
      }
      SimpleOrderedMap<String> map = (SimpleOrderedMap<String>) header.get("params");
      return new HashSet<String>(map.getAll("fq"));
   }

}
