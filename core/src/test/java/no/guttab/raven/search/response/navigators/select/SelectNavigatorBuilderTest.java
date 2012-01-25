package no.guttab.raven.search.response.navigators.select;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import no.guttab.raven.search.solr.Navigation;
import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectNavigatorBuilderTest {
   @Mock
   Navigation navigation;

   @Test
   public void build_should_return_no_navigators_when_facetField_has_no_values() throws Exception {
      FacetField facetField = new FacetField("facetName");

      SelectNavigatorBuilder selectNavigatorBuilder = new SelectNavigatorBuilder(navigation);
      SelectNavigator actual = selectNavigatorBuilder.buildFor(facetField);

      assertThat(actual.getSelectedItems().size(), is(0));
      assertThat(actual.getItems().size(), is(0));
   }


   @Test
   public void build_should_return_only_unselected_navigators_when_no_fqs_defined_for_the_facetField() throws Exception {
      FacetField facetField = new FacetField("facetName");
      facetField.add("facet", 10);
      when(navigation.fqsFor(facetField)).thenReturn(Collections.<String>emptySet());

      SelectNavigatorBuilder selectNavigatorBuilder = new SelectNavigatorBuilder(navigation);

      SelectNavigator actual = selectNavigatorBuilder.buildFor(facetField);

      assertThat(actual.getSelectedItems().size(), is(0));
      assertThat(actual.getItems().size(), is(1));
   }

   @Test
   public void build_should_return_selected_navigator_when_a_fq_exists_for_the_facetField() throws Exception {
       FacetField facetField = new FacetField("facetName");
       facetField.add("facet", 10);
       when(navigation.fqsFor(facetField)).thenReturn(new HashSet<String>(
               Arrays.asList(
                       "facetName:facet"
               )
       ));

       SelectNavigatorBuilder selectNavigatorBuilder = new SelectNavigatorBuilder(navigation);

       SelectNavigator actual = selectNavigatorBuilder.buildFor(facetField);

       assertThat(actual.getSelectedItems().size(), is(1));
       assertThat(actual.getItems().size(), is(0));

   }
}
