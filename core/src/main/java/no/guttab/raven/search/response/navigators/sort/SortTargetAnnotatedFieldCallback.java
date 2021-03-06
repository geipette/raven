package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.SortTarget;
import no.guttab.raven.annotations.SortVariant;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;

class SortTargetAnnotatedFieldCallback implements AnnotatedFieldCallback<SortTarget> {
    private SortNavigation navigation;
    private final List<SortNavigatorItem> result;

    public SortTargetAnnotatedFieldCallback(SortNavigation navigation, List<SortNavigatorItem> result) {
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
            addNavigatorItemFor(sortVariant, field, sortTarget);
        }
    }

    private void addNavigatorItemFor(SortVariant sortVariant, Field field, SortTarget sortTarget) {
        String displayName = resolveSortVariantDisplayName(field, sortTarget, sortVariant);
        String sortCriteria = resolveSortCriteria(field, sortVariant);
        String url = resolveUrl(field, sortVariant, navigation.getSortFieldName());
        if (isSelected(sortCriteria)) {
            result.add(new SortNavigatorItem(
                    displayName,
                    sortCriteria,
                    url,
                    resolveResetUrl(field, sortVariant, navigation.getSortFieldName())));
        } else {
            result.add(new SortNavigatorItem(displayName, sortCriteria, url));
        }
    }

    private boolean isSelected(String sortCriteria) {
        return sortCriteria != null && sortCriteria.equals(navigation.getSortValue());
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
            return sortVariantDirectionPostfixed(displayName, sortVariant);
        }
        return displayName;
    }

    private boolean hasDisplayName(SortTarget sortTarget) {
        return !StringUtils.isEmpty(sortTarget.displayName());
    }

    private boolean hasMultipleSortVariants(SortTarget sortTarget) {
        return sortTarget.variants().length > 1;
    }

    private String sortVariantDirectionPostfixed(String value, SortVariant sortVariant) {
        return value + " " + sortVariant.value().parameterValue();
    }

    private String resolveResetUrl(Field field, SortVariant sortVariant, String sortFieldName) {
        return navigation.resetUrlFor(sortFieldName, resolveName(field, sortVariant));
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
        return sortVariantDirectionPostfixed(field.getName(), sortVariant);
    }

}
