package no.guttab.raven.search.response.navigators.select;

import no.guttab.raven.search.response.NavigatorItems;
import no.guttab.raven.search.response.navigators.Navigation;
import org.apache.solr.client.solrj.response.FacetField;

class SelectNavigatorBuilder {

    private Navigation navigation;

    public SelectNavigatorBuilder(Navigation navigation) {
        this.navigation = navigation;
    }

    public SelectNavigator buildFor(FacetField facetField) {
        final NavigatorItems<SelectNavigatorItem> navigatorItems = SelectNavigatorItemsBuilder.build(navigation, facetField);
        return new SelectNavigator(facetField, navigatorItems);
    }


}
