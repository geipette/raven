package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.response.Navigation;

public class SortNavigatorBuilder {

   private Navigation navigation;

   public SortNavigatorBuilder(Navigation navigation) {
      this.navigation = navigation;
   }

   public SortNavigator buildFor(Class<?> responseType) {
      return null;
   }
}
