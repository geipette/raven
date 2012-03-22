package no.guttab.raven.search.query;

import no.guttab.raven.annotations.AnnotationUtils;
import no.guttab.raven.annotations.Page;
import org.apache.solr.client.solrj.SolrQuery;

public class PageQueryProcessor implements QueryProcessor {
    @Override
    public void buildQuery(final Object queryInput, final SolrQuery solrQuery) {
        AnnotationUtils.doForFirstAnnotatedFieldOn(
                queryInput, Page.class, new PageAnnotatedFieldCallback(solrQuery, queryInput));

    }

}
