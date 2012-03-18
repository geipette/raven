package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PageNavigatorUrlProcessorTest {

    @Mock
    private NavigatorUrls navigatorUrls;

    @Mock
    private PageNavigation pageNavigation;

    @Test
    public void addUrlFragments_should_do_nothing_when_response_starts_on_zero_index() throws Exception {
        when(pageNavigation.getCurrentResultStartIndex()).thenReturn(0L);

        PageNavigatorUrlProcessor pageNavigatorUrlProcessor = new PageNavigatorUrlProcessor(pageNavigation);
        pageNavigatorUrlProcessor.process(navigatorUrls);

        verifyNoMoreInteractions(navigatorUrls);
    }

    @Test
    public void addUrlFragments_should_add_page_url_fragment_when_currentPage_is_greater_that_zero() throws Exception {
        when(pageNavigation.resultsStartsAtZero()).thenReturn(false);
        when(pageNavigation.requestHasPagination()).thenReturn(true);
        when(pageNavigation.getCurrentPage()).thenReturn(1L);
        when(pageNavigation.getPageRequestFieldName()).thenReturn("page");

        PageNavigatorUrlProcessor pageNavigatorUrlProcessor = new PageNavigatorUrlProcessor(pageNavigation);
        pageNavigatorUrlProcessor.process(navigatorUrls);

        verify(navigatorUrls).addVolatileUrlFragment("page", "1");
    }

    @Test
    public void addUrlFragments_should_add_start_url_fragment_when_response_starts_in_index_greater_than_zero_and_startRequestFieldName_is_not_null() throws Exception {
        when(pageNavigation.resultsStartsAtZero()).thenReturn(false);
        when(pageNavigation.requestHasStartIndexFieldName()).thenReturn(true);
        when(pageNavigation.getStartRequestFieldName()).thenReturn("start");
        when(pageNavigation.getCurrentResultStartIndex()).thenReturn(1L);

        PageNavigatorUrlProcessor pageNavigatorUrlProcessor = new PageNavigatorUrlProcessor(pageNavigation);
        pageNavigatorUrlProcessor.process(navigatorUrls);

        verify(navigatorUrls).addVolatileUrlFragment("start", "1");
    }

    @Test
    public void addUrlFragments_should_add_page_url_fragment_when_response_is_on_second_page_and_request_hasPagination() throws Exception {
        when(pageNavigation.resultsStartsAtZero()).thenReturn(false);
        when(pageNavigation.requestHasPagination()).thenReturn(true);
        when(pageNavigation.getPageRequestFieldName()).thenReturn("page");
        when(pageNavigation.getCurrentPage()).thenReturn(2L);

        PageNavigatorUrlProcessor pageNavigatorUrlProcessor = new PageNavigatorUrlProcessor(pageNavigation);
        pageNavigatorUrlProcessor.process(navigatorUrls);

        verify(navigatorUrls).addVolatileUrlFragment("page", "2");
    }

}
