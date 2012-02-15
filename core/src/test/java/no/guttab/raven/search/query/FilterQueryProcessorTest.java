package no.guttab.raven.search.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import no.guttab.raven.annotations.CombineOperator;
import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.annotations.FilterQueryCriteriaBuilder;
import no.guttab.raven.annotations.IndexFieldName;
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
   private SolrQuery solrQuery;

   private FilterQueryProcessor filterQueryProcessor;

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
   public void buildQuery_should_add_complex_filterQuery_when_filterQuery_is_multivalue() throws Exception {
      Object queryInput = new Object() {
         @FacetField
         @FilterQuery
         private List<String> areaIds = Arrays.asList("500", "600");
      };

      filterQueryProcessor.buildQuery(queryInput, solrQuery);

      verify(solrQuery).addFilterQuery("areaIds:(500 AND 600)");
   }

   @Test
   public void buildQuery_should_honor_FilterQuery_mode_when_filterQuery_is_multivalue() throws Exception {
      Object queryInput = new Object() {
         @FilterQuery(mode = CombineOperator.OR)
         private List<String> areaIds = Arrays.asList("500", "600");
      };

      filterQueryProcessor.buildQuery(queryInput, solrQuery);

      verify(solrQuery).addFilterQuery("areaIds:(500 OR 600)");
   }


   @Test
   public void buildQuery_should_add_filterQuery_when_FacetField_implies_FilterQuery() throws Exception {
      Object queryInput = new Object() {
         @FacetField
         private String areaId = "500";
      };

      filterQueryProcessor.buildQuery(queryInput, solrQuery);

      verify(solrQuery).addFilterQuery("areaId:500");
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

   public static class DateRangeBuilder implements FilterQueryCriteriaBuilder<DateRange> {
      @Override
      public Collection<String> buildQueryCriterias(DateRange fieldValue) {
         DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis();
         return Arrays.asList("[" +
               dateTimeFormatter.print(fieldValue.fromDate) +
               " TO " +
               dateTimeFormatter.print(fieldValue.toDate) +
               "]");
      }
   }


}
