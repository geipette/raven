package no.guttab.raven.search.query;

import no.guttab.raven.annotations.AnnotationUtils;
import no.guttab.raven.annotations.Sort;
import org.apache.solr.client.solrj.SolrQuery;

public class SortQueryProcessor implements QueryProcessor {


    @Override
    public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
        AnnotationUtils.doForFirstAnnotatedFieldOn(
                queryInput, Sort.class, new SortAnnotatedFieldCallback(queryInput, solrQuery));
    }

}
