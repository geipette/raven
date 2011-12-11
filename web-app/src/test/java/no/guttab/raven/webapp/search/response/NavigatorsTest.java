package no.guttab.raven.webapp.search.response;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NavigatorsTest {

   Navigators navigators;

   @Before
   public void setUp() throws Exception {
      navigators = new Navigators();
   }

   @Test(expected = UnsupportedOperationException.class)
   public void selectedNavigatorsShouldBeUnmodifiable() throws Exception {
      navigators.getSelectedNavigators().add(Mockito.mock(Navigator.class));
   }

   @Test(expected = UnsupportedOperationException.class)
   public void navigatorsShouldBeUnmodifiable() throws Exception {
      navigators.getNavigators().add(Mockito.mock(Navigator.class));
   }

}
