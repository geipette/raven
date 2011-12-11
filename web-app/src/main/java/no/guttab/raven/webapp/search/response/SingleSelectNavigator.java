package no.guttab.raven.webapp.search.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

import static no.guttab.raven.webapp.search.response.FilterQueries.extractFqCriteria;

public class SingleSelectNavigator implements Navigator<SingleSelectNavigatorItem> {
   private FacetField facetField;
   private NavigatorUrls navigatorUrls;
   private ResponseFilterQueries responseFilterQueries;
   private List<SingleSelectNavigatorItem> items;
   private SingleSelectNavigatorItem selectedItem;

   public SingleSelectNavigator(
         FacetField facetField, NavigatorUrls navigatorUrls, ResponseFilterQueries responseFilterQueries) {
      this.facetField = facetField;
      this.navigatorUrls = navigatorUrls;
      this.responseFilterQueries = responseFilterQueries;

      initItems();
   }

   private void initItems() {
      items = new ArrayList<SingleSelectNavigatorItem>();
      final String fq = responseFilterQueries.findFqFor(facetField);
      if (fq == null) {
         generateNavigatorItems();
      } else {
         generateSelectedNavigatorItem(fq);
      }
   }

   private void generateNavigatorItems() {
      for (FacetField.Count count : facetField.getValues()) {
         String fqCriteria = extractFqCriteria(count.getAsFilterQuery());
         String url = buildNavigatorUrl(fqCriteria);
         SingleSelectNavigatorItem navigatorItem = new SingleSelectNavigatorItem(count, url);
         items.add(navigatorItem);
      }
   }

   private void generateSelectedNavigatorItem(String fq) {
      for (FacetField.Count count : facetField.getValues()) {
         if (isSelected(count, fq)) {
            String fqCriteria = responseFilterQueries.findFqCriteriaFor(facetField);
            String url = navigatorUrls.buildUrlFor(facetField.getName(), fqCriteria);
            String deselectUrl = navigatorUrls.buildUrlFor(facetField.getName());
            selectedItem = new SingleSelectNavigatorItem(count, url, deselectUrl);
            items.add(selectedItem);
            return;
         }
      }
   }

   private boolean isSelected(FacetField.Count count, String fq) {
      return count.getAsFilterQuery().equals(fq);
   }

   private String buildNavigatorUrl(String fqCriteria) {
      return navigatorUrls.buildUrlFor(facetField.getName(), fqCriteria);
   }

   public List<SingleSelectNavigatorItem> getItems() {
      return Collections.unmodifiableList(items);
   }

   @Override
   public boolean isSelected() {
      return selectedItem != null;
   }

   @Override
   public SingleSelectNavigatorItem getSelectedItem() {
      return selectedItem;
   }

}
