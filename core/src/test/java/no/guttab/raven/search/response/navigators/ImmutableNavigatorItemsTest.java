package no.guttab.raven.search.response.navigators;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

public class ImmutableNavigatorItemsTest {

   @Test(expected = UnsupportedOperationException.class)
   public void getItems_should_return_an_unmodifiable_list() throws Exception {
      NavigatorItem navigatorItem = Mockito.mock(NavigatorItem.class);

      ImmutableNavigatorItems<NavigatorItem> navigatorItems = new ImmutableNavigatorItems<NavigatorItem>(
            new ArrayList<NavigatorItem>(), new ArrayList<NavigatorItem>()
      );
      navigatorItems.getItems().add(navigatorItem);
   }

   @Test(expected = UnsupportedOperationException.class)
   public void getSelectedItems_should_return_an_unmodifiable_list() throws Exception {
      NavigatorItem navigatorItem = Mockito.mock(NavigatorItem.class);

      ImmutableNavigatorItems<NavigatorItem> navigatorItems = new ImmutableNavigatorItems<NavigatorItem>(
            new ArrayList<NavigatorItem>(), new ArrayList<NavigatorItem>()
      );
      navigatorItems.getSelectedItems().add(navigatorItem);
   }

}
