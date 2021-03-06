package no.guttab.raven.search.response.navigators.select;

import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectNavigatorItemTest {
   @Mock
   private FacetField.Count facetFieldCount;

   @Test
   public void getCount_should_get_count_from_supplied_facetFieldCount() throws Exception {
      when(facetFieldCount.getCount()).thenReturn(100L);
      SelectNavigatorItem item = new SelectNavigatorItem(facetFieldCount, null);
      assertThat(item.getCount(), is(100L));
   }

   @Test
   public void getName_should_get_name_from_supplied_facetFieldCount() throws Exception {
      when(facetFieldCount.getName()).thenReturn("aName");
      SelectNavigatorItem item = new SelectNavigatorItem(facetFieldCount, null);
      assertThat(item.getName(), is("aName"));
   }

    @Test
    public void getUrl_should_return_supplied_url() throws Exception {
       SelectNavigatorItem item = new SelectNavigatorItem(facetFieldCount, "aUrl");
       assertThat(item.getUrl(), is("aUrl"));
    }

    @Test
    public void getDeselectUrl_should_return_supplied_DeselectUrl() throws Exception {
       SelectNavigatorItem item = new SelectNavigatorItem(facetFieldCount, "aUrl", "deselectUrl");
       assertThat(item.getDeselectUrl(), is("deselectUrl"));
    }



}
