package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.search.response.navigators.NavigatorUrls;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    private SolrDocumentList solrDocumentList;

    @Before
    public void setUp() throws Exception {
        when(queryResponse.getResults()).thenReturn(solrDocumentList);
    }

    @Test
    public void addUrlFragments_should_do_nothing_when_response_is_on_first_page() throws Exception {
        when(solrDocumentList.getStart()).thenReturn(0L);

        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageConfig, queryResponse);
        pageNavigatorStrategy.addUrlFragments(navigatorUrls);

        verifyNoMoreInteractions(navigatorUrls);
    }

    @Test
    public void addUrlFragments_should_add_page_url_fragment_when_response_is_on_second_page_and_page_annotation_is_defined() throws Exception {
        when(solrDocumentList.getStart()).thenReturn(1L);
        when(pageConfig.getResultsPerPage()).thenReturn(1);
        when(pageConfig.getPageRequestFieldName()).thenReturn("page");

        PageNavigatorStrategy pageNavigatorStrategy = new PageNavigatorStrategy(pageConfig, queryResponse);
        pageNavigatorStrategy.addUrlFragments(navigatorUrls);

        verify(navigatorUrls).addUrlFragment("page", "1");
    }

}
