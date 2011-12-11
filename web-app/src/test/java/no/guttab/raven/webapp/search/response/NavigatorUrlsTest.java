package no.guttab.raven.webapp.search.response;

import no.guttab.raven.webapp.search.config.SearchRequestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unchecked"})
@RunWith(MockitoJUnitRunner.class)
public class NavigatorUrlsTest {

   @Mock
   SearchRequestConfig searchRequestConfig;

   @Test
   public void buildUrlFor_should_generate_correct_url_when_no_fragments_has_been_added() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");

      String actual = navigatorUrls.buildUrlFor("sort", "2");

      assertThat(actual, equalTo("?sortorder=2"));
   }

   @Test
   public void buildUrlFor_should_generate_correct_url_when_first_fragment_key_used() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      navigatorUrls.addUrlFragment("cat", new UrlFragment("category", "electronics"));
      navigatorUrls.addUrlFragment("sort", new UrlFragment("sortorder", "1"));
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");

      String actual = navigatorUrls.buildUrlFor("cat", "cars");

      assertThat(actual, anyOf(equalTo("?category=cars&sortorder=1"), equalTo("?sortorder=1&category=cars")));
   }

   @Test
   public void buildUrlFor_should_generate_correct_url_when_second_fragment_key_used() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      navigatorUrls.addUrlFragment("cat", new UrlFragment("category", "electronics"));
      navigatorUrls.addUrlFragment("sort", new UrlFragment("sortorder", "1"));
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");

      String actual = navigatorUrls.buildUrlFor("sort", "2");

      assertThat(actual, anyOf(equalTo("?category=electronics&sortorder=2"), equalTo("?sortorder=2&category=electronics")));
   }

}
