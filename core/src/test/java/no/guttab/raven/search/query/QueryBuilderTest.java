package no.guttab.raven.search.query;

import no.guttab.raven.search.QueryBuilder;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueryBuilderTest {

    @Mock
    private QueryProcessor queryProcessor;


    @Test
    public void withAddedQueryProcessors_should_add_supplied_processors() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder().withAddedQueryProcessors(queryProcessor);

        List<QueryProcessor> actual = queryBuilder.getQueryProcessors();

        assertThat(actual, hasItem(queryProcessor));
    }

    @Test
    public void buildQuery_should_call_buildQuery_on_QueryProcessor() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder().withQueryProcessors(Arrays.asList(queryProcessor));
        Object queryInput = new Object();

        queryBuilder.buildQuery(queryInput);

        verify(queryProcessor).buildQuery(eq(queryInput), Matchers.<SolrQuery>any());
    }


}
