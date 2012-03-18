package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
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
    private NavigatorUrls navigatorUrls;
    @Mock
    private Navigators navigators;
    @Mock
    private PageNavigation pageNavigation;

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
