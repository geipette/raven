package no.guttab.raven.search.response.navigators.select;

import no.guttab.raven.search.response.Navigator;
import no.guttab.raven.search.response.NavigatorItems;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public class SelectNavigator implements Navigator<SelectNavigatorItem> {
    private final FacetField facetField;
    private final NavigatorItems<SelectNavigatorItem> selectNavigatorItems;

    public SelectNavigator(FacetField facetField, NavigatorItems<SelectNavigatorItem> selectNavigatorItems) {
        this.facetField = facetField;
        this.selectNavigatorItems = selectNavigatorItems;
    }

    @Override
    public String getDisplayName() {
        return facetField.getName();
    }

    @Override
    public boolean isSelected() {
        return !isEmpty(selectNavigatorItems.getSelectedItems());
    }

    @Override
    public SelectNavigatorItem getFirstSelectedItem() {
        return selectNavigatorItems.getSelectedItems().isEmpty() ? null : selectNavigatorItems.getSelectedItems().get(0);
    }

    @Override
    public List<SelectNavigatorItem> getItems() {
        return selectNavigatorItems.getItems();
    }

    @Override
    public List<SelectNavigatorItem> getSelectedItems() {
        return selectNavigatorItems.getSelectedItems();
    }

}
