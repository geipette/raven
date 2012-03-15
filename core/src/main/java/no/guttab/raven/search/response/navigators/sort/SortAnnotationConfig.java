package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.Sort;

import java.lang.reflect.Field;

import static no.guttab.raven.annotations.AnnotationUtils.doForFirstAnnotatedFieldOn;

public class SortAnnotationConfig implements SortConfig {
    private String sortRequestFieldName;

    public SortAnnotationConfig(Class<?> requestType) {
        initializeSortField(requestType);
    }

    @Override
    public String getSortRequestFieldName() {
        return sortRequestFieldName;
    }

    private void initializeSortField(Class<?> requestType) {
        doForFirstAnnotatedFieldOn(requestType, Sort.class, new AnnotatedFieldCallback<Sort>() {
            @Override
            public void doFor(Field field, Sort annotation) {
                sortRequestFieldName = field.getName();
            }
        });
    }

}
