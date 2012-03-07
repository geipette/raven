package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.annotations.SortOrder;
import no.guttab.raven.annotations.SortOrders;
import no.guttab.raven.annotations.SortTarget;
import no.guttab.raven.search.response.navigators.ImmutableNavigatorItems;

import java.util.ArrayList;
import java.util.List;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;

class SortNavigatorBuilder {
    private SortNavigation navigation;
    private Class<?> responseType;

    private List<SortNavigatorItem> selectedItems = new ArrayList<SortNavigatorItem>();
    private List<SortNavigatorItem> items = new ArrayList<SortNavigatorItem>();

    public SortNavigatorBuilder(Class<?> responseType, SortNavigation navigation) {
        this.responseType = responseType;
        this.navigation = navigation;
        buildAndAddSortNavigatorItems();
    }

    public SortNavigator build() {
        return new SortNavigator(new ImmutableNavigatorItems<SortNavigatorItem>(items, selectedItems));
    }

    private void buildAndAddSortNavigatorItems() {
        final List<SortNavigatorItem> allItems = buildSortNavigatorItems();
        for (SortNavigatorItem item : allItems) {
            addSortNavigatorItem(item);
        }
    }

    private List<SortNavigatorItem> buildSortNavigatorItems() {
        final List<SortNavigatorItem> result = new ArrayList<SortNavigatorItem>();
        doForEachAnnotatedFieldOn(responseType, SortTarget.class, new SortTargetAnnotatedFieldCallback(navigation, result));
        SortOrders sortOrders = responseType.getAnnotation(SortOrders.class);
        if (sortOrders != null && sortOrders.value() != null) {
            for (SortOrder sortOrder : sortOrders.value()) {
                result.add(new SortNavigatorItem(sortOrder.value(), navigation.urlFor(navigation.getSortFieldName(), sortOrder.sortOrdering()), sortOrder.sortOrdering()));
            }
        }
        return result;
    }

    private void addSortNavigatorItem(SortNavigatorItem item) {
        if (isSelectedItem(item)) {
            selectedItems.add(item);
        } else {
            items.add(item);
        }
    }

    private boolean isSelectedItem(SortNavigatorItem item) {
        return item.getSortCriteria().equals(navigation.getSortValue());
    }

    public static class NoSortFieldException extends RuntimeException {
        public NoSortFieldException() {
            super("No @Sort annotation found on request object. Please specify one when using @SortTarget or @SortOrder");
        }
    }
}
