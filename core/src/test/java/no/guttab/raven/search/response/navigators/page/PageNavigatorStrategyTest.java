package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.Navigators;
import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PageNavigatorStrategyTest {
    @Mock
    private NavigatorUrls navigatorUrls;
    @Mock
    private Navigators navigators;
    @Mock
    private PageNavigation pageNavigation;

    @Test
    public void addNavigator_should_not_add_navigator_when_page_annotation_not_defined() throws Exception {
        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageNavigation);
        pageNavigatorStrategy.addNavigators(navigatorUrls, navigators);

        verifyNoMoreInteractions(navigators);
    }

}
