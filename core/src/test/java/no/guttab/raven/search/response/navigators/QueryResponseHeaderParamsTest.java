package no.guttab.raven.search.response.navigators;

import no.guttab.raven.search.filter.FilterQueries;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unchecked"})
@RunWith(MockitoJUnitRunner.class)
public class QueryResponseHeaderParamsTest {
    @Mock
    private NamedList<?> namedList;

    @Mock
    private SimpleOrderedMap<?> paramMap;
    private QueryResponseHeaderParams queryResponseHeaderParams;

    @Before
    public void setUp() throws Exception {
        when(namedList.get("params")).thenReturn(paramMap);
        queryResponseHeaderParams = new QueryResponseHeaderParams(namedList);
    }

    @Test
    public void should_createFilterQueries_when_fqs_are_list_of_strings() throws Exception {
        when(paramMap.getAll("fq")).thenReturn((List) Arrays.asList("test:123"));

        FilterQueries filterQueries = queryResponseHeaderParams.getFilterQueries();

        Set<String> actual = filterQueries.findFqCriteriasFor(createFacetField("test", "123", 50));
        assertThat(actual, hasItem("123"));
        assertThat(actual.size(), is(1));
    }

    @Test
    public void should_createFqs_when_fqs_are_list_of_list_of_strings() throws Exception {
        List fqList = Arrays.asList(Arrays.asList("test:123"), Arrays.asList("test2:456"));
        when(paramMap.getAll("fq")).thenReturn(fqList);

        FilterQueries filterQueries = queryResponseHeaderParams.getFilterQueries();

        Set<String> actual = filterQueries.findFqCriteriasFor(createFacetField("test", "123", 50));
        assertThat(actual, hasItem("123"));
        assertThat(actual.size(), is(1));

        Set<String> actual2 = filterQueries.findFqCriteriasFor(createFacetField("test2", "456", 50));
        assertThat(actual2, hasItem("456"));
        assertThat(actual2.size(), is(1));


    }

    @Test
    public void when_sort_exist_in_params_return_it_when_asked() throws Exception {
        String expected = "id desc";
        when(paramMap.get("sort")).thenReturn(expected);

        String actual = queryResponseHeaderParams.getSort();

        assertThat(actual, is(equalTo(expected)));
    }

    private FacetField createFacetField(String facetFieldName, String criteria, int count) {
        FacetField facetField = new FacetField(facetFieldName);
        facetField.insert(criteria, count);
        return facetField;
    }

}
