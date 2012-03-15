package no.guttab.raven.search.response.navigators;

import no.guttab.raven.annotations.AnnotationsWithCallback;
import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.common.ReverseLookupMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import static no.guttab.raven.annotations.AnnotationUtils.doForEachAnnotatedFieldOn;
import static no.guttab.raven.annotations.SearchAnnotationUtils.getIndexFieldName;
import static no.guttab.raven.reflection.ClassUtils.isCollectionType;
import static org.apache.commons.lang3.reflect.FieldUtils.getDeclaredField;

public class SearchRequestTypeInfo {
    private ReverseLookupMap<String, String> indexFieldNameMap = new ReverseLookupMap<String, String>();
    private Class<?> requestType;

    public SearchRequestTypeInfo(Class<?> requestType) {
        this.requestType = requestType;
        initializeIndexFieldMap(requestType);
    }

    private void initializeIndexFieldMap(Class<?> requestType) {
        doForEachAnnotatedFieldOn(requestType, new AnnotationsWithCallback(FilterQuery.class, FacetField.class) {
            @Override
            public void doFor(Field field, Map<Class<? extends Annotation>, ? extends Annotation> annotations) {
                indexFieldNameMap.put(field.getName(), getIndexFieldName(field));
            }
        });
    }

    public String indexFieldNameFor(String requestFieldName) {
        final String indexFieldName = indexFieldNameMap.get(requestFieldName);
        return indexFieldName == null ? requestFieldName : indexFieldName;
    }

    public String requestFieldNameFor(String indexFieldName) {
        final String requestFieldName = indexFieldNameMap.getByValue(indexFieldName);
        return requestFieldName == null ? indexFieldName : requestFieldName;
    }

    public boolean isIndexFieldMultiSelect(String indexFieldName) {
        return isRequestFieldMultiSelect(requestFieldNameFor(indexFieldName));
    }

    public boolean isRequestFieldMultiSelect(String requestFieldName) {
        final Field field = getDeclaredField(requestType, requestFieldName, true);
        return isCollectionType(field);
    }
}
