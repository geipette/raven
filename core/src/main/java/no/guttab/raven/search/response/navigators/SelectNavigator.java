package no.guttab.raven.search.response.navigators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import no.guttab.raven.search.response.NavigatorUrls;
import no.guttab.raven.search.solr.FilterQueries;
import org.apache.solr.client.solrj.response.FacetField;

import static no.guttab.raven.search.solr.FilterQueries.extractFqCriteria;
import static org.springframework.util.CollectionUtils.isEmpty;

public class SelectNavigator implements Navigator<SelectNavigatorItem> {
   private FacetField facetField;
   private NavigatorUrls navigatorUrls;
   private FilterQueries filterQueries;
   private List<SelectNavigatorItem> items = new ArrayList<SelectNavigatorItem>();
   private List<SelectNavigatorItem> selectedItems = new ArrayList<SelectNavigatorItem>();

   public SelectNavigator(FacetField facetField, NavigatorUrls navigatorUrls, FilterQueries filterQueries) {
      this.facetField = facetField;
      this.navigatorUrls = navigatorUrls;
      this.filterQueries = filterQueries;

      initItems();
   }

   @Override
   public String getDisplayName() {
      return facetField.getName();
   }

   private void initItems() {
      final Set<String> fqs = filterQueries.findFqsFor(facetField);
      if (isEmpty(fqs)) {
         generateNavigatorItems();
      } else {
         generateSelectedNavigatorItems(fqs);
      }
   }

   private void generateNavigatorItems() {
      for (FacetField.Count count : facetField.getValues()) {
         SelectNavigatorItem navigatorItem =
               generateNavigatorItem(count, buildUrlForFilterQuery(count.getAsFilterQuery()));
         items.add(navigatorItem);
      }
   }

   private void generateSelectedNavigatorItems(Set<String> fqs) {
      for (String fq : fqs) {
         for (FacetField.Count count : facetField.getValues()) {
            if (isSelected(count, fq)) {
               SelectNavigatorItem navigatorItem =
                     generateSelectedNavigatorItem(count, buildUrlForFilterQuery(fq));
               selectedItems.add(navigatorItem);
            } else {
               items.add(generateNavigatorItem(count, buildUrlForFilterQuery(count.getAsFilterQuery())));
            }
         }
      }
   }

   private SelectNavigatorItem generateNavigatorItem(FacetField.Count count, String url) {
      return new SelectNavigatorItem(count, url);
   }

   private SelectNavigatorItem generateSelectedNavigatorItem(FacetField.Count count, String url) {
      String deselectUrl = navigatorUrls.resetUrlFor(facetField.getName());
      return new SelectNavigatorItem(count, url, deselectUrl);
   }

   private String buildUrlForFilterQuery(String fq) {
      final String fqCriteria = extractFqCriteria(fq);
      return navigatorUrls.buildUrlFor(facetField.getName(), fqCriteria);
   }

   private boolean isSelected(FacetField.Count count, String fq) {
      return count.getAsFilterQuery().equals(fq);
   }

   public List<SelectNavigatorItem> getItems() {
      return Collections.unmodifiableList(items);
   }

   @Override
   public boolean isSelected() {
      return !isEmpty(selectedItems);
   }

   @Override
   public SelectNavigatorItem getFirstSelectedItem() {
      final Iterator<SelectNavigatorItem> it = selectedItems.iterator();
      return it.hasNext() ? it.next() : null;
   }

   @Override
   public List<SelectNavigatorItem> getSelectedItems() {
      return selectedItems;
   }


}
