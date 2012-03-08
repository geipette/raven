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
        buildNavigatorItemsForSortTargets(result);
        buildNavigatorItemsForSortOrders(result);
        return result;
    }

    private void buildNavigatorItemsForSortTargets(List<SortNavigatorItem> result) {
        doForEachAnnotatedFieldOn(responseType, SortTarget.class, new SortTargetAnnotatedFieldCallback(navigation, result));
    }

    private void buildNavigatorItemsForSortOrders(List<SortNavigatorItem> result) {
        SortOrders sortOrders = responseType.getAnnotation(SortOrders.class);
        if (sortOrders != null && sortOrders.value() != null) {
            for (SortOrder sortOrder : sortOrders.value()) {
                result.add(createNavigatorItemFor(sortOrder));
            }
        }
    }

    private SortNavigatorItem createNavigatorItemFor(SortOrder sortOrder) {
        if (isSelected(sortOrder)) {
            return new SortNavigatorItem(sortOrder.value(), sortOrder.sortCriteria(), urlFor(sortOrder), resetUrlFor(sortOrder));
        } else {
            return new SortNavigatorItem(sortOrder.value(), sortOrder.sortCriteria(), urlFor(sortOrder));
        }
    }

    private String resetUrlFor(SortOrder sortOrder) {
        return navigation.resetUrlFor(navigation.getSortFieldName(), sortOrder.sortCriteria());
    }

    private String urlFor(SortOrder sortOrder) {
        return navigation.urlFor(navigation.getSortFieldName(), sortOrder.sortCriteria());
    }

    private void addSortNavigatorItem(SortNavigatorItem item) {
        if (isSelected(item)) {
            selectedItems.add(item);
        } else {
            items.add(item);
        }
    }

    private boolean isSelected(SortOrder sortOrder) {
        return isSelected(sortOrder.sortCriteria());
    }

    private boolean isSelected(SortNavigatorItem item) {
        return isSelected(item.getSortCriteria());
    }

    private boolean isSelected(String sortCriteria) {
        return sortCriteria != null && sortCriteria.equals(navigation.getSortValue());
    }

    public static class NoSortFieldException extends RuntimeException {
        public NoSortFieldException() {
            super("No @Sort annotation found on request object. Please specify one when using @SortTarget or @SortOrder");
        }
    }
}
