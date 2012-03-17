package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PageNavigatorStrategyTest {
    @Mock
    private QueryResponse queryResponse;
    @Mock
    private PageConfig pageConfig;
    @Mock
    private NavigatorUrls navigatorUrls;
    @Mock
    private Navigators navigators;
    @Mock
    private PageNavigation pageNavigation;


    @Test
    public void addUrlFragments_should_do_nothing_when_response_starts_on_zero_index() throws Exception {
        when(pageNavigation.getCurrentResultStartIndex()).thenReturn(0L);

        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageNavigation);
        pageNavigatorStrategy.addUrlFragments(navigatorUrls);

        verifyNoMoreInteractions(navigatorUrls);
    }

    @Test
    public void addUrlFragments_should_add_page_url_fragment_when_currentPage_is_greater_that_zero() throws Exception {
        when(pageNavigation.resultsStartsAtZero()).thenReturn(false);
        when(pageNavigation.requestHasPagination()).thenReturn(true);
        when(pageNavigation.getCurrentPage()).thenReturn(1L);
        when(pageNavigation.getPageRequestFieldName()).thenReturn("page");

        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageNavigation);
        pageNavigatorStrategy.addUrlFragments(navigatorUrls);

        verify(navigatorUrls).addVolatileUrlFragment("page", "1");
    }

    @Test
    public void addUrlFragments_should_add_start_url_fragment_when_response_starts_in_index_greater_than_zero_and_startRequestFieldName_is_not_null() throws Exception {
        when(pageNavigation.resultsStartsAtZero()).thenReturn(false);
        when(pageNavigation.requestHasStartIndexFieldName()).thenReturn(true);
        when(pageNavigation.getStartRequestFieldName()).thenReturn("start");
        when(pageNavigation.getCurrentResultStartIndex()).thenReturn(1L);

        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageNavigation);
        pageNavigatorStrategy.addUrlFragments(navigatorUrls);

        verify(navigatorUrls).addVolatileUrlFragment("start", "1");
    }

    @Test
    public void addUrlFragments_should_add_page_url_fragment_when_response_is_on_second_page_and_request_hasPagination() throws Exception {
        when(pageNavigation.resultsStartsAtZero()).thenReturn(false);
        when(pageNavigation.requestHasPagination()).thenReturn(true);
        when(pageNavigation.getPageRequestFieldName()).thenReturn("page");
        when(pageNavigation.getCurrentPage()).thenReturn(2L);

        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageNavigation);
        pageNavigatorStrategy.addUrlFragments(navigatorUrls);

        verify(navigatorUrls).addVolatileUrlFragment("page", "2");
    }

    @Test
    public void addNavigators_should_add_navigator_when_request_has_pagination() throws Exception {
        when(pageNavigation.requestHasPagination()).thenReturn(true);

        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageNavigation);
        pageNavigatorStrategy.addNavigators(navigatorUrls, navigators);

        verify(navigators).addNavigator(any(PageNavigator.class));
    }

    @Test
    public void addNavigator_should_not_add_navigator_when_page_annotation_not_defined() throws Exception {
        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageNavigation);
        pageNavigatorStrategy.addNavigators(navigatorUrls, navigators);

        verifyNoMoreInteractions(navigators);
    }

    @Test
    public void addNavigators_should_add_navigator_with_correct_number_of_items() throws Exception {
        when(pageNavigation.getPageRequestFieldName()).thenReturn("page");
        when(pageNavigation.requestHasPagination()).thenReturn(true);
        when(pageNavigation.getNumberOfPages()).thenReturn(5L);

        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageNavigation);
        pageNavigatorStrategy.addNavigators(navigatorUrls, navigators);

        PageNavigator actual = captureAddedNavigator();

        assertThat(actual.getItems().size(), is(5));
    }

    private PageNavigator captureAddedNavigator() {
        ArgumentCaptor<PageNavigator> pageNavigator = ArgumentCaptor.forClass(PageNavigator.class);
        verify(navigators).addNavigator(pageNavigator.capture());
        return pageNavigator.getValue();
    }

}
