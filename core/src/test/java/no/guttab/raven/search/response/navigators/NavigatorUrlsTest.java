package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.config.SearchRequestConfig;
import no.guttab.raven.search.response.NavigatorUrls;
import no.guttab.raven.search.response.UrlFragment;
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
   public void buildUrlFor_should_reset_values_for_key_when_no_value_supplied() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");

      navigatorUrls.addUrlFragment("sort", new UrlFragment("sortorder", "1"));
      navigatorUrls.addUrlFragment("cat", new UrlFragment("category", "electronics"));

      String actual = navigatorUrls.buildUrlFor("cat");

      assertThat(actual, equalTo("?sortorder=1"));
   }

   @Test
   public void buildUrlFor_should_replace_value_when_the_facetField_is_singleSelect() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");
      when(searchRequestConfig.isRequestFieldMultiSelect("cat")).thenReturn(false);

      navigatorUrls.addUrlFragment("cat", new UrlFragment("category", "electronics"));
      navigatorUrls.addUrlFragment("sort", new UrlFragment("sortorder", "1"));

      String actual = navigatorUrls.buildUrlFor("cat", "cars");

      assertThat(actual, anyOf(equalTo("?category=cars&sortorder=1"), equalTo("?sortorder=1&category=cars")));
   }

   @Test
   public void buildUrlFor_should_add_value_when_the_facetField_is_multiSelect() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");
      when(searchRequestConfig.isRequestFieldMultiSelect("cat")).thenReturn(true);

      navigatorUrls.addUrlFragment("cat", new UrlFragment("category", "electronics"));
      navigatorUrls.addUrlFragment("sort", new UrlFragment("sortorder", "2"));

      String actual = navigatorUrls.buildUrlFor("cat", "cars");

      assertThat(actual, anyOf(
            equalTo("?category=cars&category=electronics&sortorder=2"),
            equalTo("?category=cars&sortorder=2&category=electronics"),
            equalTo("?category=electronics&category=cars&sortorder=2"),
            equalTo("?sortorder=2&category=cars&category=electronics"),
            equalTo("?category=electronics&sortorder=2&category=cars"),
            equalTo("?sortorder=2&category=electronics&category=cars&")
      ));
   }

}
