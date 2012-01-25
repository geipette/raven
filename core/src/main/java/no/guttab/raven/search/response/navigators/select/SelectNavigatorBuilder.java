package no.guttab.raven.search.response.navigators.select;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import no.guttab.raven.search.solr.Navigation;
import org.apache.solr.client.solrj.response.FacetField;

import static no.guttab.raven.search.solr.FilterQueries.extractFqCriteria;

public class SelectNavigatorBuilder {

   private Navigation navigation;
   private List<SelectNavigatorItem> items = new ArrayList<SelectNavigatorItem>();
   private List<SelectNavigatorItem> selectedItems = new ArrayList<SelectNavigatorItem>();

   public SelectNavigatorBuilder(Navigation navigation) {
      this.navigation = navigation;
   }

   public SelectNavigator buildFor(FacetField facetField) {
      generateNavigatorItemsForEachFacetFieldValue(facetField);
      return new SelectNavigator(facetField, items, selectedItems);
   }

   private void generateNavigatorItemsForEachFacetFieldValue(FacetField facetField) {
      if (facetField.getValueCount() == 0) {
         return;
      }
      final Set<String> fqs = navigation.fqsFor(facetField);
      for (FacetField.Count count : facetField.getValues()) {
         if (isFacetFieldCountSelected(fqs, count)) {
            addSelectedNavigatorItem(facetField.getName(), count);
         } else {
            addNavigatorItem(facetField.getName(), count);
         }
      }
   }

   private boolean isFacetFieldCountSelected(Set<String> fqs, FacetField.Count count) {
      return fqs.contains(count.getAsFilterQuery());
   }

   private void addSelectedNavigatorItem(String facetFieldName, FacetField.Count count) {
      final String filterQueryUrl = buildUrlForFilterQuery(facetFieldName, count.getAsFilterQuery());
      final String filterQueryDeselectUrl = buildResetUrlForFilterQuery(facetFieldName, count.getAsFilterQuery());
      selectedItems.add(new SelectNavigatorItem(count, filterQueryUrl, filterQueryDeselectUrl));
   }

   private void addNavigatorItem(String facetFieldName, FacetField.Count count) {
      final SelectNavigatorItem selectNavigatorItem =
            new SelectNavigatorItem(count, buildUrlForFilterQuery(facetFieldName, count.getAsFilterQuery()));
      items.add(selectNavigatorItem);
   }

   private String buildResetUrlForFilterQuery(String facetFieldName, String fq) {
      final String fqCriteria = extractFqCriteria(fq);
      return navigation.resetUrlFor(facetFieldName, fqCriteria);
   }

   private String buildUrlForFilterQuery(String facetFieldName, String fq) {
      final String fqCriteria = extractFqCriteria(fq);
      return navigation.urlFor(facetFieldName, fqCriteria);
   }

}
