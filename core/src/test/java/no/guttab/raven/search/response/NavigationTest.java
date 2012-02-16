package no.guttab.raven.search.response;

import java.util.Collections;

import no.guttab.raven.search.SearchRequestTypeInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NavigationTest {
   @Mock
   private SearchRequestTypeInfo searchRequestTypeInfo;

   @Mock
   private QueryResponse queryResponse;

   @Mock
   private NamedList<?> namedList;

   @Mock
   private SimpleOrderedMap<Object> paramMap;

   @Before
   public void setUp() throws Exception {
      when(queryResponse.getResponseHeader()).thenReturn(namedList);
      when(namedList.get("params")).thenReturn(paramMap);
      when(paramMap.getAll("fq")).thenReturn(Collections.emptyList());
   }

   @Test
   public void urlFor_sort_should_use_requestFieldName_to_generate_url() throws Exception {
      when(searchRequestTypeInfo.requestFieldNameFor("sort")).thenReturn("s");

      Navigation navigation = new Navigation(searchRequestTypeInfo, queryResponse);
      String url = navigation.urlFor("sort", "1");

      assertThat(url, is(equalTo("?s=1")));
   }
}
