package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.response.navigators.NavigatorItem;

public class SortNavigatorItem implements NavigatorItem {
   private String name;
   private String url;

   public SortNavigatorItem(String name, String url) {
      this.name = name;
      this.url = url;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public String getUrl() {
      return url;
   }

   @Override
   public String getDeselectUrl() {
      return null;
   }
}
