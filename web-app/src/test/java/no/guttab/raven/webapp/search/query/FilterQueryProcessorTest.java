package no.guttab.raven.webapp.search.query;

import no.guttab.raven.webapp.annotations.FilterQuery;
import no.guttab.raven.webapp.annotations.IndexFieldName;
import org.apache.solr.client.solrj.SolrQuery;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FilterQueryProcessorTest {
   @Mock
   SolrQuery solrQuery;

   FilterQueryProcessor filterQueryProcessor;

   @Before
   public void setUp() throws Exception {
      filterQueryProcessor = new FilterQueryProcessor();
   }

   @Test
   public void buildQuery_should_add_filterQuery_for_annotated_field() throws Exception {
      Object queryInput = new Object() {
         @FilterQuery
         private String areaId = "500";
      };

      filterQueryProcessor.buildQuery(queryInput, solrQuery);

      verify(solrQuery).addFilterQuery("areaId:500");
   }

   @Test
   public void buildQuery_should_handle_integer_types() throws Exception {
      Object queryInput = new Object() {
         @FilterQuery
         private Integer id = 500;
      };

      filterQueryProcessor.buildQuery(queryInput, solrQuery);

      verify(solrQuery).addFilterQuery("id:500");
   }

   @Test
   public void buildQuery_should_not_add_filterQuery_for_annotated_field_when_field_value_is_null() throws Exception {
      Object queryInput = new Object() {
         @FilterQuery
         private String areaId;
      };

      filterQueryProcessor.buildQuery(queryInput, solrQuery);

      verifyNoMoreInteractions(solrQuery);
   }

   @Test
   public void filterQuery_for_annotated_field_should_use_indexFieldName_when_specified() throws Exception {
      Object queryInput = new Object() {
         @IndexFieldName("geo_id")
         @FilterQuery
         private String areaId = "500";
      };

      filterQueryProcessor.buildQuery(queryInput, solrQuery);

      verify(solrQuery).addFilterQuery("geo_id:500");
   }

   @Test
   public void filterQuery_for_complex_type_should_use_supplied_FilterQueryCriteriaBuilder() throws Exception {

      Object queryInput = new Object() {
         @FilterQuery(queryCriteriaBuilder = DateRangeBuilder.class)
         private Object date = new DateRange();
      };

      filterQueryProcessor.buildQuery(queryInput, solrQuery);

      verify(solrQuery).addFilterQuery("date:[2011-12-01T00:00:00Z TO 2011-12-02T00:00:00Z]");
   }

   static class DateRange {
      DateTime fromDate = new DateTime(2011, 12, 1, 0, 0, 0, 0, DateTimeZone.forID("UTC"));
      DateTime toDate = new DateTime(2011, 12, 2, 0, 0, 0, 0, DateTimeZone.forID("UTC"));
   }

   static class DateRangeBuilder implements FilterQueryCriteriaBuilder<DateRange> {
      @Override
      public String buildQueryCriteria(DateRange fieldValue) {
         DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis();
         return "[" +
               dateTimeFormatter.print(fieldValue.fromDate) +
               " TO " +
               dateTimeFormatter.print(fieldValue.toDate) +
               "]";
      }
   }






}
