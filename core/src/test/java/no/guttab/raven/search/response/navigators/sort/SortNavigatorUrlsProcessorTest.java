package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SortNavigatorUrlsProcessorTest {

    @Mock
    private NavigatorUrls navigatorUrls;


    @Test
    public void when_sortValue_ends_with_asc__the_generated_url_fragment_should_end_with_asc() {
        SortNavigatorUrlsProcessor sortNavigatorUrlsProcessor =
                new SortNavigatorUrlsProcessor("sort", "popularity asc");
        sortNavigatorUrlsProcessor.process(navigatorUrls);

        verify(navigatorUrls).addVolatileUrlFragment("sort", "popularity asc");
    }

    @Test
    public void when_sortValue_ends_with_desc__the_generated_url_fragment_should_end_with_desc() {
        SortNavigatorUrlsProcessor sortNavigatorUrlsProcessor =
                new SortNavigatorUrlsProcessor("sortField", "popularity desc");
        sortNavigatorUrlsProcessor.process(navigatorUrls);

        verify(navigatorUrls).addVolatileUrlFragment("sortField", "popularity desc");
    }

    @Test
    public void when_sortValue_is_empty_no_url_fragment_should_be_added() {
        SortNavigatorUrlsProcessor sortNavigatorUrlsProcessor = new SortNavigatorUrlsProcessor("sort", null);
        sortNavigatorUrlsProcessor.process(navigatorUrls);

        verifyNoMoreInteractions(navigatorUrls);
    }

    @Test
    public void when_sortField_is_empty_no_url_fragment_should_be_added() {
        SortNavigatorUrlsProcessor sortNavigatorUrlsProcessor = new SortNavigatorUrlsProcessor(null, "popularity asc");
        sortNavigatorUrlsProcessor.process(navigatorUrls);

        verifyNoMoreInteractions(navigatorUrls);
    }

}
