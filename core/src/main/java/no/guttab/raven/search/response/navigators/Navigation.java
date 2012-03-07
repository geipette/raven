package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.filter.FilterQueries;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.Set;

public class Navigation {

    private NavigatorUrls navigatorUrls;
    private FilterQueries filterQueries;

    public Navigation(NavigatorUrls navigatorUrls, FilterQueries filterQueries) {
        this.navigatorUrls = navigatorUrls;
        this.filterQueries = filterQueries;
    }

    public Set<String> fqsFor(FacetField facetField) {
        return filterQueries.findFqsFor(facetField);
    }

    public String urlFor(String indexFieldName, String fqCriteria) {
        return navigatorUrls.buildUrlFor(indexFieldName, fqCriteria);
    }

    public String resetUrlFor(String indexFieldName, String fqCriteria) {
        return navigatorUrls.resetUrlFor(indexFieldName, fqCriteria);
    }

}
