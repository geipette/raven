package no.guttab.raven.search.response.navigators.select;

import no.guttab.raven.search.filter.FilterQueries;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

class SelectNavigatorUrlsProcessor {
    private List<FacetField> facetFields;
    private FilterQueries filterQueries;

    public SelectNavigatorUrlsProcessor(FilterQueries filterQueries, List<FacetField> facetFields) {
        this.filterQueries = filterQueries;
        this.facetFields = facetFields;
    }

    public void process(NavigatorUrls navigatorUrls) {
        for (final FacetField facetField : facetFields) {
            addUrlFragmentsFor(facetField, navigatorUrls);
        }
    }

    private void addUrlFragmentsFor(FacetField facetField, NavigatorUrls navigatorUrls) {
        final Set<String> fqCriterias = filterQueries.findFqCriteriasFor(facetField);
        if (!isEmpty(fqCriterias)) {
            for (String fqCriteria : fqCriterias) {
                navigatorUrls.addUrlFragment(facetField.getName(), fqCriteria);
            }
        }
    }

}
