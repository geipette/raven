package no.guttab.raven.webapp.search.response;

import org.apache.solr.client.solrj.response.FacetField;

public class SingleSelectNavigatorItem implements NavigatorItem {
   private FacetField.Count count;
   private String url;

   public SingleSelectNavigatorItem(FacetField.Count count, String url) {
      this.count = count;
      this.url = url;
   }

   public String getName() {
      return count.getName();
   }

   public long getCount() {
      return count.getCount();
   }

   public String getUrl() {
      return url;
   }
}
