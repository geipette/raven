package no.guttab.raven.search.response.navigators.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectNavigatorTest {

   @Mock
   private FacetField facetField;


   @Test
   public void getDisplayName_should_return_facetField_naem() throws Exception {
      when(facetField.getName()).thenReturn("facetFieldName");
      SelectNavigator selectNavigator = new SelectNavigator(facetField);

      String actual = selectNavigator.getDisplayName();

      assertThat(actual, equalTo("facetFieldName"));
   }

   @Test(expected = UnsupportedOperationException.class)
   public void getItems_should_return_an_unmodifiable_list() throws Exception {
      SelectNavigator selectNavigator = new SelectNavigator(
            facetField,
            listWithOneElement(),
            null);

      selectNavigator.getItems().add(emptyItem());
   }

   @Test(expected = UnsupportedOperationException.class)
   public void getSelectedItems_should_return_an_unmodifable_list() throws Exception {
      SelectNavigator selectNavigator = new SelectNavigator(
            facetField,
            null,
            listWithOneElement());

      selectNavigator.getSelectedItems().add(emptyItem());
   }

   @Test
   public void getItems_contents_should_be_equal_to_the_supplied_itemList() throws Exception {
      List<SelectNavigatorItem> expectedItems = listWithItems("item1", "item2");
      SelectNavigator selectNavigator = new SelectNavigator(
            facetField,
            expectedItems,
            null);

      List<SelectNavigatorItem> actual = selectNavigator.getItems();

      assertThat(actual, equalTo(expectedItems));
   }

   @Test
   public void getSelectedItems_contents_should_be_equal_to_the_supplied_selectedItemList() throws Exception {
      List<SelectNavigatorItem> expectedItems = listWithItems("item1", "item2");
      SelectNavigator selectNavigator = new SelectNavigator(
            facetField,
            null,
            expectedItems);

      List<SelectNavigatorItem> actual = selectNavigator.getSelectedItems();

      assertThat(actual, equalTo(expectedItems));
   }

   @Test
   public void isSelected_should_be_false_when_selectedItemList_is_empty() throws Exception {
      SelectNavigator selectNavigator = new SelectNavigator(
            facetField,
            null,
            null);

      boolean actual = selectNavigator.isSelected();

      assertThat(actual, is(false));
   }

   @Test
   public void isSelected_should_be_true_when_selectedItemList_contains_elements() throws Exception {
      SelectNavigator selectNavigator = new SelectNavigator(
            facetField,
            null,
            listWithOneElement());

      boolean actual = selectNavigator.isSelected();

      assertThat(actual, is(true));
   }

   @Test
   public void getFirstSelectedItem_should_return_first_item_in_selectedItemList() throws Exception {
      List<SelectNavigatorItem> items = listWithItems("item1", "item2");
      SelectNavigator selectNavigator = new SelectNavigator(
            facetField,
            null,
            items);
      SelectNavigatorItem expectedItem = items.get(0);

      SelectNavigatorItem actual = selectNavigator.getFirstSelectedItem();

      assertThat(actual, equalTo(expectedItem));
   }

   @Test
   public void getFirstSelectedItem_should_null_when_selectedItemList_is_empty() throws Exception {
      SelectNavigator selectNavigator = new SelectNavigator(
            facetField,
            null,
            null);

      SelectNavigatorItem actual = selectNavigator.getFirstSelectedItem();

      assertThat(actual, is(nullValue()));
   }

   private static SelectNavigatorItem emptyItem() {
      return new SelectNavigatorItem(null, null);
   }

   private List<SelectNavigatorItem> listWithItems(String... itemNames) {
      ArrayList<SelectNavigatorItem> items = new ArrayList<SelectNavigatorItem>();
      for (String itemName : itemNames) {
         items.add(itemWithName(itemName));
      }
      return items;
   }

   private SelectNavigatorItem itemWithName(String itemName) {
      return new SelectNavigatorItem(new FacetField.Count(null, itemName, 0), null, null);
   }

   private List<SelectNavigatorItem> listWithOneElement() {
      return new ArrayList<SelectNavigatorItem>(Arrays.asList(emptyItem()));
   }
}
