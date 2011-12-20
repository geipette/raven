package no.guttab.raven.search.response;

import no.guttab.raven.search.config.SearchRequestConfig;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static no.guttab.raven.search.response.UrlFragmentsTest.FragmentCount.hasElementCount;
import static no.guttab.raven.search.response.UrlFragmentsTest.FragmentsContainsEntry.containsEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlFragmentsTest {
   @Mock
   private SearchRequestConfig searchRequestConfig;

   private UrlFragments urlFragments;

   @Before
   public void setUp() throws Exception {
      urlFragments = new UrlFragments(searchRequestConfig);
   }

   @Test
   public void iterator_should_include_added_urlFragment() throws Exception {
      urlFragments.addFragment("aIndexFieldName", new UrlFragment("urlKey", "urlValue"));

      assertThat(urlFragments, hasElementCount(1));
      UrlFragments.Entry expectedEntry = entry("aIndexFieldName", "urlKey", "urlValue");
      assertThat(urlFragments, containsEntry(expectedEntry));
   }

   @Test
   public void iterator_should_include_only_the_last_added_urlFragment_with_same_key_when_indexField_is_singleSelect() throws Exception {
      when(searchRequestConfig.isIndexFieldMultiSelect("aIndexFieldName")).thenReturn(false);
      urlFragments.addFragment("aIndexFieldName", new UrlFragment("urlKey1", "urlValue1"));
      urlFragments.addFragment("aIndexFieldName", new UrlFragment("urlKey1", "urlValue2"));

      assertThat(urlFragments, hasElementCount(1));

      UrlFragments.Entry expectedEntry = entry("aIndexFieldName", "urlKey1", "urlValue2");
      assertThat(urlFragments, containsEntry(expectedEntry));
   }

   @Test
   public void iterator_should_include_added_urlFragments_with_same_key_when_indexField_is_multiSelect() throws Exception {
      when(searchRequestConfig.isIndexFieldMultiSelect("aIndexFieldName")).thenReturn(true);
      urlFragments.addFragment("aIndexFieldName", new UrlFragment("urlKey1", "urlValue1"));
      urlFragments.addFragment("aIndexFieldName", new UrlFragment("urlKey1", "urlValue2"));

      assertThat(urlFragments, hasElementCount(2));

      UrlFragments.Entry expectedEntry1 = entry("aIndexFieldName", "urlKey1", "urlValue1");
      assertThat(urlFragments, containsEntry(expectedEntry1));

      UrlFragments.Entry expectedEntry2 = entry("aIndexFieldName", "urlKey1", "urlValue2");
      assertThat(urlFragments, containsEntry(expectedEntry2));
   }

   @Test
   public void withoutFragment_should_work_when_empty() throws Exception {
      assertThat(urlFragments, hasElementCount(0));
   }

   @Test
   public void withoutFragment_should_remove_remove_supplied_fragment_when_the_fragment_has_been_added() throws Exception {
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue"));

      UrlFragments actualFragments = urlFragments.withoutFragment(new UrlFragment("urlKey1", "urlValue"));

      assertThat(actualFragments, hasElementCount(0));
   }

   @Test
   public void withoutFragment_should_keep_other_fragment_when_a_fragment_has_been_removed() throws Exception {
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue"));
      urlFragments.addFragment("indexFieldName2", new UrlFragment("urlKey2", "urlValue"));

      UrlFragments actualFragments = urlFragments.withoutFragment(new UrlFragment("urlKey1", "urlValue"));
      assertThat(actualFragments, hasElementCount(1));

      UrlFragments.Entry expectedEntry = entry("indexFieldName2", "urlKey2", "urlValue");
      assertThat(urlFragments, containsEntry(expectedEntry));
   }

   @Test
   public void withoutFragment_should_keep_other_fragment_when_a_indexField_is_multiselect_and_fragment_with_same_indexField_has_been_removed() throws Exception {
      when(searchRequestConfig.isIndexFieldMultiSelect("indexFieldName1")).thenReturn(true);
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue1"));
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue2"));

      UrlFragments actualFragments = urlFragments.withoutFragment(new UrlFragment("urlKey1", "urlValue1"));
      assertThat(actualFragments, hasElementCount(1));

      UrlFragments.Entry expectedEntry = entry("indexFieldName1", "urlKey1", "urlValue2");
      assertThat(actualFragments, containsEntry(expectedEntry));
   }

   @Test
   public void withoutIndexField_should_remove_all_fragments_when_a_indexField_is_multiselect_and_has_been_removed() throws Exception {
      when(searchRequestConfig.isIndexFieldMultiSelect("indexFieldName1")).thenReturn(true);
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue1"));
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue2"));

      UrlFragments actualFragments = urlFragments.withoutIndexField("indexFieldName1");
      assertThat(actualFragments, hasElementCount(0));
   }

   @Test
   public void withoutIndexField_should_keep_other_fragments_when_there_are_fragments_under_another_indexField_key() throws Exception {
      when(searchRequestConfig.isIndexFieldMultiSelect("indexFieldName1")).thenReturn(true);
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue1"));
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue2"));
      urlFragments.addFragment("indexFieldName2", new UrlFragment("urlKey2", "urlValue3"));
      urlFragments.addFragment("indexFieldName3", new UrlFragment("urlKey3", "urlValue4"));

      UrlFragments actualFragments = urlFragments.withoutIndexField("indexFieldName1");
      assertThat(actualFragments, hasElementCount(2));

      UrlFragments.Entry expectedEntry1 = entry("indexFieldName2", "urlKey2", "urlValue3");
      assertThat(actualFragments, containsEntry(expectedEntry1));

      UrlFragments.Entry expectedEntry2 = entry("indexFieldName3", "urlKey3", "urlValue4");

      assertThat(urlFragments, containsEntry(expectedEntry2));
   }

   @Test
   public void withAddedFragment_should_replace_fragment_for_indexField_when_singleSelect() throws Exception {
      when(searchRequestConfig.isIndexFieldMultiSelect("indexFieldName1")).thenReturn(false);
      when(searchRequestConfig.requestFieldNameFor("indexFieldName1")).thenReturn("urlKey1");
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue1"));

      UrlFragments actualFragments = urlFragments.withAddedFragment("indexFieldName1", "urlValue2");
      assertThat(actualFragments, hasElementCount(1));

      UrlFragments.Entry expectedEntry = entry("indexFieldName1", "urlKey1", "urlValue2");
      assertThat(actualFragments, containsEntry(expectedEntry));
   }


   @Test
   public void withAddedFragment_should_add_fragment_for_indexField_when_multiSelect() throws Exception {
      when(searchRequestConfig.isIndexFieldMultiSelect("indexFieldName1")).thenReturn(true);
      when(searchRequestConfig.requestFieldNameFor("indexFieldName1")).thenReturn("urlKey1");
      when(searchRequestConfig.requestFieldNameFor("indexFieldName2")).thenReturn("urlKey2");
      urlFragments.addFragment("indexFieldName1", new UrlFragment("urlKey1", "urlValue1"));
      urlFragments.addFragment("indexFieldName2", new UrlFragment("urlKey2", "urlValue3"));

      UrlFragments actualFragments = urlFragments.withAddedFragment("indexFieldName1", "urlValue2");
      assertThat(actualFragments, hasElementCount(3));

      UrlFragments.Entry expectedEntry1 = entry("indexFieldName1", "urlKey1", "urlValue1");
      assertThat(actualFragments, containsEntry(expectedEntry1));

      UrlFragments.Entry expectedEntry2 = entry("indexFieldName1", "urlKey1", "urlValue2");
      assertThat(actualFragments, containsEntry(expectedEntry2));

      UrlFragments.Entry expectedEntry3 = entry("indexFieldName2", "urlKey2", "urlValue3");
      assertThat(actualFragments, containsEntry(expectedEntry3));


   }

   private UrlFragments.Entry entry(String indexFieldName, String requestFieldName, String fqCriteria) {
      return new UrlFragments.Entry(indexFieldName, new UrlFragment(requestFieldName, fqCriteria));
   }



   static class FragmentCount extends BaseMatcher<UrlFragments> {

      private int expectedCount;

      FragmentCount(int expectedCount) {
         this.expectedCount = expectedCount;
      }

      public static FragmentCount hasElementCount(int expectedCount) {
         return new FragmentCount(expectedCount);
      }

      @Override
      public boolean matches(Object o) {
         if (o instanceof UrlFragments) {
            UrlFragments urlFragments = (UrlFragments) o;
            return elementCount(urlFragments) == expectedCount;
         }
         return false;
      }

      private int elementCount(Iterable<?> iterable) {
         int count = 0;
         for (Object o : iterable) {
            count++;
         }
         return count;
      }

      @Override
      public void describeTo(Description description) {
         description.appendText("UrlFragments should contain exactly ").appendValue(expectedCount).appendText(" elements");
      }
   }

   static class FragmentsContainsEntry extends BaseMatcher<UrlFragments> {
      private UrlFragments.Entry entry;

      FragmentsContainsEntry(UrlFragments.Entry entry) {
         this.entry = entry;
      }

      public static FragmentsContainsEntry containsEntry(UrlFragments.Entry entry) {
         return new FragmentsContainsEntry(entry);
      }

      @Override
      public boolean matches(Object o) {
         if (o instanceof UrlFragments) {
            UrlFragments urlFragments = (UrlFragments) o;
            return fragmentsContainsEntry(urlFragments, entry);
         }
         return false;
      }

      private boolean fragmentsContainsEntry(UrlFragments urlFragments, UrlFragments.Entry entry) {
         for (UrlFragments.Entry fragment : urlFragments) {
            if (entry.getIndexFieldName().equals(fragment.getIndexFieldName()) && entry.getFragment().equals(fragment.getFragment())) {
               return true;
            }
         }
         return false;
      }

      @Override
      public void describeTo(Description description) {
         description.appendText("UrlFragments should contain: ").appendValue(entry);
      }
   }


}
