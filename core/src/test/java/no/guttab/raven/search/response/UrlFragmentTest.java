package no.guttab.raven.search.response;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class UrlFragmentTest {


   @Test
   public void toString_should_generate_correct_string_fragment() throws Exception {
      UrlFragment urlFragment = new UrlFragment("drink", "beer");

      String actual = urlFragment.toString();

      assertThat(actual, equalTo("drink=beer"));
   }

   @Test
   public void toString_should_urlEncode_values() throws Exception {
      UrlFragment urlFragment = new UrlFragment("drink", "two/beers");

      String actual = urlFragment.toString();

      assertThat(actual, equalTo("drink=two%2Fbeers"));
   }

   @Test
   public void toString_should_urlEncode_keys() throws Exception {
      UrlFragment urlFragment = new UrlFragment("drink/beverage", "beer");

      String actual = urlFragment.toString();

      assertThat(actual, equalTo("drink%2Fbeverage=beer"));
   }


}
