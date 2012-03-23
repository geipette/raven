package no.guttab.raven.search.query;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.Page;
import org.apache.solr.client.solrj.SolrQuery;

import java.lang.reflect.Field;

import static no.guttab.raven.reflection.FieldUtils.getFieldValue;

class PageAnnotatedFieldCallback implements AnnotatedFieldCallback<Page> {
    private final SolrQuery solrQuery;
    private final Object queryInput;

    public PageAnnotatedFieldCallback(SolrQuery solrQuery, Object queryInput) {
        this.solrQuery = solrQuery;
        this.queryInput = queryInput;
    }

    @Override
    public void doFor(Field field, Page annotation) {
        Integer rows = applyResultsPerPage(annotation);
        applyStartIndex(field, rows);
    }

    private Integer applyResultsPerPage(Page annotation) {
        Integer rows = annotation.resultsPerPage();
        solrQuery.setRows(rows);
        return rows;
    }

    private void applyStartIndex(Field field, Integer rows) {
        Integer start = calculateStartIndexForPageField(field, rows);
        if (start != null && start > 0) {
            solrQuery.setStart(start);
        }
    }

    private Integer calculateStartIndexForPageField(Field field, int rows) {
        final Integer currentPage = findCurrentPage(field);
        return currentPage == null || currentPage <= 1 ? null : rows * (currentPage - 1);
    }

    private Integer findCurrentPage(Field field) {
        return (Integer) getFieldValue(field, queryInput);
    }
}
