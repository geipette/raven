package no.guttab.raven.search.query;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.Sort;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;

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
            solrQuery.set(CommonParams.SORT, value);
        }
    }

}
