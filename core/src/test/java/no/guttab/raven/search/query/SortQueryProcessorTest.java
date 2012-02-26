package no.guttab.raven.search.query;

import no.guttab.raven.annotations.Sort;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class SortQueryProcessorTest {

    @Mock
    private SolrQuery solrQuery;

    @Test
    public void when_sort_annotation_defined_and_it_contains_a_value_sort_should_be_set_on_query() throws Exception {
        class TestQuery {
            @Sort
            String sortField = "popularity";
        }

        SortQueryProcessor queryProcessor = new SortQueryProcessor();
        TestQuery input = new TestQuery();
        queryProcessor.buildQuery(input, solrQuery);

        Mockito.verify(solrQuery).setSortField("popularity", SolrQuery.ORDER.asc);
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


    @Test
    @Ignore
    public void fail_this_test() throws Exception {
        // TODO: Implement @SortOrder, @SortTarget and @SortVariant
        fail("Not implemented");
    }


}
