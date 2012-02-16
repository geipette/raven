package no.guttab.raven.search.response.navigators.sort;

import java.lang.reflect.Field;
import java.util.List;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.SortTarget;
import no.guttab.raven.annotations.SortVariant;
import no.guttab.raven.search.response.Navigation;
import org.apache.commons.lang3.StringUtils;

import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static no.guttab.raven.annotations.SortDirection.ASCENDING;

class SortTargetAnnotatedFieldCallback implements AnnotatedFieldCallback<SortTarget> {
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
         throw new SortNavigatorBuilder.NoSortFieldException();
      }
   }

   private boolean navigationHasSortField() {
      return !StringUtils.isEmpty(navigation.getSortFieldName());
   }

   private void addNavigatorItemsFor(Field field, SortTarget sortTarget) {
      for (SortVariant sortVariant : sortTarget.variants()) {
         String displayName = resolveSortVariantDisplayName(field, sortTarget, sortVariant);
         String url = resolveUrl(field, sortVariant, navigation.getSortFieldName());
         SortNavigatorItem item = new SortNavigatorItem(displayName, url, resolveSortCriteria(field, sortVariant));
         result.add(item);
      }
   }

   private String resolveSortCriteria(Field field, SortVariant sortVariant) {
      return getIndexFieldName(field) + " " + sortVariant.value().parameterValue();
   }

   private String resolveSortVariantDisplayName(Field field, SortTarget sortTarget, SortVariant sortVariant) {
      if (hasDisplayName(sortVariant)) {
         return sortVariant.displayName();
      } else {
         return resolveSortTargetDisplayName(field, sortTarget, sortVariant);
      }
   }

   private boolean hasDisplayName(SortVariant sortVariant) {
      return !StringUtils.isEmpty(sortVariant.displayName());
   }

   private String resolveSortTargetDisplayName(Field field, SortTarget sortTarget, SortVariant sortVariant) {
      String displayName = hasDisplayName(sortTarget) ? sortTarget.displayName() : field.getName();
      if (hasMultipleSortVariants(sortTarget)) {
         return sortVariantDirectionPrefixed(displayName, sortVariant);
      }
      return displayName;
   }

   private boolean hasDisplayName(SortTarget sortTarget) {
      return !StringUtils.isEmpty(sortTarget.displayName());
   }

   private boolean hasMultipleSortVariants(SortTarget sortTarget) {
      return sortTarget.variants().length > 1;
   }

   private String sortVariantDirectionPrefixed(String value, SortVariant sortVariant) {
      return sortVariant.value() == ASCENDING ? value : "-" + value;
   }

   private String resolveUrl(Field field, SortVariant annotation, String sortField) {
      return navigation.urlFor(sortField, resolveName(field, annotation));
   }

   private String resolveName(Field field, SortVariant annotation) {
      return hasName(annotation) ? annotation.name() : defaultSortVariantName(field, annotation);
   }

   private boolean hasName(SortVariant sortVariant) {
      return !StringUtils.isEmpty(sortVariant.name());
   }

   private String defaultSortVariantName(Field field, SortVariant sortVariant) {
      return sortVariantDirectionPrefixed(field.getName(), sortVariant);
   }

}
