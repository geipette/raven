package no.guttab.raven.search.response.navigators.page;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PageNavigationTest {

    @Mock
    PageConfig pageConfig;

    @Mock
    QueryResponse queryResponse;

    PageNavigation pageNavigation;

    @Before
    public void setUp() throws Exception {
        pageNavigation = new PageNavigation(pageConfig, queryResponse);
    }

    @Test
    public void when_pageConfig_requestFieldName_is_not_null_then_requestHasPagination_should_be_true() throws Exception {
        when(pageConfig.getPageRequestFieldName()).thenReturn("page");

        assertThat(pageNavigation.requestHasPagination(), is(true));
    }

    @Test
    public void when_pageConfig_requestFieldName_is_null_then_requestHasPagination_should_be_false() throws Exception {
        assertThat(pageNavigation.requestHasPagination(), is(false));
    }

    @Test
    public void when_pageConfig_requestFieldName_has_value_then_pageRequestFieldName_should_be_that_value() throws Exception {
        when(pageConfig.getPageRequestFieldName()).thenReturn("page");
        assertThat(pageNavigation.getPageRequestFieldName(), is("page"));
    }

    @Test
    public void when_pageConfig_startRequestFieldName_has_value_then_startRequestFieldName_should_be_that_value() throws Exception {
        when(pageConfig.getStartRequestFieldName()).thenReturn("start");
        assertThat(pageNavigation.getStartRequestFieldName(), is("start"));
    }

    @Test
    public void when_pageConfig_startRequestFieldName_has_value_then_requestHasStartIndexFieldName_should_be_true() throws Exception {
        when(pageConfig.getStartRequestFieldName()).thenReturn("start");
        assertThat(pageNavigation.requestHasStartIndexFieldName(), is(true));
    }

    @Test
    public void when_pageConfig_startRequestFieldName_is_null_then_requestHasStartIndexFieldName_should_be_false() throws Exception {
        assertThat(pageNavigation.requestHasStartIndexFieldName(), is(false));
    }

    @Test
    public void when_start_is_zero_then_resultsStatsAtZero_should_be_true() throws Exception {
        SolrDocumentList results = resultsThatStartsAt(0L);
        when(queryResponse.getResults()).thenReturn(results);
        assertThat(pageNavigation.resultsStartsAtZero(), is(true));
    }

    @Test
    public void when_start_is_not_zero_then_resultsStatsAtZero_should_be_false() throws Exception {
        SolrDocumentList results = resultsThatStartsAt(5L);
        when(queryResponse.getResults()).thenReturn(results);
        assertThat(pageNavigation.resultsStartsAtZero(), is(false));
    }

    @Test
    public void when_number_of_results_is_less_or_equal_to_pageSize_then_number_of_pages_should_be_1() throws Exception {
        SolrDocumentList results = resultsWithNumFound(5L);
        when(queryResponse.getResults()).thenReturn(results);
        when(pageConfig.getResultsPerPage()).thenReturn(10);

        assertThat(pageNavigation.getNumberOfPages(), is(1L));
    }

    @Test
    public void when_number_of_results_is_larger_than_pageSize_then_number_of_pages_should_be_greater_than_1() throws Exception {
        SolrDocumentList results = resultsWithNumFound(11L);
        when(queryResponse.getResults()).thenReturn(results);
        when(pageConfig.getResultsPerPage()).thenReturn(10);

        assertThat(pageNavigation.getNumberOfPages(), is(greaterThan(1L)));
    }

    @Test
    public void when_number_of_results_is_zero_then_number_of_pages_should_be_zero() throws Exception {
        SolrDocumentList results = resultsWithNumFound(0L);
        when(queryResponse.getResults()).thenReturn(results);
        when(pageConfig.getResultsPerPage()).thenReturn(10);

        assertThat(pageNavigation.getNumberOfPages(), is(0L));
    }

    @Test
    public void when_number_of_results_is_double_of_pageSize_then_number_of_pages_should_be_two() throws Exception {
        SolrDocumentList results = resultsWithNumFound(20L);
        when(queryResponse.getResults()).thenReturn(results);
        when(pageConfig.getResultsPerPage()).thenReturn(10);

        assertThat(pageNavigation.getNumberOfPages(), is(2L));
    }

    @Test
    public void when_number_of_results_is_double_of_pageSize_plus_one_then_number_of_pages_should_be_three() throws Exception {
        SolrDocumentList results = resultsWithNumFound(21L);
        when(queryResponse.getResults()).thenReturn(results);
        when(pageConfig.getResultsPerPage()).thenReturn(10);

        assertThat(pageNavigation.getNumberOfPages(), is(3L));
    }

    @Test
    public void when_start_is_zero_then_currentPage_should_be_1() throws Exception {
        SolrDocumentList results = resultsThatStartsAt(0L);
        when(queryResponse.getResults()).thenReturn(results);

        assertThat(pageNavigation.getCurrentPage(), is(1L));
    }

    @Test
    public void when_start_is_within_the_first_page_then_currentPage_should_be_1() throws Exception {
        SolrDocumentList results = resultsThatStartsAt(5L);
        when(queryResponse.getResults()).thenReturn(results);
        when(pageConfig.getResultsPerPage()).thenReturn(10);

        assertThat(pageNavigation.getCurrentPage(), is(1L));
    }

    @Test
    public void when_start_is_at_the_start_of_the_second_page_then_currentPage_should_be_2() throws Exception {
        SolrDocumentList results = resultsThatStartsAt(10L);
        when(queryResponse.getResults()).thenReturn(results);
        when(pageConfig.getResultsPerPage()).thenReturn(10);

        assertThat(pageNavigation.getCurrentPage(), is(2L));
    }

    @Test
    public void when_start_is_within_the_second_page_then_currentPage_should_be_2() throws Exception {
        SolrDocumentList results = resultsThatStartsAt(15L);
        when(queryResponse.getResults()).thenReturn(results);
        when(pageConfig.getResultsPerPage()).thenReturn(10);

        assertThat(pageNavigation.getCurrentPage(), is(2L));
    }

    private SolrDocumentList resultsWithNumFound(long count) {
        SolrDocumentList results = Mockito.mock(SolrDocumentList.class);
        when(results.getNumFound()).thenReturn(count);
        return results;
    }

    private SolrDocumentList resultsThatStartsAt(long start) {
        SolrDocumentList results = Mockito.mock(SolrDocumentList.class);
        when(results.getStart()).thenReturn(start);
        when(results.getNumFound()).thenReturn(1000L);
        return results;
    }

}
