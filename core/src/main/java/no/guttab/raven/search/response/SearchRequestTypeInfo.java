package no.guttab.raven.search.response;

import no.guttab.raven.annotations.*;
import no.guttab.raven.common.ReverseLookupMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.annotations.AnnotationUtils.doForFirstAnnotatedFieldOn;
import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static no.guttab.raven.reflection.ClassUtils.isCollectionType;
import static org.apache.commons.lang3.reflect.FieldUtils.getDeclaredField;

public class SearchRequestTypeInfo {
    private ReverseLookupMap<String, String> indexFieldNameMap = new ReverseLookupMap<String, String>();
    private Class<?> requestType;
    private String sortFieldName;

    public SearchRequestTypeInfo(Class<?> requestType) {
        this.requestType = requestType;
        initializeIndexFieldMap(requestType);
        initializeSortField(requestType);
    }

    private void initializeSortField(Class<?> requestType) {
        doForFirstAnnotatedFieldOn(requestType, Sort.class, new AnnotatedFieldCallback<Sort>() {
            @Override
            public void doFor(Field field, Sort annotation) {
                sortFieldName = field.getName();
                indexFieldNameMap.put(sortFieldName, sortFieldName);
            }
        });
    }

    private void initializeIndexFieldMap(Class<?> requestType) {
        doForEachAnnotatedFieldOn(requestType, new AnnotationsWithCallback(FilterQuery.class, FacetField.class) {
            @Override
            public void doFor(Field field, Map<Class<? extends Annotation>, ? extends Annotation> annotations) {
                indexFieldNameMap.put(field.getName(), getIndexFieldName(field));
            }
        });
    }

    public String getSortFieldName() {
        return sortFieldName;
    }

    public String indexFieldNameFor(String requestFieldName) {
        return indexFieldNameMap.get(requestFieldName);
    }

    public String requestFieldNameFor(String indexFieldName) {
        return indexFieldNameMap.getByValue(indexFieldName);
    }

    public boolean isIndexFieldMultiSelect(String indexFieldName) {
        return isRequestFieldMultiSelect(requestFieldNameFor(indexFieldName));
    }

    public boolean isRequestFieldMultiSelect(String requestFieldName) {
        final Field field = getDeclaredField(requestType, requestFieldName, true);
        return isCollectionType(field);
    }

}
