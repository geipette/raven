package no.guttab.raven.search.response.navigators.sort;

import java.util.List;

import no.guttab.raven.annotations.SortDirection;
import no.guttab.raven.annotations.SortTarget;
import no.guttab.raven.annotations.SortVariant;
import no.guttab.raven.search.response.Navigation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SortNavigatorBuilderTest {
   @Mock
   private Navigation navigation;


   @Test
   public void build_should_create_sort_navigator_item_when_a_field_is_annotated_with_SortTarget() throws Exception {
      class TestSearchResponse {
         @SortTarget
         String price;
      }
      when(navigation.getSortField()).thenReturn("sort");

      SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
      SortNavigator actual = sortNavigatorBuilder.build();

      assertThat(actual, is(not(nullValue())));
   }

   @Test
   public void returned_SortNavigator_should_have_SortNavigatorItem_for_annotated_field() throws Exception {
      class TestSearchResponse {
         @SortTarget
         String price;
      }
      when(navigation.getSortField()).thenReturn("sort");

      SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
      SortNavigator sortNavigator = sortNavigatorBuilder.build();
      SortNavigatorItem actualItem = getFirstItem(sortNavigator);

      assertThat(actualItem.getName(), is("price"));
   }

   @Test
   public void returned_SortNavigator_should_have_a_SortNavigatorItem_for_each_variant() throws Exception {
      class TestSearchResponse {
         @SortTarget(variants = {
               @SortVariant(direction = SortDirection.ASCENDING),
               @SortVariant(direction = SortDirection.DESCENDING)
         })
         String price;
      }
      when(navigation.getSortField()).thenReturn("sort");

      SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
      SortNavigator sortNavigator = sortNavigatorBuilder.build();
      List<SortNavigatorItem> actual = sortNavigator.getItems();

      assertThat(actual.size(), is(2));
      assertThat(actual.get(0).getName(), is("price"));
      assertThat(actual.get(1).getName(), is("-price"));
   }

   @Test
   public void sortNavigatorItem_for_annotated_field_should_use_displayName_from_annotation() throws Exception {
      class TestSearchResponse {
         @SortTarget(displayName = "Pris")
         String price;
      }
      when(navigation.getSortField()).thenReturn("sort");

      SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
      SortNavigator sortNavigator = sortNavigatorBuilder.build();
      SortNavigatorItem actualItem = getFirstItem(sortNavigator);

      assertThat(actualItem.getName(), is("Pris"));
   }

   @Test
   public void sortNavigatorItem_url_should_use_key_corresponding_to_SearchRequest_Sort_annotation() throws Exception {
      class TestSearchResponse {
         @SortTarget(displayName = "Pris")
         String price;
      }
      when(navigation.getSortField()).thenReturn("sort");

      SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
      SortNavigator sortNavigator = sortNavigatorBuilder.build();
      SortNavigatorItem actualItem = getFirstItem(sortNavigator);

      assertThat(actualItem.getUrl(), is("?sort=price"));
   }

   @Test
   public void sortNavigatorItem_url_should_use_name_corresponding_to_SearchRequest_Sort_annotation() throws Exception {
      class TestSearchResponse {
         @SortTarget(displayName = "Pris", variants = @SortVariant(name = "1"))
         String price;
      }
      when(navigation.getSortField()).thenReturn("sort");

      SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
      SortNavigator sortNavigator = sortNavigatorBuilder.build();
      SortNavigatorItem actualItem = getFirstItem(sortNavigator);

      assertThat(actualItem.getUrl(), is("?sort=1"));
   }


   @Test(expected = SortNavigatorBuilder.NoSortFieldException.class)
   public void build_should_throw_exception_if_SortTarget_defined_but_no_sortField_exists_in_request() throws Exception {
      class TestSearchResponse {
         @SortTarget(displayName = "Pris")
         String price;
      }

      SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(TestSearchResponse.class, navigation);
      SortNavigator sortNavigator = sortNavigatorBuilder.build();
      getFirstItem(sortNavigator);
   }


   private SortNavigatorItem getFirstItem(SortNavigator sortNavigator) {
      List<SortNavigatorItem> navigatorItems = sortNavigator.getItems();
      assertThat("buildFor returned navigator with zero items", navigatorItems.isEmpty(), is(false));
      return navigatorItems.get(0);
   }


}
