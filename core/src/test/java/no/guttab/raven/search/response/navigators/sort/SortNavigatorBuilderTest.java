package no.guttab.raven.search.response.navigators.sort;

import no.guttab.raven.annotations.SortTarget;
import no.guttab.raven.search.response.Navigation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SortNavigatorBuilderTest {
   @Mock
   private Navigation navigation;

   @Test
   public void buildFor_should_create_sort_navigator_item_when_field_annotated_with_SortTarget() throws Exception {
//      @SortOrder(name = "", displayName = "", expression = "")
      class TestResponseType {
         @SortTarget
         int price;
      }
      SortNavigatorBuilder sortNavigatorBuilder = new SortNavigatorBuilder(navigation);
      SortNavigator sortNavigator = sortNavigatorBuilder.buildFor(TestResponseType.class);

//      sortNavigator.getFirstSelectedItem().getCount()
   }
}
