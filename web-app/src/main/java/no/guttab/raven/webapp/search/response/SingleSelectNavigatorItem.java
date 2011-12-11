package no.guttab.raven.webapp.search.response;

import org.apache.solr.client.solrj.response.FacetField;

public class SingleSelectNavigatorItem implements NavigatorItem {
   private String deselectUrl;
   private FacetField.Count count;
   private String url;

   public SingleSelectNavigatorItem(FacetField.Count count, String url) {
      this.count = count;
      this.url = url;
   }

   public SingleSelectNavigatorItem(FacetField.Count count, String url, String deselectUrl) {
      this.count = count;
      this.url = url;
      this.deselectUrl = deselectUrl;
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

   public String getDeselectUrl() {
      return deselectUrl;
   }
}
