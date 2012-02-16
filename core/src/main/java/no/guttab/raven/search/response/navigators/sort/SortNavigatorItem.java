package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.response.navigators.NavigatorItem;

public class SortNavigatorItem implements NavigatorItem {
   private String name;
   private String url;
   private String sortCriteria;

   public SortNavigatorItem(String name, String url, String sortCriteria) {
      this.name = name;
      this.url = url;
      this.sortCriteria = sortCriteria;
   }

   public String getSortCriteria() {
      return sortCriteria;
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
