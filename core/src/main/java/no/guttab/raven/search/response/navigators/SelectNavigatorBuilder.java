package no.guttab.raven.search.response.navigators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import no.guttab.raven.search.solr.Navigation;
import org.apache.solr.client.solrj.response.FacetField;

import static no.guttab.raven.search.solr.FilterQueries.extractFqCriteria;

public class SelectNavigatorBuilder {

   private Navigation navigation;

   public SelectNavigatorBuilder(Navigation navigation) {
      this.navigation = navigation;
   }

   public SelectNavigator buildFor(FacetField facetField) {
      final ItemBuilder itemBuilder = new ItemBuilder(facetField);
      itemBuilder.generateNavigatorItems(navigation.fqsFor(facetField));
      return new SelectNavigator(facetField, itemBuilder.items, itemBuilder.selectedItems);
   }

   private class ItemBuilder {
      private List<SelectNavigatorItem> items = new ArrayList<SelectNavigatorItem>();
      private List<SelectNavigatorItem> selectedItems = new ArrayList<SelectNavigatorItem>();
      private FacetField facetField;

      private ItemBuilder(FacetField facetField) {
         this.facetField = facetField;
      }

      private void generateNavigatorItems(Set<String> fqs) {
         for (FacetField.Count count : facetField.getValues()) {
            if (isFacetFieldCountSelected(fqs, count)) {
               addSelectedNavigatorItem(count);
            } else {
               addNavigatorItem(count);
            }
         }
      }

      private boolean isFacetFieldCountSelected(Set<String> fqs, FacetField.Count count) {
         return fqs.contains(count.getAsFilterQuery());
      }

      private void addNavigatorItem(FacetField.Count count) {
         final SelectNavigatorItem selectNavigatorItem =
               new SelectNavigatorItem(count, buildUrlForFilterQuery(count.getAsFilterQuery()));
         items.add(selectNavigatorItem);
      }

      private void addSelectedNavigatorItem(FacetField.Count count) {
         final SelectNavigatorItem navigatorItem =
               new SelectNavigatorItem(
                     count,
                     buildUrlForFilterQuery(count.getAsFilterQuery()),
                     buildResetUrlForFilterQuery(count.getAsFilterQuery()));
         selectedItems.add(navigatorItem);
      }

      private String buildResetUrlForFilterQuery(String fq) {
         final String fqCriteria = extractFqCriteria(fq);
         return navigation.resetUrlFor(facetField.getName(), fqCriteria);

      }

      private String buildUrlForFilterQuery(String fq) {
         final String fqCriteria = extractFqCriteria(fq);
         return navigation.urlFor(facetField.getName(), fqCriteria);
      }
   }

}
