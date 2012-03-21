package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PageNavigatorBuilderTest {
    @Mock
    private NavigatorUrls navigatorUrls;
    @Mock
    private PageNavigation pageNavigation;

    PageNavigatorBuilder pageNavigatorBuilder;

    @Before
    public void setUp() throws Exception {
        pageNavigatorBuilder = new PageNavigatorBuilder(pageNavigation);
        when(pageNavigation.getPageRequestFieldName()).thenReturn("page");
    }

    @Test
    public void build_should_not_return_nullValue() throws Exception {
        when(pageNavigation.requestHasPagination()).thenReturn(true);

        PageNavigator actual = pageNavigatorBuilder.build(navigatorUrls);

        assertThat(actual, is(not(nullValue())));
    }


    @Test
    public void build_should_add_correct_number_of_items() throws Exception {
        setupFivePageNavigation();

        PageNavigator actual = pageNavigatorBuilder.build(navigatorUrls);

        assertThat(actual.getItems().size(), is(5));
    }

    @Test
    public void when_navigator_has_items_then_they_should_have_names_corresponding_to_their_page_number() throws Exception {
        setupFivePageNavigation();

        PageNavigator actual = pageNavigatorBuilder.build(navigatorUrls);
        assertThatItemsHasNamesCorrespondingToTheirPageNumber(actual);
    }

    @Test
    public void when_currentPage_is_not_set_then_the_navigator_for_page_one_should_be_selected() throws Exception {
        setupFivePageNavigation();

        PageNavigator actual = pageNavigatorBuilder.build(navigatorUrls);

        PageNavigatorItem selectedItem = actual.getFirstSelectedItem();
        assertThat(selectedItem, is(not(nullValue())));
        assertThat(selectedItem.getName(), is(equalTo("1")));
    }

    @Test
    public void when_currentPage_is_greaterThan_one_then_the_navigator_corresponding_to_currentPage_should_be_selected() throws Exception {
        setupFivePageNavigation();
        when(pageNavigation.getCurrentPage()).thenReturn(2L);

        PageNavigator actual = pageNavigatorBuilder.build(navigatorUrls);

        PageNavigatorItem selectedItem = actual.getFirstSelectedItem();
        assertThat(selectedItem, is(not(nullValue())));
        assertThat(selectedItem.getName(), is(equalTo("2")));
    }

    @Test
    public void pageNavigator_should_have_displayName_equal_to_the_pageNavigation_pageRequestFieldName() throws Exception {
        when(pageNavigation.getPageRequestFieldName()).thenReturn("aRequestFieldName");

        PageNavigator actual = pageNavigatorBuilder.build(navigatorUrls);

        assertThat(actual.getDisplayName(), is(equalTo("aRequestFieldName")));
    }

    @Test
    public void navigatorItems_should_use_NavigatorUrls_to_build_urls() throws Exception {
        setupFivePageNavigation();
        when(navigatorUrls.buildUrlFor("page", "1")).thenReturn("?testUrl");

        PageNavigator actual = pageNavigatorBuilder.build(navigatorUrls);

        assertThat(actual.getItems().get(0).getUrl(), is(equalTo("?testUrl")));
        verify(navigatorUrls).buildUrlFor("page", "1");
        verify(navigatorUrls).buildUrlFor("page", "2");
        verify(navigatorUrls).buildUrlFor("page", "3");
        verify(navigatorUrls).buildUrlFor("page", "4");
        verify(navigatorUrls).buildUrlFor("page", "5");
    }

    @Test
    public void navigatorItems_should_use_NavigatorUrls_to_build_deselectUrls() throws Exception {
        setupFivePageNavigation();
        when(navigatorUrls.resetUrlFor("page")).thenReturn("?testResetUrl");

        PageNavigator actual = pageNavigatorBuilder.build(navigatorUrls);

        assertThat(actual.getItems().get(0).getDeselectUrl(), is(equalTo("?testResetUrl")));

        verify(navigatorUrls, atLeastOnce()).resetUrlFor("page");
    }


    private void setupFivePageNavigation() {
        when(pageNavigation.requestHasPagination()).thenReturn(true);
        when(pageNavigation.getNumberOfPages()).thenReturn(5L);
    }


    private void assertThatItemsHasNamesCorrespondingToTheirPageNumber(PageNavigator navigator) {
        List<PageNavigatorItem> items = navigator.getItems();
        for (int i = 0; i < items.size(); i++) {
            PageNavigatorItem pageNavigatorItem = items.get(i);
            assertThat(pageNavigatorItem.getName(), is(equalTo(String.valueOf(i + 1))));
        }
    }
}
