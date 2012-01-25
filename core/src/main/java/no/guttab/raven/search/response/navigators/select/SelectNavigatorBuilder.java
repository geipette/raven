package no.guttab.raven.search.response.navigators.select;

import no.guttab.raven.search.solr.Navigation;
import org.apache.solr.client.solrj.response.FacetField;

public class SelectNavigatorBuilder {

   private Navigation navigation;

   public SelectNavigatorBuilder(Navigation navigation) {
      this.navigation = navigation;
   }

   public SelectNavigator buildFor(FacetField facetField) {
      final SelectNavigatorItemBuilder itemBuilder = new SelectNavigatorItemBuilder(navigation, facetField);
      return new SelectNavigator(facetField, itemBuilder.getItems(), itemBuilder.getSelectedItems());
   }


}
