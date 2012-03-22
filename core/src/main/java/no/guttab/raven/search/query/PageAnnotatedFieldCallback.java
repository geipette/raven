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
        int rows = annotation.resultsPerPage();
        int start = calculateStartIndexForPageField(field, rows);
        solrQuery.setStart(start);
        solrQuery.setRows(rows);
    }

    private int calculateStartIndexForPageField(Field field, int rows) {
        return rows * (Integer) getFieldValue(field, queryInput);
    }
}
