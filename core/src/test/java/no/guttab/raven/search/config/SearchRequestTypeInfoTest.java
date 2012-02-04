package no.guttab.raven.search.config;

import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.annotations.IndexFieldName;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SearchRequestTypeInfoTest {

   @Test
   public void indexFieldNameFor_should_return_requestFieldName_when_no_indexFieldName_annotated() throws Exception {
      class TestRequest {
         @FilterQuery
         String aRequestFieldName;
      }
      SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

      String actual = searchRequestConfig.indexFieldNameFor("aRequestFieldName");

      assertThat(actual, is("aRequestFieldName"));
   }

   @Test
   public void requestFieldNameFor_should_return_indexFieldName_when_no_indexFieldName_annotated() throws Exception {
      class TestRequest {
         @FilterQuery
         String aRequestFieldName;
      }
      SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

      String actual = searchRequestConfig.requestFieldNameFor("aRequestFieldName");

      assertThat(actual, is("aRequestFieldName"));
   }

   @Test
   public void indexFieldNameFor_should_return_indexFieldName_when_annotated() throws Exception {
      class TestRequest {
         @IndexFieldName("req")
         @FilterQuery
         String aRequestFieldName;
      }
      SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

      String actual = searchRequestConfig.indexFieldNameFor("aRequestFieldName");

      assertThat(actual, is("req"));
   }

   @Test
   public void requestFieldNameFor_should_return_requestFieldName_when_indexFieldName_annotated() throws Exception {
      class TestRequest {
         @IndexFieldName("req")
         @FilterQuery
         String aRequestFieldName;
      }
      SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

      String actual = searchRequestConfig.requestFieldNameFor("req");

      assertThat(actual, is("aRequestFieldName"));
   }

   @Test
   public void indexFieldNameFor_should_return_requestFieldName_when_FacetField_annotation_implies_FilterQuery()
         throws Exception {
      class TestRequest {
         @FacetField
         String aRequestFieldName;
      }
      SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

      String actual = searchRequestConfig.indexFieldNameFor("aRequestFieldName");

      assertThat(actual, is("aRequestFieldName"));
   }


}
