package no.guttab.raven.search.response.content;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import no.guttab.raven.annotations.IndexFieldName;
import org.apache.solr.common.SolrDocument;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.powermock.api.mockito.PowerMockito.when;


@SuppressWarnings({"unchecked"})
@RunWith(MockitoJUnitRunner.class)
public class DefaultDocumentBuilderTest {
   @Mock
   SolrDocument solrDocument;

   @Test
   public void buildDocument_should_create_document_object()
         throws Exception {
      when_SolrDocument_Iterator_ThenReturn_Entries();

      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      assertThat(defaultDocumentFactory.buildDocument(solrDocument), is(not(nullValue())));
   }

   @Test
   public void buildDocument_should_add_data_when_field_name_that_matches_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("field1", "value1"));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);
      assertThat(actual.field1, equalTo("value1"));
   }

   @Test
   public void buildDocument_should_not_add_data_when_field_name_does_not_match_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("field1", "value1"));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);
      assertThat(actual.field1, equalTo("value1"));
   }

   @Test
   public void buildDocument_should_add_data_when_field_is_annotated_with_a_IndexFieldName_that_match_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("cat", "value1"));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);
      assertThat(actual.category, equalTo("value1"));
   }

   @Test
   public void buildDocument_should_add_boolean_data_when_field_name_matches_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("inStock", true));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);
      assertThat(actual.inStock, equalTo(true));
   }

   @Test
   public void buildDocument_should_add_array_data_when_field_name_matches_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      List<String> expected = Arrays.asList("red", "green", "blue");
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("colors", expected));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);

      assertThat(actual.colors, hasItemInArray("red"));
      assertThat(actual.colors, hasItemInArray("green"));
      assertThat(actual.colors, hasItemInArray("blue"));
      assertThat(actual.colors.length, is(3));
   }

   @Test
   public void buildDocument_should_add_collection_data_when_field_name_matches_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      List<String> expected = Arrays.asList("europe", "africa", "america");
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("continents", expected));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);

      assertThat(actual.continents, equalTo(expected));
   }

   @Test
   public void buildDocument_should_add_dateTime_data_when_field_name_matches_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      DateTime expected = new DateTime();
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("date", expected.toDate()));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);

      assertThat(actual.date, equalTo(expected));
   }

   @Test
   public void buildDocument_should_add_localDate_data_when_field_name_matches_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      LocalDate expected = new LocalDate();
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("localDate", expected.toDate()));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);

      assertThat(actual.localDate, equalTo(expected));
   }

   @Test
   public void buildDocument_should_add_localTime_data_when_field_name_matches_solr_document_field()
         throws Exception {
      DefaultDocumentBuilder<TestDocument> defaultDocumentFactory =
            new DefaultDocumentBuilder<TestDocument>(TestDocument.class);
      Date date = new Date();
      LocalTime expected = LocalTime.fromDateFields(date);
      when_SolrDocument_Iterator_ThenReturn_Entries(entry("time", date));

      TestDocument actual = defaultDocumentFactory.buildDocument(solrDocument);

      assertThat(actual.time, equalTo(expected));
   }

   private void when_SolrDocument_Iterator_ThenReturn_Entries(Map.Entry<String, Object>... entries) {
      when(solrDocument.iterator()).thenReturn(entryIterator(entries));
   }


   private Map.Entry<String, Object> entry(final String key, final Object value) {
      return new Map.Entry<String, Object>() {
         @Override
         public String getKey() {
            return key;
         }

         @Override
         public Object getValue() {
            return value;
         }

         @Override
         public Object setValue(Object value) {
            throw new UnsupportedOperationException();
         }
      };
   }

   private Iterator<Map.Entry<String, Object>> entryIterator(Map.Entry<String, Object>... entries) {
      return Arrays.asList(entries).iterator();
   }


   public static class TestDocument {
      String field1;

      @IndexFieldName("cat")
      String category;

      boolean inStock;

      String[] colors;

      List<String> continents;

      DateTime date;

      LocalDate localDate;

      LocalTime time;

   }

}
