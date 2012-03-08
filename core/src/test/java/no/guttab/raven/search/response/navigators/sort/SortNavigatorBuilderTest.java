package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.annotations.SortOrder;
import no.guttab.raven.annotations.SortOrders;
import no.guttab.raven.annotations.SortTarget;
import no.guttab.raven.annotations.SortVariant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static no.guttab.raven.annotations.SortDirection.ASCENDING;
import static no.guttab.raven.annotations.SortDirection.DESCENDING;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.number.OrderingComparisons.greaterThan;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SortNavigatorBuilderTest {
    @Mock
    private SortNavigation navigation;

    @Test
    public void when_a_field_is_annotated_with_SortTarget_build_should_create_sort_navigator_item() throws Exception {
        class TestSearchResponse {
            @SortTarget
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator actual = sortNavigatorBuilder.build();

        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void returned_SortNavigator_should_have_SortNavigatorItem_for_annotated_field() throws Exception {
        class TestSearchResponse {
            @SortTarget
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        SortNavigatorItem actualItem = getFirstItem(sortNavigator);

        assertThat(actualItem.getName(), is("price"));
    }

    @Test
    public void returned_SortNavigatorItem_should_be_selected_when_navigation_sortValue_equals_items_sortCriteria() throws Exception {
        class TestSearchResponse {
            @SortTarget
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");
        when(navigation.getSortValue()).thenReturn("price asc");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();

        List<SortNavigatorItem> selectedItems = sortNavigator.getSelectedItems();
        assertThat("navigator should have more than zero selected items", selectedItems.size(), is(greaterThan(0)));
        SortNavigatorItem actualItem = selectedItems.get(0);
        assertThat(actualItem.getName(), is("price"));
    }

    @Test
    public void returned_SortNavigatorItem_for_SortTarget_should_have_deselectUrl_when_item_is_selected() throws Exception {
        class TestSearchResponse {
            @SortTarget
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");
        when(navigation.getSortValue()).thenReturn("price asc");
        when(navigation.resetUrlFor("sort", "price asc")).thenReturn("?");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();

        List<SortNavigatorItem> selectedItems = sortNavigator.getSelectedItems();
        assertThat("navigator should have more than zero selected items", selectedItems.size(), is(greaterThan(0)));
        SortNavigatorItem actualItem = selectedItems.get(0);
        assertThat(actualItem.getDeselectUrl(), is("?"));
    }

    @Test
    public void returned_SortNavigatorItem_should_have_a_correct_sortCriteria() throws Exception {
        class TestSearchResponse {
            @SortTarget
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        SortNavigatorItem actualItem = getFirstItem(sortNavigator);

        assertThat(actualItem.getSortCriteria(), is("price asc"));
    }


    @Test
    public void returned_SortNavigator_should_have_a_SortNavigatorItem_for_each_variant() throws Exception {
        class TestSearchResponse {
            @SortTarget(variants = {
                    @SortVariant(ASCENDING),
                    @SortVariant(DESCENDING)
            })
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        List<SortNavigatorItem> actual = sortNavigator.getItems();

        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getName(), is("price"));
        assertThat(actual.get(1).getName(), is("-price"));
    }

    @Test
    public void multiple_returned_SortNavigatorItems_should_have_correct_sortCriteria_for_each_variant() throws Exception {
        class TestSearchResponse {
            @SortTarget(variants = {
                    @SortVariant(ASCENDING),
                    @SortVariant(DESCENDING)
            })
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        List<SortNavigatorItem> actual = sortNavigator.getItems();

        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getSortCriteria(), is("price asc"));
        assertThat(actual.get(1).getSortCriteria(), is("price desc"));
    }


    @Test
    public void sortNavigatorItem_for_annotated_field_should_use_displayName_from_annotation() throws Exception {
        class TestSearchResponse {
            @SortTarget(displayName = "Pris")
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        SortNavigatorItem actualItem = getFirstItem(sortNavigator);

        assertThat(actualItem.getName(), is("Pris"));
    }

    @Test
    public void sortNavigatorItem_url_should_use_key_corresponding_to_SearchRequest_Sort_annotation() throws Exception {
        class TestSearchResponse {
            @SortTarget(displayName = "Pris")
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");
        when(navigation.urlFor("sort", "price")).thenReturn("?sort=price");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        SortNavigatorItem actualItem = getFirstItem(sortNavigator);

        assertThat(actualItem.getUrl(), is("?sort=price"));
    }

    @Test
    public void when_field_has_SortTarget_displayName_and_a_single_SortVariant_returned_SortNavigator_should_have_SortTargets_displayName() throws Exception {
        class TestSearchResponse {
            @SortTarget(displayName = "Pris synkende", variants = @SortVariant(DESCENDING))
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        SortNavigatorItem actualItem = getFirstItem(sortNavigator);

        assertThat(actualItem.getName(), is("Pris synkende"));
    }

    @Test
    public void when_field_has_SortTarget_displayName_and_multiple_SortVariants_SortNavigator_should_have_the_SortTargets_displayName_with_variant_prefix() throws Exception {
        class TestSearchResponse {
            @SortTarget(displayName = "Pris", variants = {@SortVariant(ASCENDING), @SortVariant(DESCENDING)})
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();

        List<SortNavigatorItem> actual = sortNavigator.getItems();

        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getName(), is("Pris"));
        assertThat(actual.get(1).getName(), is("-Pris"));
    }

    @Test
    public void when_SortVariant_displayName_set_SortNavigator_should_contain_items_with_the_supplied_names() throws Exception {
        class TestSearchResponse {
            @SortTarget(
                    variants = {
                            @SortVariant(displayName = "Pris lav/høy", value = ASCENDING),
                            @SortVariant(displayName = "Pris høy/lav", value = DESCENDING)
                    })
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();

        List<SortNavigatorItem> actual = sortNavigator.getItems();

        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getName(), is("Pris lav/høy"));
        assertThat(actual.get(1).getName(), is("Pris høy/lav"));
    }

    @Test
    public void sortNavigatorItem_url_should_use_name_corresponding_to_SearchRequest_Sort_annotation() throws Exception {
        class TestSearchResponse {
            @SortTarget(displayName = "Pris", variants = @SortVariant(name = "1"))
            String price;
        }
        when(navigation.getSortFieldName()).thenReturn("sort");
        when(navigation.urlFor("sort", "1")).thenReturn("?sort=1");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        SortNavigatorItem actualItem = getFirstItem(sortNavigator);

        assertThat(actualItem.getUrl(), is("?sort=1"));
    }


    @Test
    public void when_SortOrder_is_present_a_corresponding_SortNavigator_should_be_added() throws Exception {
        @SortOrders(
                @SortOrder(value = "Pris økende", sortCriteria = "price asc, score desc")
        )
        class TestSearchResponse {
        }
        when(navigation.getSortFieldName()).thenReturn("sort");
        when(navigation.urlFor("sort", "price asc, score desc")).thenReturn("?sort=price asc, score desc");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        SortNavigatorItem actualItem = getFirstItem(sortNavigator);

        assertThat(actualItem.getSortCriteria(), is("price asc, score desc"));
        assertThat(actualItem.getName(), is("Pris økende"));
        assertThat(actualItem.getUrl(), is("?sort=price asc, score desc"));
    }

    @Test
    public void returned_SortNavigatorItem_for_SortOrder_should_have_deselectUrl_when_item_is_selected() throws Exception {
        @SortOrders(
                @SortOrder(value = "Pris økende", sortCriteria = "price asc")
        )
        class TestSearchResponse {
        }
        when(navigation.getSortFieldName()).thenReturn("sort");
        when(navigation.getSortValue()).thenReturn("price asc");
        when(navigation.resetUrlFor("sort", "price asc")).thenReturn("?");

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();

        List<SortNavigatorItem> selectedItems = sortNavigator.getSelectedItems();
        assertThat("navigator should have more than zero selected items", selectedItems.size(), is(greaterThan(0)));
        SortNavigatorItem actualItem = selectedItems.get(0);
        assertThat(actualItem.getDeselectUrl(), is("?"));
    }


    @Test
    public void when_multiple_SortOrders_is_present_a_corresponding_SortNavigator_should_be_added_for_each_SortOrder() throws Exception {
        @SortOrders({
                @SortOrder(value = "Pris økende", sortCriteria = "price asc, score desc"),
                @SortOrder(value = "Pris minkende", sortCriteria = "price desc, score desc")
        })
        class TestSearchResponse {
        }

        SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
        SortNavigator sortNavigator = sortNavigatorBuilder.build();
        List<SortNavigatorItem> actual = sortNavigator.getItems();

        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getName(), is("Pris økende"));
        assertThat(actual.get(0).getSortCriteria(), is("price asc, score desc"));
        assertThat(actual.get(1).getName(), is("Pris minkende"));
        assertThat(actual.get(1).getSortCriteria(), is("price desc, score desc"));
    }


    @Test(expected = SortNavigatorBuilder.NoSortFieldException.class)
    public void build_should_throw_exception_if_SortTarget_defined_but_no_sortField_exists_in_request() throws Exception {
        class TestSearchResponse {
            @SortTarget(displayName = "Pris")
            String price;
        }

        new SortNavigatorBuilder(TestSearchResponse.class, navigation);
    }

    private SortNavigatorItem getFirstItem(SortNavigator sortNavigator) {
        List<SortNavigatorItem> navigatorItems = sortNavigator.getItems();
        assertThat("navigator has zero items", navigatorItems.isEmpty(), is(false));
        return navigatorItems.get(0);
    }


}
