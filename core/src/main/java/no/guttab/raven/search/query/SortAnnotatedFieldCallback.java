package no.guttab.raven.search.query;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.Sort;
import org.apache.solr.client.solrj.SolrQuery;

import java.lang.reflect.Field;

import static no.guttab.raven.reflection.FieldUtils.getFieldValue;
import static org.apache.commons.lang3.StringUtils.isEmpty;

class SortAnnotatedFieldCallback implements AnnotatedFieldCallback<Sort> {
    private final Object queryInput;
    private final SolrQuery solrQuery;

    public SortAnnotatedFieldCallback(Object queryInput, SolrQuery solrQuery) {
        this.queryInput = queryInput;
        this.solrQuery = solrQuery;
    }

    @Override
    public void doFor(Field field, Sort annotation) {
        String value = (String) getFieldValue(field, queryInput);
        if (!isEmpty(value)) {
            solrQuery.setSortField(solrIndexFieldFor(value), sortDirectionFor(value));
        }
    }

    private String solrIndexFieldFor(String value) {
        return prefixedByMinus(value) ? stripPrefix(value) : value;
    }

    private String stripPrefix(String value) {
        return value.substring(1);
    }

    private boolean prefixedByMinus(String value) {
        return value.charAt(0) == '-';
    }

    private SolrQuery.ORDER sortDirectionFor(String value) {
        return prefixedByMinus(value) ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc;
    }

}
