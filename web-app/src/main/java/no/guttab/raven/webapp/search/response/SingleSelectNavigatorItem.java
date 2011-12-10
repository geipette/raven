package no.guttab.raven.webapp.search.response;

import org.apache.solr.client.solrj.response.FacetField;

public class SingleSelectNavigatorItem implements NavigatorItem {
   private FacetField.Count count;

   public SingleSelectNavigatorItem(FacetField.Count count) {
      this.count = count;
   }

   public String getName() {
      return count.getName();
   }

   public long getCount() {
      return count.getCount();
   }

   public String getUrl() {
      return "";
   }
}
