package no.guttab.raven.search.response.navigators.select;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import no.guttab.raven.search.response.navigators.ImmutableNavigatorItems;
import no.guttab.raven.search.response.navigators.NavigatorItems;
import no.guttab.raven.search.solr.Navigation;
import org.apache.solr.client.solrj.response.FacetField;

import static no.guttab.raven.search.solr.FilterQueries.extractFqCriteria;

class SelectNavigatorItemsBuilder {
   private Navigation navigation;
   private FacetField facetField;
   private Set<String> fqs;
   private List<SelectNavigatorItem> items = new ArrayList<SelectNavigatorItem>();
   private List<SelectNavigatorItem> selectedItems = new ArrayList<SelectNavigatorItem>();

   SelectNavigatorItemsBuilder(Navigation navigation, FacetField facetField) {
      this.navigation = navigation;
      this.facetField = facetField;
   }

   public static NavigatorItems<SelectNavigatorItem> build(Navigation navigation, FacetField facetField) {
      final SelectNavigatorItemsBuilder builder = new SelectNavigatorItemsBuilder(navigation, facetField);

      builder.initFqSetForFacetField();
      builder.generateNavigatorItems();

      return new ImmutableNavigatorItems<SelectNavigatorItem>(builder.items, builder.selectedItems);
   }

   private void generateNavigatorItems() {
      if (facetField.getValueCount() == 0) {
         return;
      }

      for (FacetField.Count count : facetField.getValues()) {
         generateNavigatorItemForFacetFieldCount(count);
      }
   }

   private void initFqSetForFacetField() {
      fqs = navigation.fqsFor(facetField);
   }

   private void generateNavigatorItemForFacetFieldCount(FacetField.Count count) {
      final String filterQueryUrl = buildUrlForFilterQuery(count.getAsFilterQuery());
      if (isFacetFieldCountSelected(count)) {
         selectedItems.add(createSelectedNavigatorItem(count, filterQueryUrl));
      } else {
         items.add(createNavigatorItem(count, filterQueryUrl));
      }
   }

   private SelectNavigatorItem createNavigatorItem(FacetField.Count count, String filterQueryUrl) {
      return new SelectNavigatorItem(count, filterQueryUrl);
   }

   private SelectNavigatorItem createSelectedNavigatorItem(FacetField.Count count, String filterQueryUrl) {
      final String deselectUrl = buildResetUrlForFilterQuery(facetField.getName(), count.getAsFilterQuery());
      return new SelectNavigatorItem(count, filterQueryUrl, deselectUrl);
   }

   private boolean isFacetFieldCountSelected(FacetField.Count count) {
      return fqs.contains(count.getAsFilterQuery());
   }

   private String buildResetUrlForFilterQuery(String facetFieldName, String fq) {
      final String fqCriteria = extractFqCriteria(fq);
      return navigation.resetUrlFor(facetFieldName, fqCriteria);
   }

   private String buildUrlForFilterQuery(String fq) {
      final String fqCriteria = extractFqCriteria(fq);
      return navigation.urlFor(facetField.getName(), fqCriteria);
   }

}
