package no.guttab.raven.search.query;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class StringValueFilterQueryCriteriaBuilderTest {

   @Test
   public void buildQueryCriterias_should_return_one_string_value_when_fieldValue_is_not_a_collection_or_array() throws Exception {
      StringValueFilterQueryCriteriaBuilder builder = new StringValueFilterQueryCriteriaBuilder();
      Collection<String> criterias = builder.buildQueryCriterias("aSimpleString");

      assertThat(criterias.size(), is(1));
      assertThat(criterias, hasItem("aSimpleString"));
   }

   @Test
   public void buildQueryCriterias_should_return_multiple_string_values_when_fieldValue_is_a_collection_with_multiple_entries() throws Exception {
      StringValueFilterQueryCriteriaBuilder builder = new StringValueFilterQueryCriteriaBuilder();
      Collection<String> criterias = builder.buildQueryCriterias(Arrays.asList("value1", "value2"));

      assertThat(criterias, hasItems("value1", "value2"));
      assertThat("Criterias should contain 2 elements", criterias.size(), is(2));
   }

   @Test
   public void buildQueryCriterias_should_return_multiple_string_values_when_fieldValue_is_an_array_with_multiple_entries() throws Exception {
      StringValueFilterQueryCriteriaBuilder builder = new StringValueFilterQueryCriteriaBuilder();
      Collection<String> criterias = builder.buildQueryCriterias(new String[]{"value1", "value2"});

      assertThat(criterias, hasItems("value1", "value2"));
      assertThat("Criterias should contain 2 elements", criterias.size(), is(2));
   }

}
