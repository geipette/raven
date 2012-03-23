package no.guttab.raven.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.annotations.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PageAnnotatedFieldCallbackTest {
    @Mock
    private SolrQuery solrQuery;

    @Test
    public void when_page_is_not_set_then_start_should_not_be_set() throws Exception {
        TestInput input = new TestInput();

        PageAnnotatedFieldCallback callback = new PageAnnotatedFieldCallback(solrQuery, input);
        callback.doFor(testPageField(), testPageAnnotation());

        verify(solrQuery).setRows(5);
        verifyNoMoreInteractions(solrQuery);
                
    }

    @Test
    public void when_page_is_one_then_start_should_not_be_set() throws Exception {
        TestInput input = new TestInput();
        input.page = 1;

        PageAnnotatedFieldCallback callback = new PageAnnotatedFieldCallback(solrQuery, input);
        callback.doFor(TestInput.class.getField("page"), testPageAnnotation());

        verify(solrQuery).setRows(5);
        verifyNoMoreInteractions(solrQuery);
    }

    @Test
    public void when_page_is_two_then_start_should_not_be_equalTo_resultsPerPage() throws Exception {
        TestInput input = new TestInput();
        input.page = 2;

        PageAnnotatedFieldCallback callback = new PageAnnotatedFieldCallback(solrQuery, input);
        callback.doFor(testPageField(), testPageAnnotation());

        verify(solrQuery).setRows(5);
        verify(solrQuery).setStart(5);
    }

    private static class TestInput {
        @Page(resultsPerPage = 5)
        public Integer page;
    }

    private Page testPageAnnotation() throws NoSuchFieldException {
        return testPageField().getAnnotation(Page.class);
    }

    private Field testPageField() throws NoSuchFieldException {
        return TestInput.class.getField("page");
    }

}
