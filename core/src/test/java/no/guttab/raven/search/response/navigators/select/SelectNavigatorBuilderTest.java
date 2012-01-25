package no.guttab.raven.search.response.navigators.select;

import java.util.ArrayList;
import java.util.List;

import no.guttab.raven.search.solr.Navigation;
import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;


@PrepareForTest(SelectNavigatorBuilder.class)
@RunWith(PowerMockRunner.class)
public class SelectNavigatorBuilderTest {
   @Mock
   Navigation navigation;

   @Test
   public void build_should_create_SelectNavigatorItemBuilder() throws Exception {
      FacetField facetField = new FacetField("facetName");
      mockSelectNavigatorItemBuilderConstructor(facetField);

      new SelectNavigatorBuilder(navigation);
      verifyNew(SelectNavigatorItemBuilder.class);
   }

   @Test
   public void build_should_create_SelectNavigatorItemBuilder_to_get_input_when_creating_SelectNavigator() throws Exception {
      FacetField facetField = new FacetField("facetName");
      SelectNavigatorItemBuilder itemBuilder = mockSelectNavigatorItemBuilderConstructor(facetField);
      mockSelectNavigatorConstructor();
      List<SelectNavigatorItem> items = new ArrayList<SelectNavigatorItem>();
      List<SelectNavigatorItem> selectedItems = new ArrayList<SelectNavigatorItem>();

      when(itemBuilder.getItems()).thenReturn(items);
      when(itemBuilder.getSelectedItems()).thenReturn(selectedItems);

      new SelectNavigatorBuilder(navigation).buildFor(facetField);

      verifyNew(SelectNavigator.class).withArguments(facetField, items, selectedItems);
   }


   private SelectNavigatorItemBuilder mockSelectNavigatorItemBuilderConstructor(FacetField facetField) throws Exception {
      SelectNavigatorItemBuilder itemBuilder = mock(SelectNavigatorItemBuilder.class);
      whenNew(SelectNavigatorItemBuilder.class).withArguments(navigation, facetField).thenReturn(itemBuilder);
      return itemBuilder;
   }

   private SelectNavigator mockSelectNavigatorConstructor() throws Exception {
      SelectNavigator selectNavigator = mock(SelectNavigator.class);
      whenNew(SelectNavigator.class).withArguments(any(FacetField.class), any(List.class), any(List.class))
            .thenReturn(selectNavigator);
      return selectNavigator;
   }


}
