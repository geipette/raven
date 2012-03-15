package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.Page;
import no.guttab.raven.annotations.StartIndex;

import java.lang.reflect.Field;

import static no.guttab.raven.annotations.AnnotationUtils.doForFirstAnnotatedFieldOn;

public class PageAnnotationConfig implements PageConfig {
    private String pageRequestFieldName;
    private String startRequestFieldName;
    private Integer resultsPerPage;

    public PageAnnotationConfig(Class<?> requestType) {
        initializeResultsPerPage(requestType);
        initializeStartIndex(requestType);
    }

    @Override
    public String getPageRequestFieldName() {
        return pageRequestFieldName;
    }

    @Override
    public String getStartRequestFieldName() {
        return startRequestFieldName;
    }

    @Override
    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    private void initializeResultsPerPage(Class<?> requestType) {
        doForFirstAnnotatedFieldOn(requestType, Page.class, new AnnotatedFieldCallback<Page>() {
            @Override
            public void doFor(Field field, Page annotation) {
                resultsPerPage = annotation.resultsPerPage();
                pageRequestFieldName = field.getName();
            }
        });
    }

    private void initializeStartIndex(Class<?> requestType) {
        doForFirstAnnotatedFieldOn(requestType, StartIndex.class, new AnnotatedFieldCallback<StartIndex>() {
            @Override
            public void doFor(Field field, StartIndex annotation) {
                startRequestFieldName = field.getName();
            }
        });
    }


}
