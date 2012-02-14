package no.guttab.raven.search.response.navigators.select;

import no.guttab.raven.search.response.navigators.FacetNavigatorItem;
import org.apache.solr.client.solrj.response.FacetField;

public class SelectNavigatorItem implements FacetNavigatorItem {
   private String deselectUrl;
   private FacetField.Count count;
   private String url;

   public SelectNavigatorItem(FacetField.Count count, String url) {
      this.count = count;
      this.url = url;
   }

   public SelectNavigatorItem(FacetField.Count count, String url, String deselectUrl) {
      this.count = count;
      this.url = url;
      this.deselectUrl = deselectUrl;
   }

   @Override
   public String getName() {
      return count.getName();
   }

   @Override
   public long getCount() {
      return count.getCount();
   }

   @Override
   public String getUrl() {
      return url;
   }

   @Override
   public String getDeselectUrl() {
      return deselectUrl;
   }
}
