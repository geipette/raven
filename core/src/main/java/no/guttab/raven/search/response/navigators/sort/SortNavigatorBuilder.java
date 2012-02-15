package no.guttab.raven.search.response.navigators.sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.SortTarget;
import no.guttab.raven.search.response.Navigation;
import no.guttab.raven.search.response.navigators.ImmutableNavigatorItems;
import org.apache.commons.lang3.StringUtils;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;

public class SortNavigatorBuilder {
   private Navigation navigation;
   private Class<?> responseType;

   private List<SortNavigatorItem> selectedItems = new ArrayList<SortNavigatorItem>();
   private List<SortNavigatorItem> items = new ArrayList<SortNavigatorItem>();

   public SortNavigatorBuilder(Class<?> responseType, Navigation navigation) {
      this.responseType = responseType;
      this.navigation = navigation;
      buildAndAddSortNavigatorItems();
   }

   private void buildAndAddSortNavigatorItems() {
      List<SortNavigatorItem> allItems = buildSortNavigatorItems();
      for (SortNavigatorItem item : allItems) {
         addSortNavigatorItem(item);
      }
   }

   private void addSortNavigatorItem(SortNavigatorItem item) {
      if (isSelectedItem(item)) {
         selectedItems.add(item);
      } else {
         items.add(item);
      }
   }

   private boolean isSelectedItem(SortNavigatorItem item) {
      return false;
   }

   private List<SortNavigatorItem> buildSortNavigatorItems() {
      final List<SortNavigatorItem> result = new ArrayList<SortNavigatorItem>();
      doForEachAnnotatedFieldOn(responseType, SortTarget.class, new SortTargetAnnotatedFieldCallback(navigation, result));
      return result;
   }

   public SortNavigator build() {
      return new SortNavigator(new ImmutableNavigatorItems<SortNavigatorItem>(items, selectedItems));
   }

   private static class SortTargetAnnotatedFieldCallback implements AnnotatedFieldCallback<SortTarget> {
      private Navigation navigation;
      private final List<SortNavigatorItem> result;

      public SortTargetAnnotatedFieldCallback(Navigation navigation, List<SortNavigatorItem> result) {
         this.navigation = navigation;
         this.result = result;
      }

      @Override
      public void doFor(Field field, SortTarget annotation) {
         if (navigationHasSortField()) {
            addNavigatorItemsFor(field, annotation);
         } else {
            throw new NoSortFieldDefinedException();
         }
      }

      private void addNavigatorItemsFor(Field field, SortTarget annotation) {
         String displayName = resolveDisplayName(field, annotation);
         String url = resolveUrl(field, annotation, navigation.getSortField());
         SortNavigatorItem item = new SortNavigatorItem(displayName, url);
         result.add(item);
      }

      private boolean navigationHasSortField() {
         return !StringUtils.isEmpty(navigation.getSortField());
      }

      private String resolveUrl(Field field, SortTarget annotation, String sortField) {
         return "?" + sortField + "=" + resolveName(field, annotation);
      }

      private String resolveName(Field field, SortTarget annotation) {
         return field.getName();
//         return hasName(annotation) ? annotation.name() : field.getName();
      }

      private boolean hasName(SortTarget annotation) {
         return false;
//         return !StringUtils.isEmpty(annotation.name());
      }

      private boolean hasDisplayName(SortTarget annotation) {
         return !StringUtils.isEmpty(annotation.displayName());
      }

      private String resolveDisplayName(Field field, SortTarget annotation) {
         return hasDisplayName(annotation) ? annotation.displayName() : field.getName();
      }

   }

   public static class NoSortFieldDefinedException extends RuntimeException {
      public NoSortFieldDefinedException() {
         super("No @Sort annotation found on request object. Please specify one when using @SortTarget or @SortOrder");
      }
   }
}
