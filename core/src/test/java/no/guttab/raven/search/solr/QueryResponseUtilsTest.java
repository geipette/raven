package no.guttab.raven.search.solr;

import java.util.Arrays;
import java.util.Set;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class QueryResponseUtilsTest {
   @Mock
   private QueryResponse queryResponse;

   @Test
   public void getFqsFromHeader_should_handle_singleValue() throws Exception {
      setupQueryResponse("cat:electronics");
      Set<String> fq = QueryResponseUtils.getFqsFromHeader(queryResponse);

      assertThat(fq, hasItem("cat:electronics"));
      assertThat(fq.size(), is(1));
   }

   @Test
   public void getFqsFromHeader_should_handle_multiValue_separated_by_AND() throws Exception {
      setupQueryResponse("cat:(electronics AND memory)");
      Set<String> fq = QueryResponseUtils.getFqsFromHeader(queryResponse);

      assertThat(fq, hasItems("cat:electronics", "cat:memory"));
      assertThat(fq.size(), is(2));
   }

   @Test
   public void getFqsFromHeader_should_handle_multiValue_separated_by_OR() throws Exception {
      setupQueryResponse("cat:(electronics OR memory)");
      Set<String> fq = QueryResponseUtils.getFqsFromHeader(queryResponse);

      assertThat(fq, hasItems("cat:electronics", "cat:memory"));
      assertThat(fq.size(), is(2));
   }

   @Test
   public void getFqsFromHeader_should_handle_multiValue_with_quoted_values() throws Exception {
      setupQueryResponse("cat:(electronics OR \"quoted value\")");
      Set<String> fq = QueryResponseUtils.getFqsFromHeader(queryResponse);

      assertThat(fq, hasItems("cat:electronics", "cat:\"quoted value\""));
      assertThat(fq.size(), is(2));
   }

   @Test
   public void getFqsFromHeader_should_handle_multiValue_with_escaped_space_values() throws Exception {
      setupQueryResponse("cat:(electronics OR escaped\\ space)");
      Set<String> fq = QueryResponseUtils.getFqsFromHeader(queryResponse);

      assertThat(fq, hasItems("cat:electronics", "cat:escaped\\ space"));
      assertThat(fq.size(), is(2));
   }

   @Test
   public void getFqsFromHeader_should_handle_multiple_fqs() throws Exception {
      setupQueryResponse("cat:electronics", "cat:memory");
      Set<String> fq = QueryResponseUtils.getFqsFromHeader(queryResponse);

      assertThat(fq, hasItems("cat:electronics", "cat:memory"));
      assertThat(fq.size(), is(2));

   }


   private void setupQueryResponse(String... fqs) {
      NamedList namedList = mock(NamedList.class);
      Mockito.when(queryResponse.getHeader()).thenReturn(namedList);

      SimpleOrderedMap<String> simpleOrderedMap = mock(SimpleOrderedMap.class);
      Mockito.when(namedList.get("params")).thenReturn(simpleOrderedMap);

      Mockito.when(simpleOrderedMap.getAll("fq")).thenReturn(Arrays.asList(fqs));
   }


}
