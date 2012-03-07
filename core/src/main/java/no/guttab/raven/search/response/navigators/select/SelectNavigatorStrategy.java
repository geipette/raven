package no.guttab.raven.search.response.navigators.select;

import no.guttab.raven.search.filter.FilterQueries;
import no.guttab.raven.search.response.navigators.Navigation;
import no.guttab.raven.search.response.navigators.NavigatorStrategy;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import no.guttab.raven.search.response.navigators.Navigators;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;

public class SelectNavigatorStrategy implements NavigatorStrategy {

    private FilterQueries filterQueries;
    private List<FacetField> facetFields;

    public SelectNavigatorStrategy(FilterQueries filterQueries, List<FacetField> facetFields) {
        this.filterQueries = filterQueries;
        this.facetFields = facetFields;
    }

    @Override
    public void addUrlFragments(NavigatorUrls navigatorUrls) {
        new SelectNavigatorUrlsProcessor(filterQueries, facetFields).process(navigatorUrls);
    }

    @Override
    public void addNavigators(NavigatorUrls navigatorUrls, Navigators navigators) {
        final Navigation navigation = new Navigation(navigatorUrls, filterQueries);
        final SelectNavigatorBuilder builder = new SelectNavigatorBuilder(navigation);
        for (FacetField facetField : facetFields) {
            navigators.addNavigator(builder.buildFor(facetField));
        }
    }
}
