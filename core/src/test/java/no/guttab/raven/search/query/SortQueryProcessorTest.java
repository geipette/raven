package no.guttab.raven.search.query;

import no.guttab.raven.annotations.Sort;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SortQueryProcessorTest {

    @Mock
    private SolrQuery solrQuery;

    @Test
    public void when_sort_annotation_defined_and_it_contains_a_value_sort_should_be_set_on_query() throws Exception {
        class TestQuery {
            @Sort
            String sortField = "popularity asc";
        }

        SortQueryProcessor queryProcessor = new SortQueryProcessor();
        TestQuery input = new TestQuery();
        queryProcessor.buildQuery(input, solrQuery);

        Mockito.verify(solrQuery).set(CommonParams.SORT, "popularity asc");
    }

    @Test
    public void when_sort_annotation_defined_and_it_is_empty_no_sort_field_should_be_set() throws Exception {
        class TestQuery {
            @Sort
            String sortField;
        }

        SortQueryProcessor queryProcessor = new SortQueryProcessor();
        TestQuery input = new TestQuery();
        queryProcessor.buildQuery(input, solrQuery);

        Mockito.verifyNoMoreInteractions(solrQuery);
    }

}
