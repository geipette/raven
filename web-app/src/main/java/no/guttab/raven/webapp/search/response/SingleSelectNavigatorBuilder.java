package no.guttab.raven.webapp.search.response;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.springframework.util.CollectionUtils;

public class SingleSelectNavigatorBuilder {
   private String indexFieldName;

   public SingleSelectNavigatorBuilder(String indexFieldName) {
      this.indexFieldName = indexFieldName;
   }

   public SingleSelectNavigator buildNavigator(QueryResponse queryResponse) {
      final FacetField facetField = facetField(queryResponse);
      if (facetField == null) {
         return null;
      }
      return new SingleSelectNavigator(facetField, selectedItem(facetField, queryFqs(queryResponse)));
   }

   private FacetField facetField(QueryResponse queryResponse) {
      return queryResponse.getFacetField(indexFieldName);
   }

   private SingleSelectNavigatorItem selectedItem(FacetField facetField, List<String> queryFqs) {
      if (!CollectionUtils.isEmpty(queryFqs)) {
         for (String queryFq : queryFqs) {
            for (FacetField.Count count : facetField.getValues()) {
               if (count.getAsFilterQuery().equals(queryFq)) {
                  return new SingleSelectNavigatorItem(count);
               }
            }
         }
      }
      return null;
   }

   @SuppressWarnings({"unchecked"})
   private List<String> queryFqs(QueryResponse queryResponse) {
      NamedList<Object> header = queryResponse.getHeader();
      if (header == null) {
         return null;
      }
      SimpleOrderedMap<String> map = (SimpleOrderedMap<String>) header.get("params");
      return map.getAll("fq");
   }

}
