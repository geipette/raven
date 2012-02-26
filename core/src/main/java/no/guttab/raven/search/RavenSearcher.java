package no.guttab.raven.search;

import no.guttab.raven.search.response.SearchResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

public class RavenSearcher<T> {
    private SearchServer searchServer;

    public RavenSearcher(SearchServer searchServer) {
        this.searchServer = searchServer;
    }

    public SearchResponse<T> search(Object searchRequest, Class<T> responseDocumentType) {
        DefaultSearchContext<T> searchContext = new DefaultSearchContext<T>(searchRequest, responseDocumentType);
        SolrQuery solrQuery = searchContext.queryBuilder().buildQuery(searchRequest);
        QueryResponse queryResponse = searchServer.search(solrQuery);
        return searchContext.responseBuilder().buildResponse(queryResponse);
    }

}
