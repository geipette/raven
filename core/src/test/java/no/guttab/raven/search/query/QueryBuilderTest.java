package no.guttab.raven.search.query;

import java.util.Arrays;
import java.util.List;

import no.guttab.raven.search.QueryBuilder;
import no.guttab.raven.search.Search;
import org.apache.solr.client.solrj.SolrQuery;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryBuilderTest {

   @Mock
   private QueryProcessor queryProcessor;


   @Test
   public void default_constructor_should_initialize_default_queryProcessors() throws Exception {
      QueryBuilder queryBuilder = new QueryBuilder();

      List<QueryProcessor> actual = queryBuilder.getQueryProcessors();

      assertThatQueryBuilderHasDefaultQueryProcessors(actual);
   }

   @Test
   public void withAddedQueryProcessors_should_keep_existing_processors_and_add_supplied_processors() throws Exception {
      QueryBuilder queryBuilder = new QueryBuilder().withAddedQueryProcessors(queryProcessor);

      List<QueryProcessor> actual = queryBuilder.getQueryProcessors();

      assertThatQueryBuilderHasDefaultQueryProcessors(actual);
      assertThat(actual, hasItem(queryProcessor));
   }

   @Test
   public void buildQuery_should_call_buildQuery_on_QueryProcessor() throws Exception {
      QueryBuilder queryBuilder = new QueryBuilder().withQueryProcessors(Arrays.asList(queryProcessor));
      Object queryInput = new Object();

      queryBuilder.buildQuery(queryInput);

      verify(queryProcessor).buildQuery(eq(queryInput), Matchers.<SolrQuery>any());
   }

   @Test
   public void buildQuery_with_search_argument_should_call_build_query_with_the_searchRequest_contained_in_the_search_argument() throws Exception {
      QueryBuilder queryBuilder = new QueryBuilder().withQueryProcessors(Arrays.asList(queryProcessor));
      Object queryInput = new Object();
      Search<?> search = Mockito.mock(Search.class);
      when(search.getSearchRequest()).thenReturn(queryInput);

      queryBuilder.buildQuery(search);

      verify(queryProcessor).buildQuery(eq(queryInput), Matchers.<SolrQuery>any());

   }


   private void assertThatQueryBuilderHasDefaultQueryProcessors(List<QueryProcessor> actual) {
      assertThat(actual, hasInstanceOf(FacetFieldQueryProcessor.class));
      assertThat(actual, hasInstanceOf(FilterQueryProcessor.class));
      assertThat(actual, hasInstanceOf(QueryProcessor.class));
   }

   private Matcher<List<QueryProcessor>> hasInstanceOf(final Class<? extends QueryProcessor> queryProcessorClass) {
      return new BaseMatcher<List<QueryProcessor>>() {
         @SuppressWarnings({"unchecked"})
         @Override
         public boolean matches(Object o) {
            List<QueryProcessor> actual = (List<QueryProcessor>) o;
            for (QueryProcessor queryProcessor : actual) {
               if (queryProcessorClass.isInstance(queryProcessor)) {
                  return true;
               }
            }
            return false;
         }

         @Override
         public void describeTo(Description description) {
            description.appendText("expected list to contain a instance of ").appendValue(queryProcessorClass);
         }
      };
   }

}
