package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.response.NavigatorUrls;
import no.guttab.raven.search.solr.FilterQueries;
import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelectNavigatorTest {

   @Mock
   private FacetField facetField;

   @Mock
   private NavigatorUrls navigatorUrls;

   @Mock
   private FilterQueries filterQueries;

   SelectNavigator selectNavigator;

   @Before
   public void setUp() throws Exception {
      selectNavigator = new SelectNavigator(facetField, navigatorUrls, filterQueries);
   }

   @Test
   public void getItems_should_return_one_per_facetField() throws Exception {
//      Mockito.when(facetField.getValues()).thenReturn(Arrays.asList(
//            new FacetField.Count(facetField, "one", 1),
//            new FacetField.Count(facetField, "two", 2)
//      ));
//
//      List<SelectNavigatorItem> actualItems = selectNavigator.getItems();
//
//      assertThat(actualItems.size(), is(2));
   }
}
