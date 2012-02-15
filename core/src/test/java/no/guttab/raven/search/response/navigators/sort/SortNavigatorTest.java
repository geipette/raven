package no.guttab.raven.search.response.navigators.sort;

import java.util.Arrays;
import java.util.Collections;

import no.guttab.raven.search.response.navigators.NavigatorItems;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SortNavigatorTest {
   @Mock
   private NavigatorItems<SortNavigatorItem> navigatorItems;

   SortNavigator sortNavigator;

   @Before
   public void setUp() throws Exception {
      sortNavigator = new SortNavigator(navigatorItems);
   }

   @Test
   public void getFirstSelectedItem_should_return_null_when_no_items_selected() throws Exception {
      when(navigatorItems.getSelectedItems()).thenReturn(Collections.<SortNavigatorItem>emptyList());

      SortNavigatorItem actual = sortNavigator.getFirstSelectedItem();

      assertThat(actual, is(nullValue()));
   }

   @Test
   public void getFirstSelectedItem_should_return_first_selected_item() throws Exception {
      SortNavigatorItem expected = Mockito.mock(SortNavigatorItem.class);
      when(navigatorItems.getSelectedItems()).thenReturn(Arrays.asList(expected));

      SortNavigatorItem actual = sortNavigator.getFirstSelectedItem();

      assertThat(actual, is(sameInstance(expected)));
   }

}
