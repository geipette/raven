package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.config.SearchRequestTypeInfo;
import no.guttab.raven.search.response.NavigatorUrls;
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
   private SearchRequestTypeInfo searchRequestConfig;

   @Test
   public void buildUrlFor_should_generate_correct_url_when_no_fragments_has_been_added() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");

      String actual = navigatorUrls.buildUrlFor("sort", "2");

      assertThat(actual, equalTo("?sortorder=2"));
   }

   @Test
   public void resetUrlFor_should_reset_a_specific_value_when_both_key_and_value_supplied() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.isIndexFieldMultiSelect("cat")).thenReturn(true);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");

      navigatorUrls.addUrlFragment("sort", "1");
      navigatorUrls.addUrlFragment("cat", "electronics");
      navigatorUrls.addUrlFragment("cat", "cars");

      String actual = navigatorUrls.resetUrlFor("cat", "cars");

      assertThat(actual, anyOf(
            equalTo("?sortorder=1&category=electronics"),
            equalTo("?category=electronics&sortorder=1")));
   }


   @Test
   public void resetUrlFor_should_reset_values_for_key_when_no_value_supplied() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");

      navigatorUrls.addUrlFragment("sort", "1");
      navigatorUrls.addUrlFragment("cat", "electronics");

      String actual = navigatorUrls.resetUrlFor("cat");

      assertThat(actual, equalTo("?sortorder=1"));
   }

   @Test
   public void resetUrlFor_should_generate_correct_empty_url() throws Exception {
      NavigatorUrls urlStringBuilder = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");

      String actual = urlStringBuilder.resetUrlFor("sort");

      assertThat(actual, equalTo("?"));
   }

   @Test
   public void buildUrlFor_should_replace_value_when_the_facetField_is_singleSelect() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");
      when(searchRequestConfig.isRequestFieldMultiSelect("cat")).thenReturn(false);

      navigatorUrls.addUrlFragment("cat", "electronics");
      navigatorUrls.addUrlFragment("sort", "1");

      String actual = navigatorUrls.buildUrlFor("cat", "cars");

      assertThat(actual, anyOf(equalTo("?category=cars&sortorder=1"), equalTo("?sortorder=1&category=cars")));
   }

   @Test
   public void buildUrlFor_should_add_value_when_the_facetField_is_multiSelect() throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");
      when(searchRequestConfig.isIndexFieldMultiSelect("cat")).thenReturn(true);

      navigatorUrls.addUrlFragment("cat", "electronics");
      navigatorUrls.addUrlFragment("sort", "2");

      String actual = navigatorUrls.buildUrlFor("cat", "cars");

      assertThat(actual, anyOf(
            equalTo("?category=cars&category=electronics&sortorder=2"),
            equalTo("?category=cars&sortorder=2&category=electronics"),
            equalTo("?category=electronics&category=cars&sortorder=2"),
            equalTo("?sortorder=2&category=cars&category=electronics"),
            equalTo("?category=electronics&sortorder=2&category=cars"),
            equalTo("?sortorder=2&category=electronics&category=cars")
      ));
   }

   @Test
   public void buildUrlFor_should_not_add_a_already_selected_value_when_the_facetField_is_multiSelect()
         throws Exception {
      NavigatorUrls navigatorUrls = new NavigatorUrls(searchRequestConfig);
      when(searchRequestConfig.requestFieldNameFor("sort")).thenReturn("sortorder");
      when(searchRequestConfig.requestFieldNameFor("cat")).thenReturn("category");
      when(searchRequestConfig.isIndexFieldMultiSelect("cat")).thenReturn(true);

      navigatorUrls.addUrlFragment("cat", "electronics");
      navigatorUrls.addUrlFragment("sort", "2");

      String actual = navigatorUrls.buildUrlFor("cat", "electronics");

      assertThat(actual, anyOf(
            equalTo("?category=electronics&sortorder=2"),
            equalTo("?sortorder=2&category=electronics")
      ));
   }


}
