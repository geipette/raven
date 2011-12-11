package no.guttab.raven.webapp.search.response;

import org.junit.Test;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class NavigatorUrlsTest {

   @Test
   public void buildUrlFor_should_generate_correct_url_when_first_fragment_key_used() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls();
      navigatorUrls.addUrlFragment("cat", new UrlFragment("category", "electronics"));
      navigatorUrls.addUrlFragment("sort", new UrlFragment("sortorder", "1"));

      String actual = navigatorUrls.buildUrlFor("cat", "cars");

      assertThat(actual, anyOf(equalTo("?category=cars&sortorder=1"), equalTo("?sortorder=1&category=cars")));
   }

   @Test
   public void buildUrlFor_should_generate_correct_url_when_second_fragment_key_used() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls();
      navigatorUrls.addUrlFragment("cat", new UrlFragment("category", "electronics"));
      navigatorUrls.addUrlFragment("sort", new UrlFragment("sortorder", "1"));

      String actual = navigatorUrls.buildUrlFor("sort", "2");

      assertThat(actual, anyOf(equalTo("?category=electronics&sortorder=2"), equalTo("?sortorder=2&category=electronics")));
   }

}
