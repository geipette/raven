package no.guttab.raven.search.response.solr;

import no.guttab.raven.search.filter.FilterQueries;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class FilterQueriesTest {

   @Test
   public void extractFqCriteria_should_extract_correct_part_of_string() throws Exception {
      String actual = FilterQueries.extractFqCriteria("cat:electronics");
      assertThat(actual, equalTo("electronics"));
   }
}

