package no.guttab.raven.search.response.navigators.select;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import no.guttab.raven.search.response.navigators.ImmutableNavigatorItems;
import no.guttab.raven.search.response.navigators.NavigatorItems;
import no.guttab.raven.search.solr.Navigation;
import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.*;

@PrepareForTest(SelectNavigatorItemsBuilder.class)
@RunWith(PowerMockRunner.class)
public class SelectNavigatorItemsBuilderTest {
   @Mock
   private Navigation navigation;

   private FacetField facetField;

   @Test
   public void build_should_create_immutable_navigator_items_and_return_it() throws Exception {
      ImmutableNavigatorItems<SelectNavigatorItem> expected =
            new ImmutableNavigatorItems<SelectNavigatorItem>(null, null);
      setupFacetField("category");
      whenNew(ImmutableNavigatorItems.class).withArguments(any(List.class), any(List.class)).thenReturn(expected);

      NavigatorItems<SelectNavigatorItem> actual = SelectNavigatorItemsBuilder.build(navigation, facetField);

      verifyNew(ImmutableNavigatorItems.class);
      assertThat(actual, sameInstance(actual));
   }

   @Test
   public void build_should_create_only_items_when_navigation_has_no_fqs_for_the_facetField() throws Exception {
      setupFacetField("category", "electronics");
      when(navigation.fqsFor(facetField)).thenReturn(Collections.<String>emptySet());

      NavigatorItems<SelectNavigatorItem> actual = SelectNavigatorItemsBuilder.build(navigation, facetField);

      assertThatItemsHasOnly("electronics", actual.getItems());
      assertThat(actual.getSelectedItems().size(), is(0));
   }

   @Test
   public void build_should_create_selected_item_when_navigation_has_fq_for_facetField() throws Exception {
      setupFacetField("category", "electronics", "cars");
      when(navigation.fqsFor(facetField)).thenReturn(
            new HashSet<String>(Arrays.asList("category:electronics"))
      );

      NavigatorItems<SelectNavigatorItem> actual = SelectNavigatorItemsBuilder.build(navigation, facetField);

      assertThatItemsHasOnly("cars", actual.getItems());
      assertThatItemsHasOnly("electronics", actual.getSelectedItems());
   }

   @Test
   public void build_should_not_create_items_if_facetField_has_no_facets() throws Exception {
      setupFacetField("category");
      when(navigation.fqsFor(facetField)).thenReturn(
            new HashSet<String>(Arrays.asList("category:electronics"))
      );

      NavigatorItems<SelectNavigatorItem> actual = SelectNavigatorItemsBuilder.build(navigation, facetField);

      assertThat(actual.getItems().size(), is(0));
      assertThat(actual.getSelectedItems().size(), is(0));
   }


   private void assertThatItemsHasOnly(String name, List<SelectNavigatorItem> items) {
      assertThat(items.size(), is(1));
      assertThat(items.get(0).getName(), is(name));
   }


   private void setupFacetField(String facetKey, String... facetNames) {
      facetField = new FacetField(facetKey);
      for (String facetName : facetNames) {
         facetField.add(facetName, 10);
      }
   }

}
