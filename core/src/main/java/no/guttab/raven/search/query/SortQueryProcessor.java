package no.guttab.raven.search.query;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.AnnotationUtils;
import no.guttab.raven.annotations.Sort;
import org.apache.solr.client.solrj.SolrQuery;

import java.lang.reflect.Field;

import static no.guttab.raven.reflection.FieldUtils.getFieldValue;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class SortQueryProcessor implements QueryProcessor {


    @Override
    public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
        AnnotationUtils.doForFirstAnnotatedFieldOn(queryInput, Sort.class, new AnnotatedFieldCallback<Sort>() {
            @Override
            public void doFor(Field field, Sort annotation) {
                String value = (String) getFieldValue(field, queryInput);
                if (!isEmpty(value)) {
                    solrQuery.setSortField(value, SolrQuery.ORDER.asc);
                }
            }
        });
    }
}
