package no.guttab.raven.search.solr;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class FilterQuerySplitterTest {

   @Test
   public void fqStringSet_should_contain_a_single_filterQuery_supplied() throws Exception {
      FilterQuerySplitter filterQuerySplitter = new FilterQuerySplitter(
            Arrays.asList("cat:electronics")
      );

      Set<String> fq = filterQuerySplitter.splitFqs();

      assertThat(fq, hasItem("cat:electronics"));
      assertThat(fq.size(), is(1));
   }

   @Test
   public void parser_should_split_fq_into_two_fqs_when_fq_is_multiValue_separated_by_AND() throws Exception {
      FilterQuerySplitter filterQuerySplitter = new FilterQuerySplitter(
            Arrays.asList("cat:(electronics AND memory)")
      );

      Set<String> fq = filterQuerySplitter.splitFqs();

      assertThat(fq, hasItems("cat:electronics", "cat:memory"));
      assertThat(fq.size(), is(2));
   }

   @Test
   public void parser_should_split_fq_into_two_fqs_when_fq_is_multiValue_separated_by_OR() throws Exception {
      FilterQuerySplitter filterQuerySplitter = new FilterQuerySplitter(
            Arrays.asList("cat:(electronics OR memory)")
      );

      Set<String> fq = filterQuerySplitter.splitFqs();

      assertThat(fq, hasItems("cat:electronics", "cat:memory"));
      assertThat(fq.size(), is(2));
   }

   @Test
   public void parser_should_handle_multiValue_with_quoted_values() throws Exception {
      FilterQuerySplitter filterQuerySplitter = new FilterQuerySplitter(
            Arrays.asList("cat:(electronics OR \"quoted value\")")
      );

      Set<String> fq = filterQuerySplitter.splitFqs();

      assertThat(fq, hasItems("cat:electronics", "cat:\"quoted value\""));
      assertThat(fq.size(), is(2));
   }

   @Test
   public void parser_should_handle_multiValue_with_escaped_space_values() throws Exception {
      FilterQuerySplitter filterQuerySplitter = new FilterQuerySplitter(
            Arrays.asList("cat:(electronics OR escaped\\ space)")
      );

      Set<String> fq = filterQuerySplitter.splitFqs();

      assertThat(fq, hasItems("cat:electronics", "cat:escaped\\ space"));
      assertThat(fq.size(), is(2));
   }

   @Test
   public void parser_should_handle_multiple_fqs() throws Exception {
      FilterQuerySplitter filterQuerySplitter = new FilterQuerySplitter(
            Arrays.asList("cat:electronics", "cat:memory")
      );

      Set<String> fq = filterQuerySplitter.splitFqs();

      assertThat(fq, hasItems("cat:electronics", "cat:memory"));
      assertThat(fq.size(), is(2));

   }

}
