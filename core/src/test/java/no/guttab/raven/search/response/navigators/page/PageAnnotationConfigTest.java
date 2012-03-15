package no.guttab.raven.search.response.navigators.page;

import no.guttab.raven.annotations.Page;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class PageAnnotationConfigTest {

    @Test
    public void when_request_is_annotated_with_Page_pageCount_should_reflect_annotated_value() {
        class TestRequest {
            @Page(resultsPerPage = 10)
            int page;
        }
        PageAnnotationConfig pageAnnotationConfig = new PageAnnotationConfig(TestRequest.class);

        int actual = pageAnnotationConfig.getResultsPerPage();

        assertThat(actual, is(10));

    }

    @Test
    public void when_request_is_not_annotated_with_Page_pageCount_should_be_null() {
        class TestRequest {
        }
        PageAnnotationConfig pageAnnotationConfig = new PageAnnotationConfig(TestRequest.class);

        Integer actual = pageAnnotationConfig.getResultsPerPage();

        assertThat(actual, is(nullValue()));

    }

}
