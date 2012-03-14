package no.guttab.raven.search.response;

import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.annotations.IndexFieldName;
import no.guttab.raven.annotations.Sort;
import org.junit.Test;

import java.util.List;

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
    public void requestFieldNameFor_should_return_annotated_sort_field_when_called_with_sort_field_name() throws Exception {
        class TestRequest {
            @Sort
            String sortField;
        }

        SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

        String actual = searchRequestConfig.requestFieldNameFor("sortField");

        assertThat(actual, is("sortField"));

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

    @Test
    public void isRequestFieldMultiSelect_should_return_true_when_the_field_is_a_collection() {
        class TestRequest {
            @FilterQuery
            List<String> multiSelectField;
        }
        SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

        boolean actual = searchRequestConfig.isRequestFieldMultiSelect("multiSelectField");

        assertThat(actual, is(true));
    }

    @Test
    public void isRequestFieldMultiSelect_should_return_true_when_the_field_is_a_array() {
        class TestRequest {
            @FilterQuery
            String[] multiSelectField;
        }
        SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

        boolean actual = searchRequestConfig.isRequestFieldMultiSelect("multiSelectField");

        assertThat(actual, is(true));
    }

    @Test
    public void isRequestFieldMultiSelect_should_return_false_when_the_field_is_a_String() {
        class TestRequest {
            @FilterQuery
            String singleSelectField;
        }
        SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

        boolean actual = searchRequestConfig.isRequestFieldMultiSelect("singleSelectField");

        assertThat(actual, is(false));
    }

    @Test
    public void isIndexFieldMultiSelect_should_return_true_when_the_field_is_a_collection() {
        class TestRequest {
            @IndexFieldName("multiSelectIndexFieldName")
            @FilterQuery
            List<String> multiSelectField;
        }
        SearchRequestTypeInfo searchRequestConfig = new SearchRequestTypeInfo(TestRequest.class);

        boolean actual = searchRequestConfig.isIndexFieldMultiSelect("multiSelectIndexFieldName");

        assertThat(actual, is(true));
    }


}
