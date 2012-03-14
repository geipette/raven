package no.guttab.raven.search.response;

import no.guttab.raven.annotations.*;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class SearchRequestTypeInfoTest {

    @Test
    public void indexFieldNameFor_should_return_requestFieldName_when_no_indexFieldName_annotated() throws Exception {
        class TestRequest {
            @FilterQuery
            String aRequestFieldName;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        String actual = searchRequestTypeInfo.indexFieldNameFor("aRequestFieldName");

        assertThat(actual, is("aRequestFieldName"));
    }

    @Test
    public void requestFieldNameFor_should_return_indexFieldName_when_no_indexFieldName_annotated() throws Exception {
        class TestRequest {
            @FilterQuery
            String aRequestFieldName;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        String actual = searchRequestTypeInfo.requestFieldNameFor("aRequestFieldName");

        assertThat(actual, is("aRequestFieldName"));
    }

    @Test
    public void indexFieldNameFor_should_return_indexFieldName_when_annotated() throws Exception {
        class TestRequest {
            @IndexFieldName("req")
            @FilterQuery
            String aRequestFieldName;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        String actual = searchRequestTypeInfo.indexFieldNameFor("aRequestFieldName");

        assertThat(actual, is("req"));
    }

    @Test
    public void requestFieldNameFor_should_return_requestFieldName_when_indexFieldName_annotated() throws Exception {
        class TestRequest {
            @IndexFieldName("req")
            @FilterQuery
            String aRequestFieldName;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        String actual = searchRequestTypeInfo.requestFieldNameFor("req");

        assertThat(actual, is("aRequestFieldName"));
    }

    @Test
    public void requestFieldNameFor_should_return_annotated_sort_field_when_called_with_sort_field_name() throws Exception {
        class TestRequest {
            @Sort
            String sortField;
        }

        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        String actual = searchRequestTypeInfo.requestFieldNameFor("sortField");

        assertThat(actual, is("sortField"));

    }

    @Test
    public void indexFieldNameFor_should_return_requestFieldName_when_FacetField_annotation_implies_FilterQuery()
            throws Exception {
        class TestRequest {
            @FacetField
            String aRequestFieldName;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        String actual = searchRequestTypeInfo.indexFieldNameFor("aRequestFieldName");

        assertThat(actual, is("aRequestFieldName"));
    }

    @Test
    public void isRequestFieldMultiSelect_should_return_true_when_the_field_is_a_collection() {
        class TestRequest {
            @FilterQuery
            List<String> multiSelectField;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        boolean actual = searchRequestTypeInfo.isRequestFieldMultiSelect("multiSelectField");

        assertThat(actual, is(true));
    }

    @Test
    public void isRequestFieldMultiSelect_should_return_true_when_the_field_is_a_array() {
        class TestRequest {
            @FilterQuery
            String[] multiSelectField;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        boolean actual = searchRequestTypeInfo.isRequestFieldMultiSelect("multiSelectField");

        assertThat(actual, is(true));
    }

    @Test
    public void isRequestFieldMultiSelect_should_return_false_when_the_field_is_a_String() {
        class TestRequest {
            @FilterQuery
            String singleSelectField;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        boolean actual = searchRequestTypeInfo.isRequestFieldMultiSelect("singleSelectField");

        assertThat(actual, is(false));
    }

    @Test
    public void isIndexFieldMultiSelect_should_return_true_when_the_field_is_a_collection() {
        class TestRequest {
            @IndexFieldName("multiSelectIndexFieldName")
            @FilterQuery
            List<String> multiSelectField;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        boolean actual = searchRequestTypeInfo.isIndexFieldMultiSelect("multiSelectIndexFieldName");

        assertThat(actual, is(true));
    }

    @Test
    public void when_request_is_annotated_with_Page_pageCount_should_reflect_annotated_value() {
        class TestRequest {
            @Page(resultsPerPage = 10)
            int page;
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        int actual = searchRequestTypeInfo.getResultsPerPage();

        assertThat(actual, is(10));

    }

    @Test
    public void when_request_is_not_annotated_with_Page_pageCount_should_be_null() {
        class TestRequest {
        }
        SearchRequestTypeInfo searchRequestTypeInfo = new SearchRequestTypeInfo(TestRequest.class);

        Integer actual = searchRequestTypeInfo.getResultsPerPage();

        assertThat(actual, is(nullValue()));

    }


}
